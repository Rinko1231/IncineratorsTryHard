package com.rinko1231.incineratorstryhard.mixin;


import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Tidal_Tentacle_Entity.class, remap = false)
public abstract class TidalTentacleEntityMixin {

    @Inject(method = "getBaseDamage", at = @At("HEAD"), cancellable = true)
    private void modifyBaseDamage(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(IncineratorsTryHardConfig.TidalClawsTentacleDamage.get().floatValue());
    }
}