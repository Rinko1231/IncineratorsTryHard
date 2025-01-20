package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.More_Tool_Attribute;
import com.github.L_Ender.cataclysm.items.The_Incinerator;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = The_Incinerator.class)

public abstract class TheIncineratorMixin extends Item implements More_Tool_Attribute {

    public TheIncineratorMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow
    public abstract int getUseDuration(@NotNull ItemStack p43394);


    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void modifyReleaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_, CallbackInfo ci) {
        if (p_43396_ instanceof Player player) {
            int i = this.getUseDuration(p_43394_) - p_43397_;
            double headY = player.getY() + (double)1.0F;
            int standingOnY = Mth.floor(player.getY()) - 2;
            float yawRadians = (float)Math.toRadians(90.0F + player.getYRot());
            boolean hasSucceeded = false;
            if (i >= IncineratorsTryHardConfig.chargingTime.get()) {
                for(int l = 0; l < 10; ++l) {
                    double d2 = (double)2.25F * (double)(l + 1);
                    int j2 = (int)(1.5F * (float)l);
                    if (this.ba_painting$modifySpawnFlameStrike(player.getX() + (double)Mth.cos(yawRadians) * d2, player.getZ() + (double)Mth.sin(yawRadians) * d2, standingOnY, headY, yawRadians, j2, j2, p_43395_, player)) {
                        hasSucceeded = true;
                    }
                }

                if (hasSucceeded) {
                    if (!p_43395_.isClientSide) {
                        player.getCooldowns().addCooldown(this, CMConfig.TheIncineratorCooldown);
                    }

                    ScreenShake_Entity.ScreenShake(p_43395_, player.position(), 30.0F, 0.15F, 0, 30);
                    player.playSound(ModSounds.SWORD_STOMP.get(), 1.0F, 1.0F);
                }
            }
        }
     ci.cancel();
    }


    @Unique
    private boolean ba_painting$modifySpawnFlameStrike(double x, double z, double minY, double maxY, float rotation, int wait, int delay, Level world, LivingEntity player) {
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
        } while (blockpos.getY() >= minY);

        if (flag) {
            world.addFreshEntity(new Flame_Strike_Entity(world, x, blockpos.getY() + d0, z, rotation, 40, wait, delay, (float) 1.0, (float) IncineratorsTryHardConfig.basicSkillDamage.get().doubleValue(), (float) IncineratorsTryHardConfig.maxHealthDamagePercent.get().doubleValue(), false, player));
            return true;
        } else {
            return false;
        }
    }
}