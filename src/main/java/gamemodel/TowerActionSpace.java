package gamemodel;

public interface TowerActionSpace extends ActionSpace {

	Tower getTower();

	void attachDevelopmentCard(Card card);

	void giveCard(FamilyMember f);

}