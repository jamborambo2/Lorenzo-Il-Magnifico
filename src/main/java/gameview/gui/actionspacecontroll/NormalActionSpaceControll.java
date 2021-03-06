package gameview.gui.actionspacecontroll;


import gamemodel.actionSpace.ActionSpace;
import gamemodel.effects.IstantEffect;
import gamemodel.player.FamilyMember;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The NormalActionSpaceControll object contains all the personalization we let players do about, for example, 
 * the towers action spaces, like  the action value and its immediate effect. This class also contains the logic to show family
 * members in action spaces
 */


public class NormalActionSpaceControll implements ActionSpaceControll {
	
	@FXML TextFlow actionCost;
	@FXML TextFlow effect;
	@FXML Pane familyMember;
	FamilyMember fm;
	
	@Override
	public void initialize(ActionSpace as){
		fm=as.getFamilyMember();
		if(fm!=null && familyMember.getChildren().size()<=1){
			Pane p=new Pane();
			p.setLayoutX(33);
			p.setLayoutY(33);
			MakeFM.makeFM(p, fm);
			familyMember.getChildren().add(p);
		}
		Integer actionCost=as.getActionCost();
		this.actionCost.getChildren().add(new Text(actionCost.toString()));
		String effectText="";
		if(!as.getEffects().isEmpty()){
			for(IstantEffect e:as.getEffects())
				effectText+=e.toString();
			effect.getChildren().add(new Text(effectText));	
		}	
	}

}
