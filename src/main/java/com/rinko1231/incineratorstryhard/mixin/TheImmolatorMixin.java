package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.The_Immolator;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = The_Immolator.class, remap = false)
public abstract class TheImmolatorMixin extends Item {


    public TheImmolatorMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow
    public abstract int getUseDuration(@NotNull ItemStack p_77626_1_);

    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player player) {
            int i = this.getUseDuration(p_43394_) - p_43397_;
            boolean hasSucceeded = false;
            double headY = player.getY() + (double)1.0F;
            int standingOnY = Mth.floor(player.getY()) - 2;
            if (i >= IncineratorsTryHardConfig.chargingTimeForImmolator.get()) {
                float f1 = (float)Math.cos(Math.toRadians(p_43396_.getYRot() + 90.0F));
                float f2 = (float)Math.sin(Math.toRadians(p_43396_.getYRot() + 90.0F));
                float f0 = (float)Mth.atan2(f1, f2);
                if (this.ba_painting$spawnFlameStrike(player.getX(), player.getZ(), standingOnY, headY, f0, 45, 0, 0, p_43395_, IncineratorsTryHardConfig.ignisCircleRadiusForImmolator.get().floatValue(), player)) {
                    hasSucceeded = true;
                }

                if (hasSucceeded) {
                    if (!p_43395_.isClientSide) {
                        player.getCooldowns().addCooldown(this, CMConfig.ImmolatorCooldown);
                    }

                    ScreenShake_Entity.ScreenShake(p_43395_, player.position(), 30.0F, 0.15F, 0, 30);
                    p_43395_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                }
            }
        }
    }

@Unique
private boolean ba_painting$spawnFlameStrike(double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level world, float radius, LivingEntity player) {
    BlockPos blockpos = BlockPos.containing(x, maxY, z);
    boolean flag = false;
    double d0 = 0.0F;

    do {
        BlockPos blockpos1 = blockpos.below();
        BlockState blockstate = world.getBlockState(blockpos1);
        if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
            if (!world.isEmptyBlock(blockpos)) {
                BlockState blockstate1 = world.getBlockState(blockpos);
                VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                if (!voxelshape.isEmpty()) {
                    d0 = voxelshape.max(Direction.Axis.Y);
                }
            }

            flag = true;
            break;
        }

        blockpos = blockpos.below();
    } while((double)blockpos.getY() >= minY);

    if (flag) {
        world.addFreshEntity(new Flame_Strike_Entity(world, x, (double)blockpos.getY() + d0, z, rotation, duration, wait, delay, radius, IncineratorsTryHardConfig.basicSkillDamageForImmolator.get().floatValue(), IncineratorsTryHardConfig.maxHealthDamagePercentForImmolator.get().floatValue(), false, player));
        return true;
    } else {
        return false;
    }
}


/**
 * @author Rinko1231
 * @reason Tweak
 */
@Overwrite
public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
    int i = this.getUseDuration(stack) - count;
    int iMul = IncineratorsTryHardConfig.chargingTimeForImmolator.get()/45;
    if (i == 10*iMul) {
        this.masseffectParticle(worldIn, livingEntityIn, 2.0F);
    }

    if (i == 20*iMul) {
        this.masseffectParticle(worldIn, livingEntityIn, 3.5F);
    }

    if (i == 30*iMul) {
        this.masseffectParticle(worldIn, livingEntityIn, 5.0F);
    }

    if (i == 45*iMul) {
        livingEntityIn.playSound((SoundEvent) ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
    }

}

/**
 * @author Rinko1231
 * @reason Tweak
 */
@Overwrite
    private void yall(Level world, LivingEntity caster) {
        double radius = IncineratorsTryHardConfig.quakeRangeForImmolator.get();
        ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0F, 0.1F, 0, 30);
        world.playSound((Player)null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F));

        for(Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
            if (entity instanceof LivingEntity) {
                entity.hurt(world.damageSources().mobAttack(caster), (float)caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * IncineratorsTryHardConfig.quakeDamageMultiplierForImmolator.get().floatValue());
            }
        }

    }


    @Shadow protected abstract void masseffectParticle(Level worldIn, LivingEntity livingEntityIn, float v);


}
