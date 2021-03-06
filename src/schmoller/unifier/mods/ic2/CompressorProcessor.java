package schmoller.unifier.mods.ic2;

import java.util.Map;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import schmoller.unifier.Mappings;

public class CompressorProcessor extends BasicIC2MachineProcessor
{
	@Override
	public String getName()
	{
		return "Compressor";
	}

	@Override
	public int applyMappings( Mappings mappings )
	{
		Map<IRecipeInput, RecipeOutput> recipes = Recipes.compressor.getRecipes();
		
		return apply(mappings, recipes);
	}

}
