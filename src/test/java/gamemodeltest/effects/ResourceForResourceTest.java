package gamemodeltest.effects;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import gamemodel.Board;
import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.ResourceForResource;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;
import gamemodel.player.Team;


/**
* The ResourceforResourceTest class tests all combinations of the following kind of effect: "for each something
* you have,receive 2 of something else".
* 
*
*/



public class ResourceForResourceTest 
{
	Board b;
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	Player p5;
	Player p6;
	
	@Before
	public void setUp() throws Exception 
	{
		b=new Board();
		p1=new Player(new Resource(5,5,5,5), b, Team.RED);
		p2=new Player(new Resource(7,0,4,13), b, Team.RED);
		p3=new Player(new Resource(2,3,4,5), b, Team.RED);
		p4=new Player(new Resource(150,7,8,9), b, Team.RED);
		p5=new Player(new Resource(5,5,5,5), b, Team.RED);
		p6=new Player(new Resource(0,0,0,0), b, Team.RED);
	}
	
	@Test
	public void testActivatePperP() throws GameException 
	{
		p1.addPoint(new Point(0,7,0));
		IstantEffect effect=ResourceForResource.constructor("Point",null,new Point(0,1,0),null,new Point(0,0,5),2);
		effect.activate(p1);
		assertEquals(new Point(0,7,15),p1.getPoint());
	}
	
	@Test
	public void testActivateRperR() throws GameException
	{
		IstantEffect effect=ResourceForResource.constructor("Resource",new Resource(0,0,1,0),null,new Resource(0,5,0,0),null,3);
		effect.activate(p2);
		assertEquals(new Resource(7,5,4,13),p2.getResource());
	}
	
	@Test
	public void testActivatePperR() throws GameException 
	{
		p3.addPoint(new Point(0,10,0));
		IstantEffect effect=ResourceForResource.constructor("Point",null,new Point(0,1,0),new Resource(0,40,3,0),null,3);
		effect.activate(p3);
		assertEquals(new Resource(2,123,13,5),p3.getResource());
	}
	
	@Test
	public void testActivateRperP() throws GameException 
	{
		IstantEffect effect=ResourceForResource.constructor("Resource",new Resource(1,0,0,0),null,null,new Point(10,3,4),5);
		effect.activate(p4);
		assertEquals(new Point(300,90,120),p4.getPoint());
	}
	
	@Test
	public void testActivateCperP() throws GameException
	{
		for(int c=0;c<372;c++)
			p5.getTerritories().add(null);
		IstantEffect effect=ResourceForResource.constructor("TERRITORY",null,null,null,new Point(10,3,4),52);
		effect.activate(p5);
		assertEquals(new Point(70,21,28),p5.getPoint());
	}
	
	@Test
	public void testActivateCperR() throws GameException
	{
		for(int c=0;c<19;c++)
			p6.getVentures().add(null);
		IstantEffect effect=ResourceForResource.constructor("VENTURE",null,null,new Resource(0,9,27,89),null,10);
		effect.activate(p6);
		assertEquals(new Resource(0,9,27,89),p6.getResource());
	}
}
