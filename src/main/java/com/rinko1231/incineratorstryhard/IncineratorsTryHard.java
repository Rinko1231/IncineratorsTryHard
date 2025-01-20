package com.rinko1231.incineratorstryhard;

import com.rinko1231.incineratorstryhard.config.IncineratorsTryHardConfig;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;

@Mod(IncineratorsTryHard.MODID)
public class IncineratorsTryHard
{
    public static final String MODID = "incineratorstryhard";


    public IncineratorsTryHard()
    {
        IncineratorsTryHardConfig.setup();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
