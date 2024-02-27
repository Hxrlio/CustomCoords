package mc.hxrl.customcoords;

import com.mojang.logging.LogUtils;
import mc.hxrl.customcoords.config.Config;
import mc.hxrl.customcoords.hud.CoordHudOverlay;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("customcoords")
public class CustomCoords {
	
	public static final String MODID = "customcoords";
	
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public static CoordHudOverlay OVERLAY;
    
    public static boolean CHECK_ITEM = false;
    
    public static boolean XAERO = false;
    
    public static int[] COLORS = {0, 0, 0};
    
	public static String PRE_X;
	public static String PRE_Y;
	public static String PRE_Z;
	public static String POST_X;
	public static String POST_Y;
	public static String POST_Z;

    public CustomCoords() {
    	
        IEventBus mbus = FMLJavaModLoadingContext.get().getModEventBus();
        
        mbus.addListener(this::setupClient);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        OVERLAY = new CoordHudOverlay();
        
        ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CONFIG, "customcoords.toml");
        
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
    	
    	COLORS[0] = Config.COLOR_PRE.get();
    	COLORS[1] = Config.COLOR_COORD.get();
    	COLORS[2] = Config.COLOR_POST.get();
    	
    	PRE_X = (Config.SHOW_XZ.get()) ? Config.X_PRE_TEXT.get() : "";
    	POST_X = (Config.SHOW_XZ.get()) ? Config.X_POST_TEXT.get() : "";
    	PRE_Z = (Config.SHOW_XZ.get()) ? Config.Z_PRE_TEXT.get() : "";
    	POST_Z = (Config.SHOW_XZ.get()) ? Config.Z_POST_TEXT.get() : "";
    	PRE_Y = (Config.SHOW_Y.get()) ? Config.Y_PRE_TEXT.get() : "";
    	POST_Y = (Config.SHOW_XZ.get() && Config.SHOW_Y.get()) ? Config.Y_POST_TEXT.get() : "";
    	
    	ModList ml = ModList.get();
    	
		if (ml.isLoaded("xaerominimap") || ml.isLoaded("xaerominimapfair")) {
			XAERO = true;
		}
    	
    	if (!Registry.ITEM.getOptional(new ResourceLocation(Config.REQ_ITEM.get())).isEmpty()) {
    		CHECK_ITEM = true;
    	}
    }
}
