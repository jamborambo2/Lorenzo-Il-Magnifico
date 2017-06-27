package gameview.cli;

import java.io.IOException;

import gamemodel.Model;
import gamemodel.Team;
import gameview.ViewController;
import reti.ClientRequest;
import reti.RequestType;
import reti.ServerResponse;

public class UINodeLog extends UINode 
{
	ViewController hs;
	
	public UINodeLog(String desc, UITree tree,ViewController hs) 
	{
		super(desc, tree);
		this.hs=hs;
	}
	
	@Override
	public void run() throws IOException
	{
		boolean run=true;
		ServerResponse sr=null;
		while(run)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			
			if(hs.hasMessage()) {		
				sr=hs.getMessage();
				// System.out.println(sr);
				switch (sr.getType()) {
				case NEW_MODEL:
					newModel(sr.getModel());
					
					break;
				case MESSAGE:
					System.out.print("Someone sent you this message: ");
					System.out.println(sr.getMessage());
					break;
				case PLAYER_ASSIGNED:
					if (!tree.hasModel) {
						System.out.println("They sent me the player but I still did not get the model...");
						break;
					}
					Team team = sr.getPlayerTeam();
					System.out.println("Your player got assigned, you're team: " + team);
					tree.setPlayer(team);
					System.out.println("Now get out of this log and play!");
					break;
				case LEADER:
					System.out.println(sr.getQuestion());
					tree.sendRequestToServer(new ClientRequest(CLIView.getString()));
					break;
				default:
					System.out.println("Should this message get here? " + sr);
					break;
				}
			}
			
			if(CLIView.inKeyboard.ready() && tree.hasModel && tree.hasPlayer) {
				run=false;
			}
		}
		
		super.run();
	}
	
	private void newModel(Model model){
		System.out.print("A new model has arrived... It says:");
		tree.setModel(model);
		switch(model.getState()){
		case GAME_FINISH:
			break;
		case PLAYER_PLAING:
			System.out.println("it's Player turn :"+model.getCurrentPlayer());
			break;
		case SET_UP_ROUND:
			System.out.println("the turn is finished:"+model.turn);
			break;
		case VATICAN_TIME:
			System.out.println("it's time to play with the Pope, unless you're too poor");
			System.out.println("yes or not???");
			tree.sendRequestToServer(new ClientRequest(CLIView.getString(),RequestType.VATICAN_REPORT));
			break;
		default:
			break;
		
		}
		
	}
}


