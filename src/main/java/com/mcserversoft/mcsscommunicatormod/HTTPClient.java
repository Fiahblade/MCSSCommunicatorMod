package com.mcserversoft.mcsscommunicatormod;

import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.Logger;

public class HTTPClient {

    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final Logger logger;
    private final boolean debug;

    private URL baseUrl;

    public HTTPClient(Logger logger, Config config) {
        this.logger = logger;
        this.debug = config.getIsDebugEnabled();

        boolean success = setUrl(config.getUrl());
        if (!success) {
            setUrl("http://localhost:9696/api");
        }
    }

    private boolean setUrl(String url) {
        try {
            this.baseUrl = new URL(url);
        } catch (MalformedURLException ex) {
            logger.warn("Failed to parse url from config File. Will use the default value.");
            return false;
        }
        return true;
    }

    public void post(String path, String json) {

        try {
            String url = String.format("%s/%s", baseUrl, path);

            if (debug) {
                logger.info(String.format("[DEBUG] %s", json));
                logger.info(String.format("[DEBUG] %s", url));
            }

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            if (debug) {
                try (Response response = client.newCall(request).execute()) {
                    logger.info(String.format("[DEBUG] %s", response.body().string()));
                    logger.info(String.format("[DEBUG] %s", response.code()));
                }
            }

        } catch (Exception ex) {
            logger.error(String.format("Failed to post data to mcss: ", ex.getMessage()));
        }
    }
}
