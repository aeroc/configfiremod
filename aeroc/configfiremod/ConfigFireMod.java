package aeroc.configfiremod;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import aeroc.drmcraft.CommonProxy;
import aeroc.drmcraft.DRMCraft;
import aeroc.drmcraft.handler.PacketHandler;
import aeroc.drmcraft.manager.BlockManager;
import aeroc.drmcraft.manager.RenderManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod( modid="configfiremod", name="Config Fire Mod", version="0.1" )

@NetworkMod( clientSideRequired = false, serverSideRequired = false )

public class ConfigFireMod {
	
	private static ArrayList blockList = new ArrayList();

	@Instance( "FireMod" )
	public static ConfigFireMod instance;
	
	@EventHandler
	public void preInit( FMLPreInitializationEvent event ){
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		ConfigFireMod.parseConfigString( config.get( "FireSettings", "BlockString", "" ).getString() );
		config.save();
	}
	
	@EventHandler
	public void init( FMLInitializationEvent event ){
		ConfigFireMod.instance = this;
		
	}
	@EventHandler
	public void postInit( FMLPostInitializationEvent event ){
		for( int i = 0; i < ConfigFireMod.blockList.size(); i++ ){
			BlockFireSetting blockFireSetting = (BlockFireSetting) ConfigFireMod.blockList.get(i);
			Block.setBurnProperties( blockFireSetting.getBlockID(), blockFireSetting.getEncouragement(), blockFireSetting.getFlammability() );
		}
	}
	public static void parseConfigString( String parseList ){
		String[] firstSplit = parseList.split( ";" );
		for( int i = 0; i < firstSplit.length; i++ ){
			if( firstSplit[i].isEmpty() ){
				return;
			}
			String[] secondSplit = firstSplit[i].split( "," );
			if( secondSplit.length != 3 ){
				return;
			}
			if( secondSplit[0].isEmpty() || secondSplit[1].isEmpty() || secondSplit[2].isEmpty() ){
				return;
			}
			try{
				int blockID = Integer.parseInt( secondSplit[0] );
				int encouragement = Integer.parseInt( secondSplit[1] );
				int flammability = Integer.parseInt( secondSplit[2] );

				if( blockID < 0 || blockID > 4096 || encouragement < 0 || flammability < 0 ){
					return;
				}
				ConfigFireMod.blockList.add( new BlockFireSetting( blockID, encouragement, flammability ) );
			}
			catch( Exception e ){
				System.out.println( "ConfigFireMod failed to parse config partial string: " + firstSplit[i] );
			}
		}
	}
}
