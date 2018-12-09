package by.bsuir.course.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Configurator {
    private static final String FILE_PATH = "src/main/resources/dbProperties.json";
//    private static final String FILE_PATH = "src/main/java/by/bsuir/course/resources/dbProperties.json";

    public DataBaseProperties getProperties() {
        String json = "";
        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(getClass().getResourceAsStream("/dbProperties.json")));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line).append("\n");
            }

            json = stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        DataBaseProperties dataBaseProperties = gson.fromJson(json, DataBaseProperties.class);
        return dataBaseProperties;
    }
}