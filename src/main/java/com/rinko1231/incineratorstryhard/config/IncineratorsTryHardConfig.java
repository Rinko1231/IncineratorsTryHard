package com.rinko1231.incineratorstryhard.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;


public class IncineratorsTryHardConfig
{
    public static ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.DoubleValue maxHealthDamagePercent;
    public static ForgeConfigSpec.DoubleValue basicSkillDamage;
    public static ForgeConfigSpec.DoubleValue ignisCircleRadius;
    public static ForgeConfigSpec.IntValue chargingTime;
    public static ForgeConfigSpec.IntValue chargingTimeToMaxCircle;

    public static ForgeConfigSpec.DoubleValue maxHealthDamagePercentForImmolator;
    public static ForgeConfigSpec.DoubleValue basicSkillDamageForImmolator;
    public static ForgeConfigSpec.DoubleValue ignisCircleRadiusForImmolator;
    public static ForgeConfigSpec.IntValue chargingTimeForImmolator;
    public static ForgeConfigSpec.IntValue chargingTimeToMaxCircleForImmolator;

    public static ForgeConfigSpec.DoubleValue TidalClawsTentacleDamage;


    static
    {

        BUILDER.comment("Incinerator's Try Hard Config");

        BUILDER.push("The Incinerator");

        chargingTime = BUILDER
                .defineInRange("Charging time (ticks) of The Incinerator", 60,1,100);

        chargingTimeToMaxCircle = BUILDER
                .defineInRange("(Client Only) Max Charging time (ticks) to Form the Biggest Magic Circle underfoot when charging", 60,1,100);

        basicSkillDamage = BUILDER
                .defineInRange("Basic Damage dealt by The Incinerator's Skill", 6.0,0.0, Double.MAX_VALUE);

        maxHealthDamagePercent = BUILDER
                .defineInRange("Max Health Damage Percentage dealt by The Incinerator's Skill", 2.0,0.0,100);

        ignisCircleRadius = BUILDER
                .defineInRange("The Radius of the ignis circles released by The Incinerator's Skill", 1.0,0.0,100);

        BUILDER.pop();

        BUILDER.push("The Immolator");

        chargingTimeForImmolator = BUILDER
                .defineInRange("Charging time (ticks) of The Immolator", 45,1,100);

        chargingTimeToMaxCircleForImmolator = BUILDER
                .defineInRange("(Client Only) Max Charging time (ticks) to Form the Biggest Magic Circle underfoot when charging", 45,1,100);

        basicSkillDamageForImmolator = BUILDER
                .defineInRange("Basic Damage dealt by The Immolator's Skill", 6.0,0.0, Double.MAX_VALUE);

        maxHealthDamagePercentForImmolator = BUILDER
                .defineInRange("Max Health Damage Percentage dealt by The Immolator's Skill", 2.0,0.0,100);

        ignisCircleRadiusForImmolator = BUILDER
                .defineInRange("The Radius of the ignis circles released by The Immolator's Skill", 2.5,0.0,100);

        BUILDER.pop();

        BUILDER.push("Tidal Claws");

        TidalClawsTentacleDamage = BUILDER
                .defineInRange("Tentacle Damage released by Tidal Claws", 3.0,0.0, Double.MAX_VALUE);

        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "IncineratorsTryHard.toml");
    }


}