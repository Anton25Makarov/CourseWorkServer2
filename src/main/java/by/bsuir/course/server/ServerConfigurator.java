package by.bsuir.course.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConfigurator {

    public ServerProperties getProperties() {
        String json = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(getClass().getResourceAsStream("/serverProperties.json")));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            json = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ServerProperties serverProperties = gson.fromJson(json, ServerProperties.class);
        return serverProperties;
    }
}