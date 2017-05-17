package gamemodel;

public interface Board {

	void setupRound();
	void addActionSpace(RealActionSpace a);
	RealActionSpace getActionSpaces(int id);
	void addPlayer(Player player);
	String toString();
	
}