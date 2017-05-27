package gamemodel.jsonparsing;

import java.util.*;


import gamemodel.card.*;
import gamemodel.ActionSpace.*;

public class App{
	
    public static void main( String[] args )
    {
    	//testAS();
    	//testBCard();
    	testTCard();
    	
    } 
    
    private static void testTCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/TerritoryCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}
    
    
    private static void testBCard() {
    	List<Card> Bcard= new CustomizationFileReader<Card>("Config/BuildingCards.json",new CardParsing()::parsing).parse();
    	for(Card a:Bcard)
    		System.out.println(a);
	}


	private static void testAS(){
    	List<ActionSpace> AS= new CustomizationFileReader<ActionSpace>("Config/ActionSpace.json",new ASParsing()::parsing).parse();
    	List<TowerActionSpace> AST=new CustomizationFileReader<TowerActionSpace>("Config/TowerActionSpace.json",new TowerASParsing()::parsing).parse();
    	for(ActionSpace a:AS)
    		System.out.println(a);
    	for(TowerActionSpace a:AST)
    		System.out.println(a);
    	
    }
}
