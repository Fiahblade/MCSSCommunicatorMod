package com.mcserversoft.mcsscommunicatormod;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MCSSCommunicatorMod.MODID, name = MCSSCommunicatorMod.NAME, version = MCSSCommunicatorMod.VERSION)
public class MCSSCommunicatorMod {

    public static final String MODID = "mcsscommunicatormod";
    public static final String NAME = "MCSSCommunicatorMod";
    public static final String WEBSITE = "https://www.mcserversoft.com";
    public static final String VERSION = "1.0";

    private static Logger logger;

    private EventListener eventListener;

    @Comment({
        " Core-element of MC Server Soft.",
        " Provides real-time diagnostics and server telemetry.",
        "",
        "URL of the mcss internal webserver"
    })
    public static String url = "{0}";

    @Comment({
        "GUID of the mcss server"
    })
    public static String serverGUID = "{1}";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Loading config
        Config config = new Config(url,serverGUID);
      //  config.LoadFromDisk();

        // Register EventListener
        this.eventListener = new EventListener(config, logger);

        // Loading complete
        logger.info("Powering your Minecraft Server since Beta 1.5");
        logger.info(String.format("> Core-element of MC Server Soft. "));
        logger.info(String.format("> Provides real-time diagnostics and server telemetry."));
        logger.info(String.format("For more info visit: %s", MCSSCommunicatorMod.WEBSITE));
        logger.info(String.format("Server version: %s", ForgeVersion.getVersion()));

        logger.info(String.format("MCSSCommunicator version: %s", MCSSCommunicatorMod.VERSION));
    }
}
