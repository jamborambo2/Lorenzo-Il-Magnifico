package gamemodel.effects;

import java.lang.reflect.Array;

import gamemodel.CardType;
import gamemodel.Player;
import gamemodel.Point;
import gamemodel.Resource;

public class ResourceForResource implements Effect 
{
	private CardType cardType;
	private Resource resourcesIn;
	private Point pointsIn;
	private Resource resourcesOut;
	private Point pointsOut;
	private int forEach;
	
	public static Effect constructor(String typeIn,Resource resourcesIn,Point pointsIn,Resource resourcesOut,Point pointsOut,int forEach)
	{
		Effect effect=null;
		CardType[] cardTypeArray=CardType.values();
		int length=Array.getLength(cardTypeArray);
		String[] cTypeArray=new String[length];
		int c;
		for (c=0;c<length;c++) 
			cTypeArray[c]=cardTypeArray[c].toString();
		for (c=0;c<length;c++) 
		{
			if(typeIn.equals(cTypeArray[c])==true)
			{
				if(resourcesOut==null)
					effect=new ResourceForResource(cardTypeArray[c],pointsOut,forEach);
				if(pointsOut==null)
					effect=new ResourceForResource(cardTypeArray[c],resourcesOut,forEach);
			}
		}
		if(typeIn.equals("Point")==true)	
		{
			if(resourcesOut==null)
					effect=new ResourceForResource(pointsIn,pointsOut,forEach);
			if(pointsOut==null)
					effect=new ResourceForResource(pointsIn,resourcesOut,forEach);
		}
		if(typeIn.equals("Resource")==true)	
		{
			if(resourcesOut==null)
					effect=new ResourceForResource(resourcesIn,pointsOut,forEach);
			if(pointsOut==null)
					effect=new ResourceForResource(resourcesIn,resourcesOut,forEach);
		}
		return effect;
	}

	public ResourceForResource(CardType cardType,Point pointsOut,int forEach)
	{
		this.cardType=cardType;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.resourcesOut=null;
	}
	public ResourceForResource(CardType cardType,Resource resourcesOut,int forEach)
	{
		this.cardType=cardType;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsIn=null;
		this.resourcesIn=null;
		this.pointsOut=null;
	}
	public ResourceForResource(Point pointsIn,Point pointsOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.resourcesIn=null;
	}
	public ResourceForResource(Point pointsIn,Resource resourcesOut,int forEach)
	{
		this.pointsIn=pointsIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.cardType=null;
		this.pointsOut=null;
		this.resourcesIn=null;
	}
	public ResourceForResource(Resource resourcesIn,Point pointsOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.pointsOut=pointsOut;
		this.forEach=forEach;
		this.cardType=null;
		this.resourcesOut=null;
		this.pointsIn=null;
	}
	public ResourceForResource(Resource resourcesIn,Resource resourcesOut,int forEach)
	{
		this.resourcesIn=resourcesIn;
		this.resourcesOut=resourcesOut;
		this.forEach=forEach;
		this.pointsOut=null;
		this.cardType=null;
		this.pointsIn=null;
	}
	
	private int count(int cardNumber,int forEach)
	{
		int c;
		int howManyTimes=0;
		for(c=1;c<=cardNumber;c++)
		{
			if(c%forEach==0)
				howManyTimes++;
		}
		return howManyTimes;
	}
	private int foo(Point points,int forEach)
	{
		int c;
		int howManyTimes=0;
		if(points.getFaith()!=0)
		{
			for(c=1;c<=points.getFaith();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		if(points.getMilitary()!=0)
		{
			for(c=1;c<=points.getMilitary();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		if(points.getVictory()!=0)
		{
			for(c=1;c<=points.getVictory();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		return howManyTimes;
	}
	private int foo(Resource resources,int forEach)
	{
		int c;
		int howManyTimes=0;
		if(resources.getGold()!=0)
		{
			for(c=1;c<=resources.getGold();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		if(resources.getServant()!=0)
		{
			for(c=1;c<=resources.getServant();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		if(resources.getStone()!=0)
		{
			for(c=1;c<=resources.getStone();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		if(resources.getWood()!=0)
		{
			for(c=1;c<=resources.getWood();c++)
			{
				if(c%forEach==0)
					howManyTimes++;
			}
		}
		return howManyTimes;
	}
	
	@Override
	public String toString() {
		return "ResourceForResource [cardType=" + cardType + ", resourcesIn=" + resourcesIn + ", pointsIn=" + pointsIn
				+ ", resourcesOut=" + resourcesOut + ", pointsOut=" + pointsOut + ", forEach=" + forEach + "]";
	}

	@Override
	public void activate(Player player)
	{
		if(cardType!=null)
		{
			int howManyTimes;
			howManyTimes=count(player.contCard(cardType),forEach);
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addResources(resourcesOut);
			}
		}
		if(pointsIn!=null)
		{
			int howManyTimes;
			howManyTimes=foo(pointsIn,forEach);
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addResources(resourcesOut);
			}
		}
		if(resourcesIn!=null)
		{
			int howManyTimes;
			howManyTimes=foo(resourcesIn,forEach);
			int c;
			if(pointsOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addPoint(pointsOut);
			}
			if(resourcesOut!=null)
			{
				for(c=0;c<howManyTimes;c++)
					player.addResources(resourcesOut);
			}
		}
	}
}

