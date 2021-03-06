package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.HarvesterAndBuildings;
import gamemodel.effects.IstantEffect;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The TerritoryCardController object contains all the personalization we let players do about territory card,like
 * the name of the card,the price,the effects and so on
 * 
 */


public class TerritoryCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow actionValue;
	@FXML TextFlow instantEffect;
	@FXML TextFlow permanentEffect;
	
	public void initialize(Card card)
	{
		Text cardName=new Text(card.getName());
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);
		
		Integer actionValue=((HarvesterAndBuildings)card).getActionCost();
		this.actionValue.getChildren().add(new Text(actionValue.toString()));
		
		if(!card.getIstantEffect().isEmpty())
		{
			for(IstantEffect e:card.getIstantEffect()){
				Text instantEffect=new Text(e.toString()+"\n");
				instantEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
				this.instantEffect.getChildren().add(instantEffect);
			}
		}
		
		if(!((HarvesterAndBuildings)card).getPermanentEffects().isEmpty())
		{
			for(IstantEffect e:((HarvesterAndBuildings) card).getPermanentEffects()){
				Text permanentEffect=new Text(e.toString());
				permanentEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
				this.permanentEffect.getChildren().add(permanentEffect);
			}
		}
	}
}