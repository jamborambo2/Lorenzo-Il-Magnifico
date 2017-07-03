package gameview.cli;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.Team;
import gameview.ViewController;
import reti.ServerResponse;


public class CLIView {

	
	public static void main(String[] arg) throws IOException, InterruptedException, NotBoundException {		
		UITree uiTree;
		
//		uiTree = new UITree(hs);
		
		LinkedList<String> stringChoices = new LinkedList<>();
		LinkedList<Integer> intChoices = new LinkedList<>();
		LinkedList<ServerResponse> messages = new LinkedList<>();
		LinkedList<ServerResponse> responses = new LinkedList<>();

		Model m = new Model(2);
		m.setCurretPlayer(new Player(new Resource(0,0,0,0), m.getBoard(), Team.RED));

		messages.addLast(new ServerResponse(m));
		messages.addLast(new ServerResponse(m.getCurrentPlayer()));
		intChoices.addLast(0);
		intChoices.addLast(0);
		intChoices.addLast(0);
		stringChoices.addLast("0");
		responses.addLast(new ServerResponse());
		intChoices.addLast(4);

		uiTree = new UITree(intChoices, stringChoices, messages, responses);
		
		uiTree.run();
	}
}


