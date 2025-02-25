package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.capabilities.HookCapability;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Hook_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Tidal_Claws.class, remap = false)
public abstract class TidalClawsMixin extends Item {

    public TidalClawsMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow
    protected abstract boolean isCharged(Player player, ItemStack stack);

    @Shadow
    public abstract boolean launchTendonsAt(ItemStack stack, LivingEntity playerIn, Entity closestValid);


    /**
     * @author Rinko1231
     * @reason Tweak
     */
    @Overwrite
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        HookCapability.IHookCapability hookCapability = (HookCapability.IHookCapability) ModCapabilities.getCapability(user, ModCapabilities.HOOK_CAPABILITY);
        if (hookCapability != null) {
            if (!level.isClientSide && !hookCapability.hasHook()) {
                //user.displayClientMessage(Component.literal("Yes Mixin"), true);
                double maxRange = IncineratorsTryHardConfig.TidalClawsHookMaxRange.get();
                double maxSpeed = IncineratorsTryHardConfig.TidalClawsHookMaxSpeed.get();
                Tidal_Hook_Entity hookshot = new Tidal_Hook_Entity((EntityType) ModEntities.TIDAL_HOOK.get(), user, level);
                hookshot.setProperties(stack, maxRange, maxSpeed, user.getXRot(), user.getYRot(), 0.0F, 1.5F * (float)(maxSpeed / (double)10.0F));
                level.addFreshEntity(hookshot);
            }

            user.startUsingItem(hand);
            hookCapability.setHasHook(true);
        }
        return (super.use(level, user, hand));
    }

    @Inject(method = "onLeftClick", at = @At("HEAD"), cancellable = true)
    public void onLeftClickTweak(ItemStack stack, LivingEntity playerIn, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is((Item) ModItems.TIDAL_CLAWS.get()) && (!(playerIn instanceof Player) || this.isCharged((Player)playerIn, stack))) {
            Level worldIn = playerIn.level();
            Entity closestValid = null;
            Vec3 playerEyes = playerIn.getEyePosition(1.0F);
            HitResult hitresult = worldIn.clip(new ClipContext(playerEyes, playerEyes.add(playerIn.getLookAngle().scale(IncineratorsTryHardConfig.TidalClawsTentacleFirstRange.get())), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, playerIn));
            if (hitresult instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult)hitresult).getEntity();
                if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity)) {
                    closestValid = entity;
                }
            } else {
                for(Entity entity : worldIn.getEntitiesOfClass(LivingEntity.class, playerIn.getBoundingBox().inflate(IncineratorsTryHardConfig.TidalClawsTentacleSecondRange.get()))) {
                    if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && entity instanceof Mob && playerIn.hasLineOfSight(entity) && (closestValid == null || playerIn.distanceTo(entity) < playerIn.distanceTo(closestValid))) {
                        closestValid = entity;
                    }
                }
            }

            cir.setReturnValue(this.launchTendonsAt(stack, playerIn, closestValid));
        } else {
            cir.setReturnValue(false);
        }
    }








}
