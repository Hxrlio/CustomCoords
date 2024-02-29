package mc.hxrl.customcoords.logic;

import mc.hxrl.customcoords.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class CoordLogic {
	
	public static String[] coordCalcXZ(int x, int z) {
		
		String[] xz = {"",""};
		//if we don't show x & z they can stay blank
		if (!Config.SHOW_XZ.get()) {
			return xz;
		}
		
		Minecraft client = Minecraft.getInstance();
		Entity player = client.getCameraEntity();
		//if something's gone wrong, leave them blank
		if (player == null) {
			return xz;
		}
		//player's current dimension written as a simple string.
		String dim = player.level.dimension().location().getPath();
		//these are the 2 dimensions we care about.
		boolean overworld = dim.equals("overworld");
		boolean nether = dim.equals("nether");
		//if we are doing chunks and aren't doing nether conversion
		if (Config.XZ_CHUNK.get() && (!nether || !Config.XZ_NETHER.get())) {
		//then we're just using the chunk numbers.	
			x = player.chunkPosition().x;
			z = player.chunkPosition().z;
			
		}
		//if we're doing nether conversion
		if (nether && Config.XZ_NETHER.get()) {
			
			if (Config.XZ_CHUNK.get()) {
				//on chunks
				x = x/2;
				z = z/2;
				
			} else {
				//on blocks
				x = x*8;
				z = z*8;
			}
			
		}
		
		if (overworld) {
			//offset normally
			x = x + Config.X_OFFSET.get();
			z = z + Config.Z_OFFSET.get();
			
		} else if (nether) {
			//use a smaller offset in the nether
			x = x + (Config.X_OFFSET.get())/8;
			z = z + (Config.Z_OFFSET.get())/8;
			
		}
		//put it into a string list
		xz[0] = String.valueOf(x);
		xz[1] = String.valueOf(z);
		return xz; //and send it off
	}
	
	public static String coordCalcY(int y) {
		//y is just a matter of show with offset or don't bother.
		return (Config.SHOW_Y.get()) ? String.valueOf(y + Config.Y_OFFSET.get()) : "";
	}
}
