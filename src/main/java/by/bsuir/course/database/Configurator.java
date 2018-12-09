package by.bsuir.course.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Configurator {
    private static final String FILE_PATH = "src/main/resources/properties.json";
//    private static final String FILE_PATH = "src/main/java/by/bsuir/course/resources/properties.json";

    public DataBaseProperties getProperties() {
        String json = "";
        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/properties.json")));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line).append("\n");
            }

            json = stringBuffer.toString();
//            System.out.println(stringBuffer);

           /* List<String> strings = Files.readAllLines(Paths.get(FILE_PATH));

            for (String string : strings) {
                json += string;
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        DataBaseProperties dataBaseProperties = gson.fromJson(json, DataBaseProperties.class);
        return dataBaseProperties;
    }
}