package com.rinko1231.incineratorstryhard.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;


public class IncineratorsTryHardConfig
{
    public static ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
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
    public static ForgeConfigSpec.DoubleValue quakeRangeForImmolator;
    public static ForgeConfigSpec.DoubleValue quakeDamageMultiplierForImmolator;

    public static ForgeConfigSpec.DoubleValue TidalClawsTentacleDamage;
    public static ForgeConfigSpec.DoubleValue TidalClawsTentacleFirstRange;
    public static ForgeConfigSpec.DoubleValue TidalClawsTentacleSecondRange;
    public static ForgeConfigSpec.DoubleValue TidalClawsHookMaxRange;
    public static ForgeConfigSpec.DoubleValue TidalClawsHookMaxSpeed;

    public static ForgeConfigSpec.DoubleValue ChargeDamageMultiplierOfGauntletOfBulwark;

    public static ForgeConfigSpec.IntValue coolDownForBloomStonePauldrons;

    public static ForgeConfigSpec.DoubleValue annihilatorSkillRange;
    public static ForgeConfigSpec.DoubleValue annihilatorSkillDamageMultiplier;
    public static ForgeConfigSpec.IntValue annihilatorChargingTime;
    public static ForgeConfigSpec.IntValue annihilatorSkillCoolDown;

    public static ForgeConfigSpec.DoubleValue gauntletOfGuardSkillRange;
    public static ForgeConfigSpec.DoubleValue getGauntletOfGuardSkillVectorScale;

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
                .defineInRange("Basic Ignis Circle Damage dealt by The Immolator's Skill", 6.0,0.0, Double.MAX_VALUE);

        maxHealthDamagePercentForImmolator = BUILDER
                .defineInRange("Max Health Damage Percentage dealt by The Immolator's Skill", 2.0,0.0,100);

        ignisCircleRadiusForImmolator = BUILDER
                .defineInRange("The Radius of the ignis circles released by The Immolator's Skill", 2.5,0.0,100);

        quakeRangeForImmolator= BUILDER
                .defineInRange("[Questionable, maybe unused method] The Radius of the Quake released by The Immolator's Skill", 6.0,0.01,250);

        quakeDamageMultiplierForImmolator = BUILDER
                .defineInRange("[Questionable, maybe unused method] Quake Damage Multiplier of The Immolator's Skill", 2.0,0.01, Double.MAX_VALUE);


        BUILDER.pop();

        BUILDER.push("Tidal Claws");

        TidalClawsTentacleDamage = BUILDER
                .defineInRange("Tentacle Damage released by Tidal Claws", 3.0,0.0, Double.MAX_VALUE);

        TidalClawsTentacleFirstRange = BUILDER
                .defineInRange("Tentacle's First Detection Range released by Tidal Claws", 16.0,1.0, Double.MAX_VALUE);

        TidalClawsTentacleSecondRange = BUILDER
                .defineInRange("Tentacle's Second Detection Range released by Tidal Claws", 16.0,1.0, Double.MAX_VALUE);

        TidalClawsHookMaxRange = BUILDER
                .defineInRange("Hook's Max Range released by Tidal Claws", 30.0,0.01, Double.MAX_VALUE);

        TidalClawsHookMaxSpeed = BUILDER
                .defineInRange("Hook's Max Speed released by Tidal Claws", 12.0,0.01, Double.MAX_VALUE);



        BUILDER.pop();

        BUILDER.push("Gauntlet of Bulwark");

        ChargeDamageMultiplierOfGauntletOfBulwark = BUILDER
                .defineInRange("Charge Damage Multiplier Of Gauntlet Of Bulwark", 1.2,1.0, Double.MAX_VALUE);

        BUILDER.pop();

        BUILDER.push("Bloom Stone Pauldrons");

        coolDownForBloomStonePauldrons = BUILDER
                .defineInRange("Cooldown time (ticks) of Bloom Stone Pauldrons to shoot Amethyst Cluster", 240,1,Integer.MAX_VALUE);

        BUILDER.pop();

        BUILDER.push("The Annihilator");

        annihilatorSkillRange= BUILDER
                .defineInRange("Skill Range Of The Annihilator", 6,0.01, Double.MAX_VALUE);

        annihilatorSkillDamageMultiplier= BUILDER
                .defineInRange("Skill Damage Multiplier Of The Annihilator", 2,0.01, Double.MAX_VALUE);

        annihilatorChargingTime = BUILDER
                .defineInRange("Charging time (ticks) of The Annihilator", 40,1,1000);

        annihilatorSkillCoolDown = BUILDER
                .defineInRange("Skill Cooldown time (ticks) of The Annihilator", 100,1,Integer.MAX_VALUE);

        BUILDER.pop();

        BUILDER.push("Gauntlet of Guard");

        gauntletOfGuardSkillRange = BUILDER
                .defineInRange("Attraction Radius Of Gauntlet Of Guard", 11,0.01, Double.MAX_VALUE);

        getGauntletOfGuardSkillVectorScale = BUILDER
                .defineInRange("Scale of Normalized Attraction Vector Of Gauntlet Of Bulwark", 0.1,0.01, Double.MAX_VALUE);


        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "IncineratorsTryHard.toml");
    }


}