package com.mcserversoft.mcsscommunicatormod;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;

public class Config {

    private String url;
    private String serverGUID;

    private Logger logger;
    private static Configuration configuration;

    Config(Logger logger) {
        this.logger = logger;

        File configFile = new File(Loader.instance().getConfigDir(), "mcsscommunicatormod/mcsscommunicatormod.cfg");
        configuration = new Configuration(configFile);

        try {
            configuration.load();

            Property urlProperty = configuration.get(Configuration.CATEGORY_GENERAL,
                    "url",
                    "\"{0}\"",
                    "url of the mcss internal webserver");

            Property serverGUIDProperty = configuration.get(Configuration.CATEGORY_GENERAL,
                    "serverGUID",
                    "\"{1}\"",
                    "GUID of the mcss server");

            // trim quotes from url
            this.url = urlProperty.toString().replace("\"", "");
            this.serverGUID = serverGUIDProperty.getString().replace("\"", "");

        } catch (Exception ex) {
            logger.info(String.format("Could not read config file: (%s)", ex.getCause()));
        } finally {
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getServerGUID() {
        return this.serverGUID;
    }
}
