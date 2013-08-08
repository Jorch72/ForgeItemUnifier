package schmoller.unifier.mods.ic2;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

import ic2.api.recipe.Recipes;
import schmoller.unifier.IProcessor;
import schmoller.unifier.Mappings;

public class CompressorProcessor implements IProcessor
{
	@Override
	public String getName()
	{
		return "Compressor";
	}

	@Override
	public int applyMappings( Mappings mappings )
	{
		int remapCount = 0;
		
		Map<ItemStack, Object> recipes = Recipes.compressor.getRecipes();
		
		for(Entry<ItemStack, Object> recipe : recipes.entrySet())
		{
			if(recipe.getValue() instanceof ItemStack)
			{
				if(mappings.applyMapping((ItemStack)recipe.getValue()))
					++remapCount;
			}
		}
		
		return remapCount;
	}

}
