package schmoller.unifier.gui;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.ModContainer;

import schmoller.unifier.ModForgeUnifier;
import schmoller.unifier.OreCategory;
import schmoller.unifier.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringTranslate;

public class GuiUnifierSettings extends GuiScreen
{
	private GuiOreList mOreList = null;
	private OreCategory mSelected = OreCategory.Ingots;
	private RenderItem renderItem = new RenderItem();
	
	private long mLastClickTime = 0;
	private int mLastClicked = -1; 
	private boolean mHasClicked = false;
	
	private boolean mEditable = true;

	public static final ItemStack unknownItem = new ItemStack(Block.bedrock);
	
	public GuiUnifierSettings(boolean editable)
	{
		allowUserInput = true;
		mEditable = editable;
		if(mEditable)
			ModForgeUnifier.mappings.beingModify();
	}
	
	@Override
	public void onGuiClosed()
	{
		if(mEditable)
			ModForgeUnifier.mappings.endModify();
	}
	
	@Override
	public void initGui()
	{
		int space = width - 40;
		
		int size = space / OreCategory.values().length;
		
		int totalSize = (size + 1) * OreCategory.values().length;
		
		for(OreCategory category : OreCategory.values())
		{
			GuiButton button = new GuiButton(category.ordinal(), (width - totalSize) / 2 + (size + 1) * category.ordinal(), 35, size, 20, category.name());
			if(category == mSelected)
				button.enabled = false;
			
			buttonList.add(button);
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height - 25, StringTranslate.getInstance().translateKey("gui.done")));
		//Mouse.setNativeCursor(Cursor.getDefaultCursor());
		setOreCategory(mSelected);
	}
	
	private void setOreCategory(OreCategory category)
	{
		mOreList = new GuiOreList(this, 20, 60, width / 2 - 20, height - 90, ModForgeUnifier.mappings, category);
		mLastClicked = -1;
	}
	
	Minecraft getMC()
	{
		return mc;
	}
	
	FontRenderer getFontRenderer()
	{
		return fontRenderer;
	}
	
	@Override
	protected void actionPerformed( GuiButton button )
	{
		if(button.id < OreCategory.values().length)
		{
			if(button.id != mSelected.ordinal())
			{
				((GuiButton)buttonList.get(mSelected.ordinal())).enabled = true;
				mSelected = OreCategory.values()[button.id];
				button.enabled = false;
				setOreCategory(mSelected);
			}
		}
		else if (button.id == 200)
        {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(null);
        }
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	void drawHoveringText(List par1List, int xCoord, int yCoord, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = xCoord + 12;
            int j1 = yCoord - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > this.width)
            {
                i1 -= 28 + k;
            }
            
            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            renderItem.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            renderItem.zLevel = 0.0F;
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            //RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	void drawItemStack(ItemStack item, int x, int y)
	{
		
		renderItem.zLevel = 100;
		renderItem.renderItemIntoGUI(fontRenderer, mc.renderEngine, item, x, y);
		renderItem.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, item, x, y);
		renderItem.zLevel = 0;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
	}
	
	@Override
	public void drawScreen( int mouseX, int mouseY, float par3 )
	{
		drawDefaultBackground();
        
        Tessellator tes = Tessellator.instance;
        mc.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
//        tes.startDrawingQuads();
//        tes.setColorRGBA_I(4210752, 255);
//        tes.addVertexWithUV(0.0D, 60.0D, zLevel, 0.0D, 60 / 32D);
//        tes.addVertexWithUV((double)width, 60.0D, zLevel, width / 32D, 60 / 32D);
//        tes.addVertexWithUV((double)width, 0.0D, zLevel, width / 32D, 0.0D);
//        tes.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, 0.0D);
//        tes.draw();
        
        drawCenteredString(this.fontRenderer, "Forge Unifier Options", width / 2, 15, 0xffffff);
        
        super.drawScreen(mouseX, mouseY, par3);
        
        if(mOreList != null)
		{
			Entry<String, Set<ItemStack>> selected = mOreList.getSelected();
			ItemStack hoverItem = null;
			
			if(selected != null)
			{
				drawCenteredString(this.fontRenderer, EnumChatFormatting.GOLD + selected.getKey(), (int)(width * 0.75), 65, 0xffffff);
				
				ItemStack item = ModForgeUnifier.mappings.getMapping(selected.getKey());

				drawString(this.fontRenderer, "Using: ", (int)((width + 10) / 2), 85, 0xffffff);
				
				if(item != null)
				{
					drawItemStack(item, (int)((width + 80) / 2), 80);
					
					drawString(this.fontRenderer, item.getDisplayName(), (int)((width + 130) / 2), 80, 0xffffff);
					ModContainer container = Utilities.findOwningMod(item);
					
					if(container == null)
						drawString(this.fontRenderer, EnumChatFormatting.YELLOW + "Unknown", (int)((width + 130) / 2), 91, 0xffffff);
					else
						drawString(this.fontRenderer, EnumChatFormatting.YELLOW + container.getName(), (int)((width + 130) / 2), 91, 0xffffff);
					
				}
				else
				{
					// TODO: Draw unknown symbol here
					drawItemStack(unknownItem, (int)((width + 80) / 2), 80);
					
					drawString(this.fontRenderer, "Not mapped", (int)((width + 130) / 2), 80, 0xffffff);
					drawString(this.fontRenderer, EnumChatFormatting.YELLOW + "Unknown", (int)((width + 130) / 2), 91, 0xffffff);
				}
				
				if(mEditable)
				{
					drawString(this.fontRenderer, "Available Choices:", (int)((width + 10) / 2), 110, 0xffffff);
					
					int xx = ((width + 10) / 2);
					int yy = 125;
					
					int index = 0;
					
					{
						if(mouseX >= xx-1 && mouseX < xx + 17 && mouseY >= yy-1 && mouseY < yy + 17)
						{
							hoverItem = unknownItem;
							drawRect(xx-1, yy-1, xx + 17, yy + 17, -2130706433);
							
							if(Mouse.isButtonDown(0) && !mHasClicked)
							{
								boolean doubleClick = (index == mLastClicked && System.currentTimeMillis() - mLastClickTime < 250L);
								
								mHasClicked = true;
	
								if (doubleClick)
								{
									// Select it
									
									ModForgeUnifier.mappings.changeMapping(selected.getKey(), new ItemStack(0, 0, 0));
								}
								
								mLastClickTime = System.currentTimeMillis();
								mLastClicked = index;
							}
						}
						
						drawItemStack(unknownItem, xx, yy);
						
						xx += 18;
						
						if(xx + 18 >= width - 20)
						{
							xx = ((width + 10) / 2);
							yy += 18;
						}
						
						++index;
					}
					
					for(ItemStack option : selected.getValue())
					{
						if(mouseX >= xx-1 && mouseX < xx + 17 && mouseY >= yy-1 && mouseY < yy + 17)
						{
							hoverItem = option;
							drawRect(xx-1, yy-1, xx + 17, yy + 17, -2130706433);
							
							if(Mouse.isButtonDown(0) && !mHasClicked)
							{
								boolean doubleClick = (index == mLastClicked && System.currentTimeMillis() - mLastClickTime < 250L);
								
								mHasClicked = true;
	
								if (doubleClick)
								{
									// Select it
									ModForgeUnifier.mappings.changeMapping(selected.getKey(), option);
								}
								
								mLastClickTime = System.currentTimeMillis();
								mLastClicked = index;
							}
						}
						
						drawItemStack(option, xx, yy);
						
						xx += 18;
						
						if(xx + 18 >= width - 20)
						{
							xx = ((width + 10) / 2);
							yy += 18;
						}
						
						++index;
					}
				}
			}
			else
			{
				drawCenteredString(this.fontRenderer, "Nothing selected", (int)(width * 0.75), 65, 0xffffff);
			}
			
			mOreList.drawScreen(mouseX, mouseY, par3);
			
			if(hoverItem != null)
			{
				List list;
				if(hoverItem == unknownItem)
				{
					list = new ArrayList<String>();
					list.add(EnumChatFormatting.YELLOW + "No Mapping");
				}
				else
				{
					list = hoverItem.getTooltip(this.mc.thePlayer, false);
					for (int k = 0; k < list.size(); ++k)
			        {
			            if (k == 0)
			                list.set(k, "\u00a7" + Integer.toHexString(hoverItem.getRarity().rarityColor) + (String)list.get(k));
			            else
			                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
			        }
					
					ModContainer container = Utilities.findOwningMod(hoverItem);
					
					if(container == null)
						list.add(EnumChatFormatting.YELLOW + "Unknown");
					else
						list.add(EnumChatFormatting.YELLOW + container.getName());
				}
				
				drawHoveringText(list, mouseX, mouseY, fontRenderer);
			}
		}
        
        if(!Mouse.isButtonDown(0))
        {
        	mHasClicked = false;
        }
	}
}
