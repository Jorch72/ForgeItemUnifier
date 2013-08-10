package schmoller.unifier.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

import schmoller.network.ModPacket;
import schmoller.network.NonReturnable;

public class ModPacketChangeMapping extends ModPacket implements NonReturnable
{
	public Map<String, ItemStack> newMappings;
	private int mId = -1;
	
	@Override
	public void write( DataOutput output ) throws IOException
	{
		output.writeInt(mId);
		output.writeInt(newMappings.size());
		for(Entry<String, ItemStack> entry : newMappings.entrySet())
		{
			output.writeUTF(entry.getKey());
			output.writeInt(entry.getValue().itemID);
			output.writeInt(entry.getValue().getItemDamage());
		}
	}

	@Override
	public void read( DataInput input ) throws IOException
	{
		mId = input.readInt();
		newMappings = new HashMap<String, ItemStack>();
		int toRead = input.readInt();
		
		while(toRead > 0)
		{
			String key = input.readUTF();
			int id = input.readInt();
			int data = input.readInt();
			
			ItemStack item = new ItemStack(id, 1, data);
			
			newMappings.put(key, item);
			
			--toRead;
		}
	}

	@Override
	public void setId( int id )
	{
		mId = id;
	}

	@Override
	public int getId()
	{
		return mId;
	}

}
