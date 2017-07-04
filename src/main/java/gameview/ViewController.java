package gameview;



import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayDeque;
import java.util.Queue;


import reti.ClientRequest;
import reti.ServerResponse;
import reti.client.HandlerServer;
import reti.client.HandlerServerRMIImpl;
import reti.client.HandlerSocket;

public class ViewController {
		
	private Queue<ServerResponse> serverMessages=new ArrayDeque<>();
	private ClientRequest crOut;
	private ServerResponse srIn = null;
	private HandlerServer hs;
	private boolean live=true;

	
	public ViewController() throws IOException, InterruptedException, NotBoundException{
		super();
		hs=new HandlerSocket(this);
		//hs=new HandlerServerRMIImpl(this);
		new Thread((Runnable) hs).start();
	}
	

	public boolean hasMessage()
	{
		return !serverMessages.isEmpty();
	}
	
	public void placeResponse(ServerResponse sr) throws IOException{
		
		switch(sr.getType())
		{
		case OK:
		case ERROR:	
		case QUESTION:  
					this.setSRIn(sr);
					break;
						
		case MESSAGE:
		case NEW_MODEL:
		case PLAYER_ASSIGNED:
		case VATICAN_SUPPORT:
		case LEADER:	
					this.serverMessages.add(sr);
					break;
		default:
			System.out.println("What is going on ???");
			break;
		}	
	}


	public ServerResponse getMessage() 
	{
		return serverMessages.remove();
	}
	
	
	private void setSRIn(ServerResponse response){
		this.srIn=response;
		this.crOut=null;
	}


	private synchronized ServerResponse getSRIn() {
		ServerResponse temp=srIn;
		srIn=null;
		return temp;
	}

	public ServerResponse syncSend(ClientRequest request) {
		hs.doRequest(request);
		ServerResponse srr;
		while (live) { 
	    	try {
	    		Thread.sleep(100); 
	    	} catch (InterruptedException e) {
	    		Thread.currentThread().interrupt();
	    		e.printStackTrace(); 
	    	}     	
	    	srr = getSRIn(); 
	    	if (srr != null) return srr; 
	    }
		return null; 
	}

	public void shutdown() {
		hs.shutdown();
	}	
}



