package schmoller.unifier.asm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.launchwrapper.LaunchClassLoader;

import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.FMLInjectionData;

public class NameHelper
{
	public static TypeName guiMainMenu = new TypeName("net.minecraft.client.gui.GuiMainMenu");
	public static TypeName guiButton = new TypeName("net.minecraft.client.gui.GuiButton");
	public static TypeName entityVillager = new TypeName("net.minecraft.entity.passive.EntityVillager");
	public static TypeName merchantRecipeList = new TypeName("net.minecraft.village.MerchantRecipeList");
	public static TypeName nbtTagCompound = new TypeName("net.minecraft.nbt.NBTTagCompound");

	public static ObjectName initGui = new ObjectName(NameType.Method, guiMainMenu, "initGui");
	public static ObjectName actionPerformed = new ObjectName(NameType.Method, guiMainMenu, "actionPerformed");
	public static ObjectName buttonList = new ObjectName(NameType.Field, guiMainMenu, "buttonList");
	public static ObjectName width = new ObjectName(NameType.Field, guiMainMenu, "width");
	public static ObjectName insertionPointVar = new ObjectName(NameType.Field, guiMainMenu, "field_104025_t");
	
	public static ObjectName id = new ObjectName(NameType.Field, guiButton, "id");
	
	public static ObjectName readEntityFromNBT = new ObjectName(NameType.Method, entityVillager, "readEntityFromNBT");
	public static ObjectName addDefaultEquipmentAndRecipies = new ObjectName(NameType.Method, entityVillager, "addDefaultEquipmentAndRecipies");
	public static ObjectName buyingList = new ObjectName(NameType.Field, entityVillager, "buyingList");
	
	private static boolean mIsObfuscated = false;
	private static DefaultArtifactVersion mVersion;
	
	static
	{
		VersionRange version = VersionParser.parseRange("1.6.4");
		guiMainMenu.addName(version, "blt");
		guiButton.addName(version, "aut");
		entityVillager.addName(version, "ub");
		merchantRecipeList.addName(version, "abm");
		nbtTagCompound.addName(version, "by");
		
		initGui.addName(version, "A_");
		actionPerformed.addName(version, "a");
		buttonList.addName(version, "i");
		width.addName(version, "g");
		insertionPointVar.addName(version, "u");
		
		id.addName(version, "g");
		
		readEntityFromNBT.addName(version, "a");
		addDefaultEquipmentAndRecipies.addName(version, "q");
		buyingList.addName(version, "bu");
	}
	
	static void init()
	{
		mVersion = new DefaultArtifactVersion((String)FMLInjectionData.data()[4]);
		try
        {
			mIsObfuscated = ((LaunchClassLoader)NameHelper.class.getClassLoader()).getClassBytes("net.minecraft.world.World") == null;
        }
        catch(IOException iox)
        {
        	mIsObfuscated = true;
        }
	}
	
	public enum NameType
	{
		Field,
		Method
	}
	
	public static class TypeName
	{
		private String mName;
		private HashMap<VersionRange, String> mObfuscatedNames = new HashMap<VersionRange, String>();
		
		public TypeName(String name)
		{
			mName = name;
		}
		
		public String getName()
		{
			if(mIsObfuscated)
			{
				for(Entry<VersionRange, String> entry : mObfuscatedNames.entrySet())
				{
					if(entry.getKey().containsVersion(mVersion))
						return entry.getValue();
				}
				return null;
			}
			else
				return mName;
		}
		
		public String getPath()
		{
			return getName().replaceAll("\\.", "/");
		}
		
		public String getDesc()
		{
			return "L" + getName().replaceAll("\\.", "/");
		}
		
		public void addName(VersionRange version, String name)
		{
			mObfuscatedNames.put(version, name);
		}
	}
	
	public static class ObjectName
	{
		private String mName;
		private Object[] mDesc;
		
		private NameType mType;
		private TypeName mOwner;
		private HashMap<VersionRange, String> mObfuscatedNames = new HashMap<VersionRange, String>();
		
		public ObjectName(NameType type, TypeName owner, String name, Object... desc)
		{
			mName = name;
			mType = type;
			mOwner = owner;
			mDesc = desc;
		}
		
		public String getName()
		{
			if(mIsObfuscated)
			{
				for(Entry<VersionRange, String> entry : mObfuscatedNames.entrySet())
				{
					if(entry.getKey().containsVersion(mVersion))
						return entry.getValue();
				}
				return null;
			}
			else
				return mName;
		}
		
		public void addName(VersionRange version, String name)
		{
			mObfuscatedNames.put(version, name);
		}
	}
}
