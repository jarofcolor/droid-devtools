package real.droid.devtools.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileUtil {

    public static ArrayList<String> readLines(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                lines.add(str);
            }
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
