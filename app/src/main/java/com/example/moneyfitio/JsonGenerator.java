import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Driver {
    public static void main(String[] args) {
        File jsonFile = new File("Sample.json");
        try {
            if (jsonFile.createNewFile()) {
                System.out.println("File created");
                FileWriter fileWriter = new FileWriter("Sample.Json");
                fileWriter.write("{");
                fileWriter.write("\"data\":[");
                for (int i = 0; i < 30; i++) {
                    fileWriter.write(jsonObject(i + 1));
                }
                fileWriter.write("]");
                fileWriter.write("}");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String jsonObject(int i) {
        String day = String.valueOf(i);
        if (i < 10) {
            day = "0" + String.valueOf(i);
        }
        String date = "2020-01-" + day;
        String investment_invest_value = String.valueOf(getRandom(900, 1000));
        String investment_current_value = String.valueOf(getRandom(800, 1500));
        String res = "{";
        res += "\"date\":" + "\"" + date + "\",";
        res += "\"investment_invest_value\":" + investment_invest_value + ",";
        res += "\"investment_current_value\":" + investment_current_value;
        res += "}";
        if (i < 30) {
            res += ",";
        }
        return res;
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }
}