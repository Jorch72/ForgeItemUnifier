package schmoller.unifier;

import java.io.File;
import java.util.Map.Entry;
import java.util.logging.Logger;

import schmoller.unifier.vanilla.CraftingProcessor;
import schmoller.unifier.vanilla.SmeltingProcessor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name="Forge Unifier", modid="ForgeUnifier", version="1.1", dependencies="after:*")
public class ModForgeUnifier
{
	public static Logger log;
	public static Mappings mappings;
	
	private Configuration mConfig;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLLog.makeLog("OreUnifier");
		log = FMLLog.getLogger().getLogger("OreUnifier");
		log.setUseParentHandlers(true);
		
		File configFile = event.getSuggestedConfigurationFile();
		mConfig = new Configuration(configFile);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		log.info("Loading mappings:");
		
		mappings = new Mappings();
		mappings.loadMappings(mConfig);
		
		for(Entry<String, ItemStack> entry : mappings.getMappings().entrySet())
		{
			ModContainer mod = Utilities.findOwningMod(entry.getValue());
			log.info(String.format("%s will be simplified to %s[%s][%d:%d]", entry.getKey(), Utilities.getSafeDisplayName(entry.getValue()), (mod != null ? mod.getName() : "Unknown"), entry.getValue().itemID, entry.getValue().getItemDamage()));
		}

		ProcessorManager manager = new ProcessorManager();
		manager.registerProcessor(new CraftingProcessor());
		manager.registerProcessor(new SmeltingProcessor());
		
		log.info("Starting re-mapping of items");
		manager.execute(mappings);
		log.info("Finished re-mapping of items");
		
//		CraftingRecipes.remapCrafting(mappings);
//		SmeltingRecipes.remapSmelting(mappings);
//		ChestLoot.remapLoot(mappings);
//		
//		// Do mod remapping
//		
//		event.buildSoftDependProxy("ThermalExpansion|Factory", ThermalExpansionRemapper.class.getName());
//		event.buildSoftDependProxy("Railcraft", RailcraftRemapper.class.getName());
//		event.buildSoftDependProxy("factorization", FactorizationRemapper.class.getName());
//		event.buildSoftDependProxy("IC2", IC2Remapper.class.getName());
//		event.buildSoftDependProxy("Forestry", ForestryRemapper.class.getName());
//		event.buildSoftDependProxy("Thaumcraft", ThaumcraftRemapper.class.getName());
//		event.buildSoftDependProxy("RedPowerCore", RP2Remapper.class.getName());
	}
}
