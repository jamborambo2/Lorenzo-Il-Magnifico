package gamemodeltest.command;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.*;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.ActionSpace;
import gamemodel.command.GameError;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.TestEffects;
import gamemodel.player.Color;
import gamemodel.player.Player;
import gamemodel.player.Resource;
import gamemodel.player.Team;

/**
* The PlaceFamilyMemberCommandMarketTest class represents the move checker for market action
* space and tests the rule game set related to it
*
*/


public class PlaceFamilyMemberCommandMarketTest {
	
	Board b;
	Player p1;
	ActionSpace a0,a1;
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
		b.setDice(1, 7, 0);
		p1=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		a0=new ActionSpace(0,5, e, ActionSpaceType.MARKET);
		a1=new ActionSpace(1,0, e, ActionSpaceType.MARKET);
		p1.prepareForNewRound();
		model.setCurretPlayer(p1);
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
	public void testDoublePlaceSamePost(){
		try{p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),5));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.WHITE),5));}
		catch(GameException e){s=e.getType();}
		assertEquals(GameError.SA_ERR,s);
	}
	
	@Test
	public void testDoubleUseFamiliare(){
		try{p1.placeFamilyMember(new Action(p1,a0,p1.getFamilyMember(Color.BLACK),5));
			p1.setAlradyPlaceFM(false);
			p1.placeFamilyMember(new Action(p1,a1,p1.getFamilyMember(Color.BLACK),5));}
		catch(GameException e){s=e.getType();}		
		assertEquals(GameError.FM_ERR_USE,s);		
	}

}
