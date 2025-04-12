package dev.journey.PathSeeker.mixin;

import dev.journey.PathSeeker.modules.utility.sixbeeOW;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Inject(method = "isPressed", at = @At("HEAD"), cancellable = true)
    private void onIsPressed(CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.options == null || mc.player == null) return;

        KeyBinding self = (KeyBinding) (Object) this;
        sixbeeOW module = Modules.get().get(sixbeeOW.class);

        if (self == mc.options.forwardKey && module != null && module.shouldWalk()) {
            cir.setReturnValue(true);
        }
    }
}
