package com.mcserversoft.mcsscommunicatormod;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.Logger;

public class HTTPClient {
	private final Logger logger;
	private final boolean debug;

	private URL baseUrl;

	public HTTPClient(Logger logger, Config config) {
		this.logger = logger;
		this.debug = config.getIsDebugEnabled();

		boolean success = setUrl(config.getUrl());
		if (!success) {
			setUrl("http://localhost:25560/api");
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

	public String executePost(String path, String json) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(json.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public void post(String path, String json) {

		try {
			String url = String.format("%s/%s", baseUrl, path);

			if (debug) {
				logger.info(String.format("[DEBUG] %s", json));
				logger.info(String.format("[DEBUG] %s", url));
			}

			String response = executePost(url, json);
			if (debug) {
				logger.info(String.format("[DEBUG] %s", response));
			}

		} catch (Exception ex) {
			logger.error(String.format("Failed to post data to mcss: ", ex.getMessage()));
		}
	}
}
