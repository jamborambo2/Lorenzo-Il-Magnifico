package gameModel;

public interface TowerActionSpace extends ActionSpace {

	Tower getTower();

	void attachDevelopmentCard(Card card);

}