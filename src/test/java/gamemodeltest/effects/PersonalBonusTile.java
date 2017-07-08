package gamemodeltest.effects;

import static org.junit.Assert.*;

import org.junit.Test;

import gamemodel.Board;
import gamemodel.Model;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;
import gamemodel.Team;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.command.GameException;

public class PersonalBonusTile 
{
	Player player;
	Board board;
	Model model;
	
	@Test
	public void test() 
	{
		board=new Board();
		model=new Model(2);
		player=new Player(new Resource(1,2,3,4),board,Team.RED,model);
		try {
			player.getPersonalBonusTile().activate(player,ActionSpaceType.HARVEST);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(new Resource(1,3,4,5),player.getResource());
		try {
			player.getPersonalBonusTile().activate(player, ActionSpaceType.PRODUCTION);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(new Resource(3,3,4,5),player.getResource());
		assertEquals(new Point(1,0,0),player.getPoint());
	}

}
