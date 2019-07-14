package com.mcserversoft.mcsscommunicatormod.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;

public class IOUtils {

    public static JsonObject jsonReader(String filename) {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(new FileReader(filename)).getAsJsonObject();
            return obj;

        } catch (Exception e) {
            return new JsonObject();
        }
    }

    public static boolean jsonWriter(String filename, JsonObject json) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString());
            file.flush();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
