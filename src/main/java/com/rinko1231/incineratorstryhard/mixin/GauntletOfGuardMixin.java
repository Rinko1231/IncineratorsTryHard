package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = Gauntlet_of_Guard.class, remap = false)
public abstract class GauntletOfGuardMixin extends Item {

    public GauntletOfGuardMixin(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        double radius = IncineratorsTryHardConfig.gauntletOfGuardSkillRange.get();
        Level world = livingEntityIn.level();

        for(LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, livingEntityIn.getBoundingBox().inflate(radius))) {
            if (!(entity instanceof Player) || !((Player)entity).getAbilities().invulnerable) {
                Vec3 diff = entity.position().subtract(livingEntityIn.position().add((double)0.0F, (double)0.0F, (double)0.0F));
                diff = diff.normalize().scale(IncineratorsTryHardConfig.getGauntletOfGuardSkillVectorScale.get());
                entity.setDeltaMovement(entity.getDeltaMovement().subtract(diff));
            }
        }

        if (world.isClientSide) {
            for(int i = 0; i < 3; ++i) {
                int j = world.random.nextInt(2) * 2 - 1;
                int k = world.random.nextInt(2) * 2 - 1;
                double d0 = livingEntityIn.getX() + (double)0.25F * (double)j;
                double d1 = (double)((float)livingEntityIn.getY() + world.random.nextFloat());
                double d2 = livingEntityIn.getZ() + (double)0.25F * (double)k;
                double d3 = (double)(world.random.nextFloat() * (float)j);
                double d4 = ((double)world.random.nextFloat() - (double)0.5F) * (double)0.125F;
                double d5 = (double)(world.random.nextFloat() * (float)k);
                world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
        }

    }



}
