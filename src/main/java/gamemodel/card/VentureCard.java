package gamemodel.card;

import java.util.List;

import gamemodel.command.GameException;
import gamemodel.effects.IstantEffect;
import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

public class VentureCard extends Card {

	private static final long serialVersionUID = 1L;
	private List<IstantEffect> permanentEffect;

	public VentureCard(int id,String name, int period, Resource resourceRequirement, Resource resourcePrice, Point point,
			Point pointPrice, List<IstantEffect> istantEffects,List<IstantEffect> permanentEffects, CardType type) {
		super(id,name, period, resourceRequirement, resourcePrice, point, pointPrice, istantEffects, type);
		this.permanentEffect=permanentEffects;
	}
	
	public void activePermanentEffect(Player p) throws GameException {
		for(IstantEffect e:this.permanentEffect)
			e.activate(p);	
	}


	public List<IstantEffect> getPermanentEffects() {
		return this.permanentEffect;
	}
	
	@Override
	public String toString(){
		String str = "";
		str +=this.type+"\n";
		str +="id:"+this.id+" "+this.name+"\n";
		if(resourceRequirement!=resourcePrice)
			str +="resource requirement-> "+this.resourceRequirement+ "\n";
		if(resourcePrice!=null)
			str +="resource price-> "+this.resourcePrice+ "\n";	
		if(pointRequirement!=pointPrice)
			str +="point requirement-> "+this.pointRequirement+ "\n";
		if(pointPrice!=null)
			str +="point price-> "+this.pointPrice+ "\n";
		if(this.istantEffect!=null)
			str +="istant effect-> "+this.istantEffect+ "\n";		
		if(this.permanentEffect!=null)
			str +="Action effect-> "+this.permanentEffect+ "\n";		
		return str;
	}

	/*@Override
	public String toString() {
		return "HarvesterAndBuildings [name=" + name + ", resourceRequirement="
				+ resourceRequirement + ", resourcePrice=" + resourcePrice + ", pointRequirement=" + pointRequirement
				+ ", pointPrice=" + pointPrice + ", istantEffect=" + istantEffect + ", type=" + type + ", id=" + id + ", requirementCard=" + requirementCard + "]";
	}*/

}
