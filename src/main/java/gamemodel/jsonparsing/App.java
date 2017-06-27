package gamemodel.jsonparsing;

import java.util.*;



import gamemodel.actionSpace.*;
import gamemodel.card.*;
import gamemodel.Board;
import gamemodel.LeaderCard;

public class App{
	
	private static Board board=new Board();
	
    public static void main( String[] args )
    {	
    	//testAS();
    	testBCard();
    	testTCard();
    	//testCCard();
    	//testVCard();
    	//test();
    	//testLCard();
    } 
    
    private static void testpoints() {
    	List<Integer> Bcard= new CustomizationFileReader<Integer>("Config/FaithRequirements.json",new FaithRequirements()::parsing).parse();
    	for(Integer a:Bcard)
    		System.out.println(a);
		
	}

	private static void testEx() {
    	List<Excommunication> Bcard= new CustomizationFileReader<Excommunication>("Config/Excommunication.json",new ExcommunicationParsing()::parsing).parse();
    	for(Excommunication a:Bcard)
    		System.out.println(a);
		
	}

	private static void testCCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/CharacterCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    private static void testVCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/VentureCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    private static void testTCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    
    private static void testBCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing(board)::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    private static void testLCard() {
    	List<LeaderCard> Bcard= new CustomizationFileReader<LeaderCard>("Config/LeaderCards.json",new LeaderCardParsing(board)::parsing).parse();
    	for(LeaderCard a:Bcard)
    		System.out.println(a);
	}

	private static void testAS(){
    	List<ActionSpace> AS= new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing(board)::parsing).parse();
    	List<TowerActionSpace> AST=new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing(board)::parsing).parse();
    	for(ActionSpace a:AS)
    		System.out.println(a);
    	for(TowerActionSpace a:AST)
    		System.out.println(a);
    	
    }
}
