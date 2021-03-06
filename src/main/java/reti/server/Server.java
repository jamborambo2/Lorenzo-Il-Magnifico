package reti.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import gamemodel.jsonparsing.CustomizationFileReader;

/**
 * The Server object is that which accepts the socket connection and holds the current
 * GameManager object. If the user connects via socket, it gets added to the 
 * GameManager by the thread running inside the server. If the user connects 
 * via RMI, he can add himself to the current GameManager. For this reason the user
 * accepting method (addHV) must be synchronized.
 */
public class Server {
	
	private ExecutorService pool = Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	private GameManager gm;
	private boolean live=true;
	
	private int delay;
	private int port;
	private int gameDelay;
	
	public static void main(String[]args) throws IOException, ClassNotFoundException, AlreadyBoundException{
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);	
		Server server=new Server();
		server.setUpServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown(server)));
		server.serverSocket=new ServerSocket(server.port);			
		Registry registry = LocateRegistry.getRegistry();
		RMIAcceptImpl rai= new RMIAcceptImpl(server);
		registry.bind("rai",rai);
		System.out.println("RegistroPronto");
		server.start();		
	}

	private void setUpServer() throws IOException{	
		String config = CustomizationFileReader.reedFile(new File("Config/ServerConfig.json"));
		JsonObject item=Json.parse(config).asObject();	
		port=item.getInt("port", 3003);
		delay=item.getInt("server-delay", 1000);
		gameDelay=item.getInt("game-deley", 200000);
	}

	private void start() throws IOException {
		System.out.println("server start");
		gm=new GameManager(delay,gameDelay);
		pool.execute(gm);
		while(live){
			Socket s=serverSocket.accept();				
			System.out.println("received new connection");
			HandlerViewSocket hv =new HandlerViewSocket(s);		
			addHV(hv);			
		}		
	}
	
	public synchronized void addHV(HandlerView hv){
		if(gm == null || gm.getIsFull()) {
			gm = new GameManager(delay,gameDelay);
			new Thread(gm).start();
		}
		gm.addHV(hv);
		
	}
	
	public void Shutdown(){
		try {
			serverSocket.close();
			live=false;
		} catch (IOException e) {
			Logger.getLogger("errorlog.log").log(Level.ALL, "error: ", e);
		}
	}
}	

class Shutdown implements Runnable {
	private Server s;

	public Shutdown(Server s) {
		this.s = s;
	}
	
	public void run() {
		s.Shutdown();
	}
	
}
