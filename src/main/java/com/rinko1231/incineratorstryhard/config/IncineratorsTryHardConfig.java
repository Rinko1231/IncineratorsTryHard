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
    public static ForgeConfigSpec.IntValue chargingTime;

    static
    {
        BUILDER.push("Incinerator's Try Hard Config");

        chargingTime = BUILDER
                .defineInRange("Charging time (ticks) of The Incinerator", 60,1,100);

        basicSkillDamage = BUILDER
                .defineInRange("Basic Damage dealt by The Incinerator's Skill", 6.0,0.0, Double.MAX_VALUE);

        maxHealthDamagePercent = BUILDER
                .defineInRange("Max Health Damage Percentage dealt by The Incinerator's Skill", 2.0,0.0,100);

        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "IncineratorsTryHard.toml");
    }


}