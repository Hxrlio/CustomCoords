package mc.hxrl.customcoords.hud.xaero;

import java.util.ArrayList;
import java.util.List;

import mc.hxrl.customcoords.CustomCoords;
import mc.hxrl.customcoords.logic.CoordLogic;
import net.minecraft.network.chat.TextComponent;
import xaero.common.minimap.info.InfoDisplay;
import xaero.common.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.common.minimap.info.widget.InfoDisplayCommonWidgetFactories;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CustomCoordsInfoDisplay {
	
	private static List<InfoDisplay<?>> ALL = new ArrayList<>();
    public static final InfoDisplay<Boolean> CUSTOM_COORDS;
    static{
        CUSTOM_COORDS = new InfoDisplay("customcoords",
        		new TextComponent("Custom Coords"),
        		true,
        		InfoDisplayCommonStateCodecs.BOOLEAN,
        		InfoDisplayCommonWidgetFactories.OFF_ON,
        		(displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
        			String[] xz = CoordLogic.coordCalcXZ(playerBlockX, playerBlockZ);
        			compiler.addLine(new TextComponent(CustomCoords.PRE_X + xz[0] + CustomCoords.POST_X
        					+ CustomCoords.PRE_Y + CoordLogic.coordCalcY(playerBlockY) + CustomCoords.POST_Y
        					+ CustomCoords.PRE_Z + xz[1] + CustomCoords.POST_Z));
        		},ALL);
    }
}
