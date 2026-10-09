package de.betterhud;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Keyboard.class)
public class KeyboardMixin {
 @Inject(method="onKey",at=@At("TAIL"))
 private void betterhud$onKey(long window,int key,int scancode,int action,int modifiers,CallbackInfo ci) {
  BetterHud.key(key,action!=0);
 }
}
