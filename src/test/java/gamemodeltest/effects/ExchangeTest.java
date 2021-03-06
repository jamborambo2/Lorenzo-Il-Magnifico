package gamemodeltest.effects;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.Model;
import gamemodel.command.GameException;
import gamemodel.effects.CouncilPrivileges;
import gamemodel.effects.Exchange;
import gamemodel.effects.IstantEffect;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;
import gamemodel.player.Team;

/**
* The ExchangeTest class tests all combinations of exchange effect
* 
*
*/


public class ExchangeTest 
{
	Board b;
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	Player p5;
	IstantEffect councilPrivileges;
	static Model model;
	
	@BeforeClass
	public static void setUpClass(){
		model=new Model(4);
	}
	
	@Before
	public void setUp() throws Exception 
	{
		b=new Board();
		councilPrivileges=new CouncilPrivileges(1);
		p1=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p2=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p3=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p4=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		p5=new Player(new Resource(5,5,5,5), b, Team.RED,model);
		
	}

	@Test
	public void testActivateRR() throws GameException 
	{
		Exchange effect=new Exchange(null,null,new Resource(5,0,0,0),new Resource(0,0,2,0),null);
		effect.activate(p1);
		assertEquals(new Resource(10,5,3,5),p1.getResource());
	}
	@Test
	public void testActivateRP() throws GameException 
	{
		Exchange effect=new Exchange(new Point(1,0,0),null,null,new Resource(1,0,0,0),null);
		effect.activate(p2);
		assertEquals(new Resource(4,5,5,5),p2.getResource());
		assertEquals(new Point(1,0,0),p2.getPoint());
	}
	@Test//(expected=java.lang.NullPointerException.class)
	public void testActivateRC() throws GameException 
	{
		Exchange effect=new Exchange(null,null,null,new Resource(1,0,0,0),councilPrivileges);
		effect.activate(p3);
		assertEquals(new Resource(4,6,6,5),p3.getResource());
		assertEquals(new Point(0,0,0),p3.getPoint());
	}
	@Test
	public void testActivatePRP() throws GameException 
	{
		Exchange effect=new Exchange(new Point(0,0,2),new Point(0,0,0),new Resource(2,0,0,0),null,null);
		effect.activate(p4);
		assertEquals(new Resource(7,5,5,5),p4.getResource());
		assertEquals(new Point(0,0,2),p4.getPoint());
	}
}
