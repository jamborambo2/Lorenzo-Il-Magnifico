package gameview.gui;

import gamemodel.card.Card;
import gamemodel.card.VentureCard;
import gamemodel.effects.IstantEffect;
import gamemodel.effects.PointModify;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The VentureCardController object contains all the personalization we let players do about venture card,like
 * the name of the card,the price,the effects and so on
 * 
 */

public class VentureCardController 
{
	@FXML TextFlow cardName;
	@FXML TextFlow victoryPoints;
	@FXML TextFlow instantEffect;
	@FXML TextFlow cardPrice;
	
	public void initialize(Card card)
	{
		Text cardName=new Text(card.getName());
		cardName.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
		this.cardName.getChildren().add(cardName);	
		Text cardPrice;
		Font font=Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8);
		if(card.getResourcePrice()!=null){
			cardPrice=new Text(card.getResourcePrice().toString());
			cardPrice.setFont(font);
			this.cardPrice.getChildren().add(cardPrice);
		}
		if(card.getPointPrice()!=null){
			cardPrice=new Text(card.getPointPrice().toString());
			cardPrice.setFont(font);
			this.cardPrice.getChildren().add(cardPrice);
		}
		
		if(!card.getIstantEffect().isEmpty())
		{
			for(IstantEffect e:card.getIstantEffect()){
				Text instantEffect=new Text(e.toString()+"\n");
				instantEffect.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 8));
				this.instantEffect.getChildren().add(instantEffect);
			}
		}
		
		if(!((VentureCard)card).getPermanentEffects().isEmpty())
		{
			for(IstantEffect e:((VentureCard) card).getPermanentEffects()){
				Integer victoryPoint=(((PointModify) e).getPoints().getVictory());
				this.victoryPoints.getChildren().add(new Text(victoryPoint.toString()));
			}
		}		
	}
}





