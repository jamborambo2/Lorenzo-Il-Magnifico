package gamemodel.card;

import java.io.Serializable;

import gamemodel.player.Player;
import gamemodel.player.Point;
import gamemodel.player.Resource;

/**
 * This and the CardRequirement class represent the resources that a player 
 * must have to take a card.
 */
public class Requirement implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Resource resource;
	private CardRequirement cardReq;
	private Point point;

	public Requirement(Resource resource) {
		this.resource = resource;
	}
	
	public Requirement(Point point){
		this.point=point;
	}
	
	public Requirement(CardRequirement cr) {
		this.cardReq = cr;
	}

	public Requirement(Point point, Resource resource, CardRequirement card) {
		this.resource=resource;
		this.point=point;
		this.cardReq=card;
	}

	public boolean isSatisfiedBy(Player player) {
		boolean satisfies = true;

		if (resource != null) {
			satisfies = player.getResource().isEnought(resource);
		}
		
		if(point!= null){
			satisfies = player.getPoint().isEnought(point);
		}
		
		if (cardReq != null) {
			satisfies = cardReq.isSatisfiedBy(player);
		}
		
		return satisfies;
	}

	@Override
	public String toString() {
		String s="";
		if(resource!=null)
			s+=resource;
		if(point!=null)
			s+=point;
		if(cardReq!=null)
			s+=cardReq;
		return s;
	}
	
}
