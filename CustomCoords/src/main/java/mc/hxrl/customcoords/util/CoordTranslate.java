package mc.hxrl.customcoords.util;

import mc.hxrl.customcoords.config.Config;

public class CoordTranslate {
	//Translate input string list {xVanilla, zVanilla} into string list {xCustom, zCustom} for the Overworld.
	private static String[] translateTo2OWList(String[] coords) {
		//if we've received an incorrect input
		if (coords.length != 2) {
			//leave
			return null;
		}
		//separate and convert input strings to integers
		int xInput = Integer.valueOf(coords[0]);
		int zInput = Integer.valueOf(coords[1]);
		//our output starts off life as our input
		int xOutput = xInput;
		int zOutput = zInput;
		//if we're using the chunk system lets cut that number down
		if (Config.XZ_CHUNK.get()) {
			
			xOutput = xOutput/16;
			zOutput = zOutput/16;
			//and correct it slightly because BlockPos (0,0) & (16,16) -> ChunkPos (0,0) while BlockPos (-1,-1) -> ChunkPos(-1, -1)
			if (xInput < 0) {
				xOutput -= 1;
			}
			if (zInput < 0) {
				zOutput -= 1;
			}
		}
		//apply the offset
		xOutput = xOutput + Config.X_OFFSET.get();
		zOutput = zOutput + Config.Z_OFFSET.get();
		//send it back as a string
		String[] output = {String.valueOf(xOutput), String.valueOf(zOutput)};
		return output;
	}
	//translate input string list {xVanilla, yVanilla, zVanilla} to output string "(<xCustom>, <yCustom>, <zCustom>)"
	public static String translateTo3OW(String[] coords) {
		//if we've received an incorrect input
		if (coords.length != 3) {
			//leave
			return null;
		}
		//get x & z from above
		String[] xz = {coords[0], coords[2]};
		String[] translatedXZ = translateTo2OWList(xz);
		//y is much simpler because we just have an offset
		int y = Integer.valueOf(coords[1]) + Config.Y_OFFSET.get();
		//build it into a string
		return "(" + translatedXZ[0] + ", " + String.valueOf(y) + ", " + translatedXZ[1] + ")";
	}
	//translate input string list {xVanilla, zVanilla} to output string "(<xCustom>, <zCustom)"
	public static String translateTo2OW(String[] coords) {
		//get translation from above
		String[] translatedCoords = translateTo2OWList(coords);
		//build into string format
		return "(" + translatedCoords[0] + ", " + translatedCoords[1] + ")";
		
	}
}
