package gameview;

import java.io.IOException;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpace;

public class UITree {
	private UINode root;
	private UINode next;
	private ClientRequest request = new ClientRequest();
	private HandlerServer serverHandler;
//	private ModelShell ms;
	private Model model;
	private Player player;
	boolean hasModel = false;
	boolean hasPlayer = false;
	
		
	public UITree(ModelShell modelShell, HandlerServer serverEndler) {
//		ms = modelShell;
		this.serverHandler=serverEndler;

// 		// Riusciremo a infilarlo nell'albero un giorno?
//		UINodeSetResponseType sendMessage = 
//		new UINode("Chat", 
//				response::setType, 
//				ResponseType.CHAT);

		UINodeLog log = new UINodeLog("", this, serverEndler);
		UINodeChooseUI menu = new UINodeChooseUI("Menu'", this); 
		UINodeSetRequestType placeFM = 
				new UINodeSetRequestType("Place family member", 
						request::setType, 
						RequestType.PLACEFAMILYMEMBER, this);

		UINodeChooseValue<ActionSpace> where = 
				new UINodeChooseValue<ActionSpace>("Where?",
						request::setWhere,
						() -> this.getModel().getBoard().getActionSpaces(),
						this);
		UINodeChooseValue<FamilyMember> which =
				new UINodeChooseValue<FamilyMember>("Which?",
						request::setWhich,
						() -> this.getPlayer().getFamilyMembers(),
						this);
		UINodeGetInput servants= 
				new UINodeGetInput("How many servants?",
						request::setServants, this);
		UINode talkToServer = new UINodeTalkToServer("Waiting for server response...", this);

		root= log.addSon(
				menu.addSon(
				  placeFM.addSon(
	                where.addSon(
	                  which.addSon(
	                	servants.addSon(
	                	  talkToServer)))))); 
		
		reset();
		System.out.println("Hi, this is Lorenzo!");
	}

	Player getPlayer() {
		return player;
	}

	Model getModel() {
		return model;
	}
	
	ServerResponse sendRequestToServer() throws IOException {
		return sendRequestToServer(request);
	}

	public ServerResponse sendRequestToServer(ClientRequest request) throws IOException { 
		serverHandler.setCROut(request);
	    ServerResponse srr = null; 
	    while (true) { 
	    	try {
	    		Thread.sleep(100); 
	    	} catch (InterruptedException e) {
	    		e.printStackTrace(); 
	    	} 
	    	srr = serverHandler.getSRIn(); 
	    	if (srr != null) return srr; 
	    } 
	} 

	public void run() throws IOException {
		while (true) {
			while (next != null) {
				next.run();
				next = next.getNextNode();
			}
			reset();
		}
	}

	private void reset() {
		next = root;
		request.cleanUp();
	}

//	public void setModelShell(ModelShell modelShell) {
//		 TODO Auto-generated method stub
//		ms = modelShell;
//	}

	public ClientRequest getRequest() {
		return request;
	}

	public void setModel(Model model2) {
		model = model2;
		hasModel = true;
	}

	public void setPlayer(Team playerTeam) {
		player = model.getPlayer(playerTeam);
		hasPlayer = true;
	}
}