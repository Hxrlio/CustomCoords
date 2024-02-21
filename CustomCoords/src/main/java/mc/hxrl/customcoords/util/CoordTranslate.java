package mc.hxrl.customcoords.util;

import mc.hxrl.customcoords.config.Config;

public class CoordTranslate {
	
	private static String[] translateTo2OWList(String[] coords) {
		
		if (coords.length != 2) {
			return null;
		}
		int xInput = Integer.valueOf(coords[0]);
		int zInput = Integer.valueOf(coords[1]);
		int xOutput = xInput;
		int zOutput = zInput;
		if (Config.XZ_CHUNK.get()) {
			xOutput = xOutput/16;
			zOutput = zOutput/16;
			if (xInput < 0) {
				xOutput -= 1;
			}
			if (zInput < 0) {
				zOutput -= 1;
			}
		}
		xOutput = xOutput + Config.X_OFFSET.get();
		zOutput = zOutput + Config.Z_OFFSET.get();
		String[] output = {String.valueOf(xOutput), String.valueOf(zOutput)};
		return output;
	}
	
	public static String translateTo3OW(String[] coords) {
		
		if (coords.length != 3) {
			return null;
		}
		String[] xz = {coords[0], coords[2]};
		String[] translatedXZ = translateTo2OWList(xz);
		int y = Integer.valueOf(coords[1]) + Config.Y_OFFSET.get();
		return "(" + translatedXZ[0] + ", " + String.valueOf(y) + ", " + translatedXZ[1] + ")";
	}
	
	public static String translateTo2OW(String[] coords) {
		
		String[] translatedCoords = translateTo2OWList(coords);
		return "(" + translatedCoords[0] + ", " + translatedCoords[1] + ")";
		
	}
}
