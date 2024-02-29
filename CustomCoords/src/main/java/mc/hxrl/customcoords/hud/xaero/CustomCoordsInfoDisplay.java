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
	//"ALL" is used internally in Xaero's to refer to all elements of the Info Display, we make our own so it understands where it should live.
	private static List<InfoDisplay<?>> ALL = new ArrayList<>();
    public static final InfoDisplay<Boolean> CUSTOM_COORDS; //Boolean as in does it show up or not
    static{
        CUSTOM_COORDS = new InfoDisplay("customcoords", //id
        		new TextComponent("Custom Coords"), //name (shown in Xaero's menus)
        		true, //default state, yeah this should show up by default
        		InfoDisplayCommonStateCodecs.BOOLEAN,
        		InfoDisplayCommonWidgetFactories.OFF_ON, //no fancy business, just shown or hidden.
        		//Xaero's always needs all this stuff passed through, even if we aren't going to use it.
        		(displayInfo, compiler, session, processor, x, y, w, h, scale, size, playerBlockX, playerBlockY, playerBlockZ, playerPos) -> {
        			//but playerBlock does come in handy
        			String[] xz = CoordLogic.coordCalcXZ(playerBlockX, playerBlockZ);
        			//have it written in all in one line
        			compiler.addLine(new TextComponent(CustomCoords.PRE_X + xz[0] + CustomCoords.POST_X
        					+ CustomCoords.PRE_Y + CoordLogic.coordCalcY(playerBlockY) + CustomCoords.POST_Y
        					+ CustomCoords.PRE_Z + xz[1] + CustomCoords.POST_Z));
        		},ALL);
    }
}
