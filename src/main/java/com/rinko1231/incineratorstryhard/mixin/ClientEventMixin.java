package com.rinko1231.incineratorstryhard.mixin;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.ClientProxy;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Axis;
import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import com.github.L_Ender.cataclysm.client.event.ClientEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientEvent.class, remap = false)
public abstract class ClientEventMixin {

    @Shadow public static final ResourceLocation FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/soul_flame_strike_sigil.png");

    @Shadow public static final ResourceLocation NORMAL_FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/flame_strike_sigil.png");

    @Shadow abstract public void drawVertex(org.joml.Matrix4f p_229039_1_, org.joml.Matrix3f p_229039_2_, VertexConsumer p_229039_3_, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) ;


        @Inject(method = "onPreRenderEntity", at = @At("HEAD"), cancellable = true)
    public void modifyOnPreRenderEntity(RenderLivingEvent.Pre event, CallbackInfo ci) {
        LivingEntity player = event.getEntity();
        boolean usingIncinerator = player.isUsingItem() && player.getUseItem().is(ModItems.THE_INCINERATOR.get());
        boolean usingImmolator = player.isUsingItem() && player.getUseItem().is(ModItems.THE_IMMOLATOR.get());
        if (usingIncinerator) {
            int i = player.getTicksUsingItem();
            float f2 = (float)player.tickCount + event.getPartialTick();
            PoseStack matrixStackIn = event.getPoseStack();
            int maxChargeTime = IncineratorsTryHardConfig.chargingTimeToMaxCircle.get(); // 新的蓄力时间上限
            int originalMaxTime = 60;
            float f3 = (float) Mth.clamp(i, 1, maxChargeTime) / maxChargeTime * originalMaxTime;
            matrixStackIn.pushPose();
            VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(event.getMultiBufferSource(), CMRenderTypes.getGlowingEffect(FLAME_STRIKE), false, true);
            matrixStackIn.translate(0.0F, 0.001, 0.0F);
            matrixStackIn.scale(f3 * 0.05F, f3 * 0.05F, f3 * 0.05F);
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180.0F));
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F + f2));
            PoseStack.Pose lvt_19_1_ = matrixStackIn.last();
            Matrix4f lvt_20_1_ = lvt_19_1_.pose();
            Matrix3f lvt_21_1_ = lvt_19_1_.normal();
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
            matrixStackIn.popPose();
        }

        if (usingImmolator) {
            int i = player.getTicksUsingItem();
            float f2 = (float)player.tickCount + event.getPartialTick();
            PoseStack matrixStackIn = event.getPoseStack();
            int maxChargeTime = IncineratorsTryHardConfig.chargingTimeToMaxCircleForImmolator.get(); // 新的蓄力时间上限
            int originalMaxTime = 45;
            float f3 = (float) Mth.clamp(i, 1, maxChargeTime) / maxChargeTime * originalMaxTime;
            matrixStackIn.pushPose();
            VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(event.getMultiBufferSource(), CMRenderTypes.getGlowingEffect(NORMAL_FLAME_STRIKE), false, true);
            matrixStackIn.translate(0.0F, 0.001, 0.0F);
            matrixStackIn.scale(f3 * 0.05F, f3 * 0.05F, f3 * 0.05F);
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180.0F));
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F + f2));
            PoseStack.Pose lvt_19_1_ = matrixStackIn.last();
            Matrix4f lvt_20_1_ = lvt_19_1_.pose();
            Matrix3f lvt_21_1_ = lvt_19_1_.normal();
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
            this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
            matrixStackIn.popPose();
        }
            if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
                if (!Cataclysm.PROXY.isFirstPersonPlayer(event.getEntity())) {
                    MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                    event.setCanceled(true);
                }

                ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
            }
     ci.cancel();
    }


}
