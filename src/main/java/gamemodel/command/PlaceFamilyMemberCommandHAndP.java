package gamemodel.command;

import gamemodel.Action;
import gamemodel.Board;
import gamemodel.actionSpace.ActionSpaceType;
import gamemodel.actionSpace.MemoryActionSpace;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.player.Color;
import gamemodel.player.FamilyMember;
import gamemodel.player.Resource;

public class PlaceFamilyMemberCommandHAndP implements Command {
	protected FamilyMember f;
	private int servant;
	MemoryActionSpace h;
	protected Action action;
	
	public PlaceFamilyMemberCommandHAndP(Board board,int id,FamilyMember f, int servant) {
		this.f=f;
		this.servant=servant;
		// this.h=board.getActionSpace(id);
	}

	public PlaceFamilyMemberCommandHAndP(Action action) {
		this.f = action.getFm();
		this.servant = action.getServants();
		this.h = (MemoryActionSpace) action.getActionSpace();
		this.action=action;
	}
	
	protected boolean IsEnoughtStrong(){
		return(f.getActionpoint()>=h.getActionCost());
	}

	protected boolean controlServant() throws GameException{
		if(servant>=0)
			if(f.getPlayer().isEnoughtResource(new Resource(0,0,0,servant))){
				f.getPlayer().subResources(new Resource(0,0,0,servant));
				return true;
			}
		return false;			
	}

	@Override
	public void isLegal() throws GameException {
			if(!f.isUsed())
				if(h.controlPlayer(f))
					if(IsEnoughtStrong())
						if(controlServant())
							if (f.getColor() == Color.STRANGE) {
								f.getPlayer().getPersonalBonusTile().activate(f.getPlayer(),h.getType());								
								cardEffect(h.getType());
							}
							else if(h.isAccessible(action)) {
								f.getPlayer().getFamilyMember(f.getColor()).use();
								f.getPlayer().getPersonalBonusTile().activate(f.getPlayer(),h.getType());
								cardEffect(h.getType());
								h.occupy();
								h.addPlayer(f);
							}
							else{
								f.getPlayer().getFamilyMember(f.getColor()).use();
								f.setActionpoint(f.getActionpoint()-3);
								f.getPlayer().getPersonalBonusTile().activate(f.getPlayer(),h.getType());
								cardEffect(h.getType());
								h.addPlayer(f);
							}
						else throw new GameException(GameError.RESOURCE_ERR_SERVANTS);
					else throw new GameException(GameError.FM_ERR_PA);
				else throw new GameException(GameError.SA_ERR_FM);
			else throw new GameException(GameError.FM_ERR_USE);
		}

	protected void cardEffect(ActionSpaceType type) throws GameException {
		
		if(type==ActionSpaceType.HARVEST){			
			for(int i=0;i<f.getPlayer().getTerritories().size();i++){
				HarvesterAndBuildings c=(HarvesterAndBuildings)f.getPlayer().getTerritories().get(i);
				if(f.getActionpoint()>=c.getActionCost())
					c.activePermanentEffect(f.getPlayer());
			}
				
		}
		if(type==ActionSpaceType.PRODUCTION){
			for(int i=0;i<f.getPlayer().getBuildings().size();i++){
				HarvesterAndBuildings c=(HarvesterAndBuildings)f.getPlayer().getBuildings().get(i);
				if(f.getActionpoint()>=c.getActionCost())
					c.activePermanentEffect(f.getPlayer());
			}			
		}		
	}
		
}
