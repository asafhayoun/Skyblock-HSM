package hsm.hotshirtlessmen.mixin;

import hsm.hotshirtlessmen.HSM;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {

    @Inject(at = @At("RETURN"), method = "shouldInstantlyReplaceVisibleItem", cancellable = true)
    private void stopFlickerOnMending(ItemStack stackFrom, ItemStack stackTo, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            cir.setReturnValue(HSM.compareComponents(stackFrom.getComponents(),stackTo.getComponents()));
        }
    }

}
