package Assignment1.ExternalMethods;

import Assignment1.Classes.UtilityAndAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import static Assignment1.Main.Config.NUM_COLS;
import static Assignment1.Main.Config.NUM_ROWS;

public class FileIO {
    public static void writeToFile(List<UtilityAndAction[][]> ListOfAUArr, int scale, String directory, String fileName) {

        StringBuilder sb = new StringBuilder();
        String pattern = "00.0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        for (int col = 0; col < NUM_COLS*scale; col++) {
            for (int row = 0; row < NUM_ROWS*scale; row++) {

                Iterator<UtilityAndAction[][]> iter = ListOfAUArr.iterator();
                while(iter.hasNext()) {

                    UtilityAndAction[][] actionAndUtility = iter.next();
                    sb.append(decimalFormat.format(
                            actionAndUtility[row][col].getUtility()), 0, 7);

                    if(iter.hasNext()) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        }

        writeToFile(sb.toString().trim(), directory,  fileName + ".csv");
    }

    public static void writeToFile(String content, String directory, String fileName)
    {
        try
        {
            File dir = new File(directory);
            dir.mkdirs();
            String filePath = directory + fileName;
            FileWriter fw = new FileWriter(new File(filePath), false);

            fw.write(content);
            fw.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
