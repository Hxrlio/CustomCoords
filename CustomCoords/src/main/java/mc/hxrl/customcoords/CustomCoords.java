package mc.hxrl.customcoords;

import com.mojang.logging.LogUtils;

import mc.hxrl.customcoords.hud.CoordHudOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("customcoords")
public class CustomCoords
{
	
	public static final String MODID = "customcoords";
	
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public static CoordHudOverlay OVERLAY;

    public CustomCoords()
    {
        IEventBus mbus = FMLJavaModLoadingContext.get().getModEventBus();
        
        mbus.addListener(this::setup);
        mbus.addListener(this::setupClient);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        OVERLAY = new CoordHudOverlay();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // LOGGER.info("Working, Hxrl");
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
    	
    	// LOGGER.info("pogger.info");
    	
    	
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        
    }
}
