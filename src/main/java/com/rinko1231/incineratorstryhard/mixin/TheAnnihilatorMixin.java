package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.The_Annihilator;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = The_Annihilator.class, remap = false)
public abstract class TheAnnihilatorMixin extends Item {


    public TheAnnihilatorMixin(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    private void yall(Level world, LivingEntity caster) {
        double radius = IncineratorsTryHardConfig.annihilatorSkillRange.get();
        float radiusMul = (float) radius /30.0f;
        ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0f, 0.1F, 0, 30);
        world.playSound((Player)null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F));

        for(Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
            if (entity instanceof LivingEntity) {
                entity.hurt(world.damageSources().mobAttack(caster), (float)caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * IncineratorsTryHardConfig.annihilatorSkillDamageMultiplier.get().floatValue());
            }
        }

        if (world.isClientSide) {
            world.addParticle(new RingParticle.RingData(0.0F, ((float)Math.PI / 2F), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F*radiusMul, false, RingParticle.EnumRingBehavior.GROW), caster.getX(), caster.getY() + (double)0.03F, caster.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player player) {
            int i = this.getUseDuration(p_43394_) - p_43397_;
            if (i >= IncineratorsTryHardConfig.annihilatorChargingTime.get()) { //40
                this.yall(p_43395_, p_43396_);
                if (!p_43395_.isClientSide) {
                    player.getCooldowns().addCooldown(this, IncineratorsTryHardConfig.annihilatorSkillCoolDown.get()); //100
                }
            }
        }

    }

    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        int i = this.getUseDuration(stack) - count;
        int iMul = IncineratorsTryHardConfig.annihilatorChargingTime.get()/40;
        if (i == 10*iMul) {
            this.masseffectParticle(worldIn, livingEntityIn, 2.0F);
        }

        if (i == 20*iMul) {
            this.masseffectParticle(worldIn, livingEntityIn, 3.5F);
        }

        if (i == 30*iMul) {
            this.masseffectParticle(worldIn, livingEntityIn, 5.0F);
        }

        if (i == 40*iMul) {
            livingEntityIn.playSound((SoundEvent) ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
        }

    }

    @Shadow
    protected abstract void masseffectParticle(Level worldIn, LivingEntity livingEntityIn, float v);


}
