package com.mcserversoft.mcsscommunicatormod;

import com.google.gson.Gson;
import com.mcserversoft.mcsscommunicatormod.dto.PlayerDTO;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.Logger;

public class EventProcessor {

    private final Config config;
    private final HTTPClient client;

    public EventProcessor(Logger logger, Config config) {
        this.config = config;
        this.client = new HTTPClient(logger, config.getUrl());
    }

    public void playerLogin(EntityPlayer player) {
        PlayerDTO playerDTO = createPlayerDTO(player);
        client.post("player/login", new Gson().toJson(playerDTO));
    }

    public void playerQuit(EntityPlayer player) {
        PlayerDTO playerDTO = createPlayerDTO(player);
        client.post("player/quit", new Gson().toJson(playerDTO));
    }

    private PlayerDTO createPlayerDTO(EntityPlayer player) {
        String username = player.getDisplayName().getUnformattedText();
        String uuid = player.getUniqueID().toString();
        String serverGuid = config.getServerGUID();

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUsername(username);
        playerDTO.setUUID(uuid);
        playerDTO.setServerUUID(serverGuid);

        return playerDTO;
    }
}
