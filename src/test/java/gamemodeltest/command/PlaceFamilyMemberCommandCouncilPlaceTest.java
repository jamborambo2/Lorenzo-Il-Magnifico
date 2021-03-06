package gamemodeltest.command;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.*;
import gamemodel.actionSpace.*;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.TestEffects;
import gamemodel.player.Color;
import gamemodel.player.Player;
import gamemodel.player.Resource;
import gamemodel.player.Team;

/**
* The PlaceFamilyMemberCommandCouncilPlaceTest class represents the move checker for council place action space
* and tests the rule game set related to it
*
*/



public class PlaceFamilyMemberCommandCouncilPlaceTest {
	
	Board b;
	Player p1,p2;
	MemoryActionSpace a0;
	ActionSpace a1;
	IstantEffect e;
	GameError s;
	int id0,id1;
	static Model model;
	
	@BeforeClass
	public static void setUpClass(){
		model=new Model(4);
	}
	
	@Before
	public void setUp(){
		e=new TestEffects();
		b=new Board();
		b.setDice(1, 7, 7);
		p1=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p2=new Player(new Resource(5,5,5,5), b, Team.BLUE,model);
		a0=new MemoryActionSpace(0,5, e, ActionSpaceType.COUNCIL_PALACE);
		id0=a0.getId();
		a1=new MemoryActionSpace(1,0, e, ActionSpaceType.COUNCIL_PALACE);
		id1=a1.getId();
		p1.prepareForNewRound();
		p2.prepareForNewRound();
		b.addActionSpace(a0);	
		b.addActionSpace(a1);
		model.setCurretPlayer(p1);
	}
	
	
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),5));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
	}
	
	@Test
	public void testZeroServantsFail() {
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.FM_ERR_PA,s);		
	}
		
	@Test
	public void testSomeServants(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),5));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);
		assertEquals(new Resource(5,5,5,0),p1.getResource());
	}
	@Test 
	public void testTooMatchServant(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),7));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.RESOURCE_ERR_SERVANTS,s);
		assertEquals(new Resource(5,5,5,5),p1.getResource());
	}
	
	@Test
	public void testDoublePlaceSamePost() throws GameException{
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),5));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0));}
		catch(GameException e){s=e.getType();}
		assertEquals(null,s);		
	}
	
	@Test
	public void testDoublePlaceSamePostDifferentPlayer() throws GameException{
		List<Player> testplayers=new ArrayList<Player>();
		testplayers.add((Player) p1);
		testplayers.add((Player) p2);
		p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.WHITE),0));
		model.setCurretPlayer(p2);
		p2.placeFamilyMember(new Action(p2,a0,p2.getFamilyMember(Color.WHITE),0));
		assertEquals(a0.getPlayers(),testplayers);		
	}
	
	@Test
	public void tooMuchFM() throws GameException{
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.WHITE),0));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),0));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.ORANGE),0));
			p1.setAlradyPlaceFM(false);
			model.setCurretPlayer(p2);
			p2.placeFamilyMember(new Action(p2,a1,p2.getFamilyMember(Color.WHITE),0));
			model.setCurretPlayer(p1);
			p1.placeFamilyMember(new Action(p2,a1,p2.getFamilyMember(Color.BLACK),0));
		}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_MAX_FM,s);		
	}
	

}
	


