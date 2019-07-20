package com.mcserversoft.mcsscommunicatormod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import org.apache.logging.log4j.Logger;

public class EventListener {

    private final EventProcessor eventProcessor;

    public EventListener(Logger logger, Config config) {
        this.eventProcessor = new EventProcessor(logger, config);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        eventProcessor.playerLogin(event.player);
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        eventProcessor.playerQuit(event.player);
    }
}
