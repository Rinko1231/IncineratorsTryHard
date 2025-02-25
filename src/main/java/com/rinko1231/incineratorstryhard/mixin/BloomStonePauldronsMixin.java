package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.Bloom_Stone_Pauldrons;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Bloom_Stone_Pauldrons.class, remap = false)
public abstract class BloomStonePauldronsMixin extends ArmorItem {


    public BloomStonePauldronsMixin(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Inject(method = "onKeyPacket", at = @At("HEAD"), cancellable = true)
    public void packetCoolDown(Player player, ItemStack itemStack, int Type, CallbackInfo ci)
    {
        if (player != null && !player.getCooldowns().isOnCooldown(this)) {
            for(int i = 0; i < 8; ++i) {
                float throwAngle = (float)i * (float)Math.PI / 4.0F;
                double sx = player.getX() + (double)(Mth.cos(throwAngle) * 1.0F);
                double sy = player.getY() + (double)player.getBbHeight() * (double)0.5F;
                double sz = player.getZ() + (double)(Mth.sin(throwAngle) * 1.0F);
                double vx = (double)Mth.cos(throwAngle);
                double vy = (double)(0.0F + player.getRandom().nextFloat() * 0.3F);
                double vz = (double)Mth.sin(throwAngle);
                double v3 = (double)Mth.sqrt((float)(vx * vx + vz * vz));
                Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity((EntityType) ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), player.level(), player, (float) CMConfig.AmethystClusterdamage);
                projectile.moveTo(sx, sy, sz, (float)i * 11.25F, player.getXRot());
                float speed = 0.8F;
                projectile.shoot(vx, vy + v3 * (double)0.2F, vz, speed, 1.0F);
                player.level().addFreshEntity(projectile);
            }

            player.getCooldowns().addCooldown((Item) ModItems.BLOOM_STONE_PAULDRONS.get(), IncineratorsTryHardConfig.coolDownForBloomStonePauldrons.get());
            ci.cancel();
        }

    }

}
