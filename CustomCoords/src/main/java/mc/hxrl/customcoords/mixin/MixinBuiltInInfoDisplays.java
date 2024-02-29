package mc.hxrl.customcoords.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.minimap.info.BuiltInInfoDisplays;
import xaero.common.minimap.info.InfoDisplayManager;
import static mc.hxrl.customcoords.hud.xaero.CustomCoordsInfoDisplay.CUSTOM_COORDS;

//Yeah, I hate mixins too, but this one is actually necessary, and isn't likely to blow up the universe like poorly thought out ones.
//Xaero's doesn't have an api (yet, teases Xaero himself) and this is the Xaero-approved way to do it, so here we are.
@Mixin(BuiltInInfoDisplays.class)
public class MixinBuiltInInfoDisplays {
	//at the end of the addToManager method which simply adds all the Xaero info displays to a list...
	@Inject(method = "addToManager", at = @At("TAIL"), remap = false)
	private static void addToManager(InfoDisplayManager manager, CallbackInfo ci) {
		//just add ours too.
		manager.add(CUSTOM_COORDS);
	}
}
