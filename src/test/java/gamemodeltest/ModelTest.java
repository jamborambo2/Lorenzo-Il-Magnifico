package gamemodeltest;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import gamemodel.Model;
import gamemodel.Point;

public class ModelTest 
{
	Model model;
	Model model2;
	
	
	
	@Before
	public void setUp() throws Exception 
	{
		model=new Model(3);
		model2=new Model(3);
		
		
	}

	@Test
	public void pointsVictoryBoundedToMilitaryPointsTest() 
	{
		model.getPlayers().get(1).addPoint(new Point(0,0,0));
		model.getPlayers().get(2).addPoint(new Point(7,0,0));
		model.pointsVictoryBoundedToMilitaryPoints(model.getPlayers());
		assertEquals(new Point(7,0,5),model.getPlayers().get(0).getPoint());
		assertEquals(new Point(0,0,2),model.getPlayers().get(1).getPoint());
		assertEquals(new Point(0,0,2),model.getPlayers().get(2).getPoint());
	}

	@Test
	public void whoIsWinnerTest() 
	{
		
		model2.getPlayers().get(0).addPoint(new Point(0,0,7));
		model2.getPlayers().get(1).addPoint(new Point(7,0,5));
		model2.getPlayers().get(2).addPoint(new Point(7,0,10));
		
		model2.whoIsWinner(model2.getPlayers());
		assertEquals(new Point(7,0,10),model2.getPlayers().get(0).getPoint());
		assertEquals(new Point(0,0,7),model2.getPlayers().get(1).getPoint());
		assertEquals(new Point(7,0,5),model2.getPlayers().get(2).getPoint());
	}

	
	
}