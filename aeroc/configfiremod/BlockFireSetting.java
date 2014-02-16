package aeroc.configfiremod;

public class BlockFireSetting {

	private int blockID;
	private int encouragement;
	private int flammability;
	
	public BlockFireSetting( int blockID, int encouragement, int flammability ){
		this.blockID = blockID;
		this.encouragement = encouragement;
		this.flammability = flammability;
	}
	public int getBlockID(){
		return this.blockID;
	}
	public int getEncouragement(){
		return this.encouragement;
	}
	public int getFlammability(){
		return this.flammability;
	}
}
