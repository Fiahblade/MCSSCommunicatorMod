package com.mcserversoft.mcsscommunicatormod;

import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.logging.log4j.Logger;

public class HTTPClient {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private URL baseUrl;
    private Logger logger;

    public HTTPClient(String url, Logger logger) {
        this.logger = logger;

        boolean success = setUrl(url);
        if (!success) {
            setUrl("http://localhost:9696/api");
        }
    }

    private boolean setUrl(String url) {
        try {
            this.baseUrl = new URL(url);
        } catch (MalformedURLException ex) {
            logger.error("Failed to parse url from config File. Will use the default value.");
            return false;
        }
        return true;
    }

    public void post(String path, String json) {

        try {
            String url = String.format("%s/%s", baseUrl, path);
            
            /*
             logger.info(json);
             logger.info(url);
             */
            
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            /*         
             try (Response response = client.newCall(request).execute()) {
             logger.info(response.body().string());
             logger.info(response.code());
             }
             */

            //TODO add custom messages when mcss is not running etc.
            //TODO add debug option
        } catch (Exception ex) {
            logger.error(String.format("Failed to post data to mcss: ", ex.getMessage()));
        }
    }
}
