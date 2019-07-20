package com.mcserversoft.mcsscommunicatormod;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;

public class Config {

    private Configuration configuration;

    private String url;
    private String serverGUID;
    private boolean debug;

    Config(Logger logger) {
        File configFile = new File(Loader.instance().getConfigDir(), "MCSSCommunicatorMod/MCSSCommunicatorMod.cfg");
        this.configuration = new Configuration(configFile);

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

            Property debugProperty = configuration.get(Configuration.CATEGORY_GENERAL,
                    "debug",
                    false,
                    "Enable or disable debugging of mcss requests");

            // trim quotes from strings
            this.url = urlProperty.getString().replace("\"", "");
            this.serverGUID = serverGUIDProperty.getString().replace("\"", "");
            this.debug = debugProperty.getBoolean();

        } catch (Exception ex) {
            logger.warn(String.format("Could not read config file: (%s)", ex.getCause()));
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

    public boolean getIsDebugEnabled() {
        return this.debug;
    }
}
