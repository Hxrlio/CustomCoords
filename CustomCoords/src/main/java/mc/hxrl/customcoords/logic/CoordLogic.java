package mc.hxrl.customcoords.logic;

import mc.hxrl.customcoords.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class CoordLogic {
	
	public static String[] coordCalcXZ(int x, int z) {
		
		String[] xz = {"",""};
		
		if (!Config.SHOW_XZ.get()) {
			return xz;
		}
		
		Minecraft client = Minecraft.getInstance();
		Entity player = client.getCameraEntity();
		
		if (player == null) {
			return xz;
		}
		
		String dim = player.level.dimension().location().getPath();
		boolean overworld = dim.equals("overworld");
		boolean nether = dim.equals("nether");
		
		if (Config.XZ_CHUNK.get() && (!nether || !Config.XZ_NETHER.get())) {
			
			x = player.chunkPosition().x;
			z = player.chunkPosition().z;
			
		}

		if (nether && Config.XZ_NETHER.get()) {
			
			if (Config.XZ_CHUNK.get()) {
				
				x = x/2;
				z = z/2;
				
			} else {
				
				x = x*8;
				z = z*8;
			}
			
		}
		
		if (overworld) {
			
			x = x + Config.X_OFFSET.get();
			z = z + Config.Z_OFFSET.get();
			
		} else if (nether) {
			
			x = x + (Config.X_OFFSET.get())/8;
			z = z + (Config.Z_OFFSET.get())/8;
			
		}
		
		xz[0] = String.valueOf(x);
		xz[1] = String.valueOf(z);
		return xz;
	}
	
	public static String coordCalcY(int y) {
		return (Config.SHOW_Y.get()) ? String.valueOf(y + Config.Y_OFFSET.get()) : "";
	}
}
