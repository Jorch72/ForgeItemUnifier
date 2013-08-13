package schmoller.unifier;

import java.util.List;

import schmoller.unifier.gui.GuiUnifierSettings;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;

public class UnifierHooks
{
	/**
	 * Called upon initial spawn and load of villagers
	 * @param list
	 */
	public static void onMerchantLoad(MerchantRecipeList list)
	{
		for(MerchantRecipe recipe : (List<MerchantRecipe>)list)
		{
			ModForgeUnifier.mappings.applyMapping(recipe.getItemToSell());
			ModForgeUnifier.mappings.applyMapping(recipe.getItemToBuy());
			if(recipe.getSecondItemToBuy() != null)
				ModForgeUnifier.mappings.applyMapping(recipe.getSecondItemToBuy());	
		}

		FMLLog.info("Merchant Load " + list.toString());
	}
	
	/**
	 * Called upon clicking the unifier options button in the main menu
	 * @param menu
	 */
	public static void onOpenGlobalOptions(GuiMainMenu menu)
	{
		Mappings.safeGuardOreDict();
		
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiUnifierSettings(true, ModForgeUnifier.globalMappings));
		
	}
}
