package com.mcserversoft.mcsscommunicatormod;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;
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

	public static String executePost(String url, String param) throws Exception {
		String charset = "UTF-8";
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

		try (OutputStream output = connection.getOutputStream()) {
			output.write(param.getBytes(charset));
		}

		InputStream response = connection.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(response, writer, charset);
		String theString = writer.toString();
		return theString;
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
