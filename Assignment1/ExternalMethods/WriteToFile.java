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

/**
 * WriteToFile class is used to write all the utility estimates in the csv file
 */
public class WriteToFile {
    public static void writeToFile(List<UtilityAndAction[][]> ListOfAUArr, int scale, String directory, String fileName) {

        StringBuilder sb = new StringBuilder();
        String pattern = "00.0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        for (int col = 0; col < NUM_COLS*scale; col++) {
            for (int row = 0; row < NUM_ROWS*scale; row++) {

                Iterator<UtilityAndAction[][]> iter = ListOfAUArr.iterator();
                while(iter.hasNext()) {

                    UtilityAndAction[][] utilityAndActionArr = iter.next();
                    sb.append(decimalFormat.format(utilityAndActionArr[row][col].getUtility()), 0, 7);

                    if(iter.hasNext()) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        }

        fileName = fileName + ".csv";
        try
        {
            File dir = new File(directory);
            dir.mkdirs();
            String filePath = directory + fileName;
            FileWriter fw = new FileWriter(new File(filePath), false);

            fw.write(sb.toString().trim());
            fw.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
