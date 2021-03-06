package gameview;



import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import reti.ClientRequest;
import reti.ServerResponse;
import reti.client.HandlerServer;
import reti.client.HandlerServerRMIImpl;
import reti.client.HandlerSocket;


/**
 * The ViewController object contains some background logic of the interaction between client and server. 
 * 
 */



public class ViewController {
		
	private Queue<ServerResponse> serverMessages=new ArrayDeque<>();
	private ServerResponse srIn = null;
	private HandlerServer hs;
	private boolean live=true;

	
	public ViewController() {
		this("socket");
	}
	

	public ViewController(String networkChoose) {
		super();
		if (networkChoose == "rmi") {
			try {
				hs=new HandlerServerRMIImpl(this);
			} catch (NotBoundException | IOException e) {
				Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
			}
		} else if (networkChoose == "socket") {
			try {
				hs=new HandlerSocket(this);
			} catch (IOException | InterruptedException e) {
				Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
			}
			new Thread((Runnable) hs).start();
		} else {
			
		}
		
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
		case LEADER:	
			this.serverMessages.add(sr);
			break;
		default:
			break;
		}	
	}


	public ServerResponse getMessage() 
	{
		return serverMessages.remove();
	}
	
	
	private void setSRIn(ServerResponse response){
		this.srIn=response;
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
	    		Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
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



