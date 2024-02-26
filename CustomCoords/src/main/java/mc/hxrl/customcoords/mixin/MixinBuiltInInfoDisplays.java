package mc.hxrl.customcoords.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.minimap.info.BuiltInInfoDisplays;
import xaero.common.minimap.info.InfoDisplayManager;
import static mc.hxrl.customcoords.hud.xaero.CustomCoordsInfoDisplay.CUSTOM_COORDS;

@Mixin(BuiltInInfoDisplays.class)
public class MixinBuiltInInfoDisplays {
	
	@Inject(method = "addToManager", at = @At("TAIL"), remap = false)
	private static void addToManager(InfoDisplayManager manager, CallbackInfo ci) {
		manager.add(CUSTOM_COORDS);
	}
}
