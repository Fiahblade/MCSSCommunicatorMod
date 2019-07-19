package com.mcserversoft.mcsscommunicatormod;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MCSSCommunicatorMod.MODID, name = MCSSCommunicatorMod.NAME, version = MCSSCommunicatorMod.VERSION, acceptableRemoteVersions = "*")
public class MCSSCommunicatorMod {

    public static final String MODID = "mcsscommunicatormod";
    public static final String NAME = "MCSSCommunicatorMod";
    public static final String WEBSITE = "https://www.mcserversoft.com";
    public static final String VERSION = "1.0";

    public static Configuration configuration;

    private static Logger logger;

    private static Config config;
    private EventListener eventListener;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        this.config = new Config(logger);

        // Register EventListener
        MinecraftForge.EVENT_BUS.register(new EventListener(config, logger));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Powering your Minecraft Server since Beta 1.5");
        logger.info("> Core-element of MC Server Soft. ");
        logger.info("> Provides real-time diagnostics and server telemetry.");
        logger.info(String.format("For more info visit: %s", MCSSCommunicatorMod.WEBSITE));
        logger.info(String.format("Server version: %s", ForgeVersion.getVersion()));
        logger.info(String.format("MCSSCommunicator version: %s", MCSSCommunicatorMod.VERSION));
    }
}
