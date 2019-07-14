package com.mcserversoft.mcsscommunicatormod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcserversoft.mcsscommunicatormod.utils.IOUtils;
import org.apache.logging.log4j.Logger;

public class Config {

    private String url;
    private String serverGUID;

    private Logger logger;

    public Config(Logger logger) {
        this.logger = logger;
    }

   public Config(String url, String serverGUID) {
        this.url = url;
        this.serverGUID = serverGUID;
    }

    public String getUrl() {
        return this.url;
    }

    public String getServerGUID() {
        return this.serverGUID;
    }

   /* public void LoadFromDisk() {
        try {
            JsonParser parser = new JsonParser();
            JsonObject json = IOUtils.ymlReader("config.yml");

            this.url = json.get("url").getAsString();
            this.serverGUID = json.get("serverGUID").getAsString();

        } catch (Exception e) {
            logger.warn("Could not determine the version of MCSS, this could cause problems...");
        }
    }*/
}
