package Assignment1.ExternalMethods;

import Assignment1.Maze.GridState;
import Assignment1.Classes.UtilityAndAction;

import java.text.DecimalFormat;

import static Assignment1.ExternalMethods.CommonMethods.*;

public class DisplayHelper {


    public static void displayActionPolicy(final UtilityAndAction[][] UtilityAndAction) {

        StringBuilder str = new StringBuilder();
        int rows = UtilityAndAction.length;
        int cols = UtilityAndAction[0].length;

        str.append(getSectionHeader("Action Policy", true));
        for (int row = 0; row < rows; row++) {
            if (row==0) {
                for (int r = 0; r < rows + 1; r++) {
                    if (r-1 < 0) {
                        str.append("     "); continue;
                    }
                    str.append(r-1);
                    str.append("    ");
                }
                str.append("\n");
            }
            str = addShortInBetweenDesign(str, cols);
            str.append("\n");
            for (int col = 0; col < cols; col++) {
                if (col==0) {
                    str.append(row);
                    str.append("  ");
                }
                if(UtilityAndAction[row][col].toString() == "X"){
                    str.append("| " + UtilityAndAction[row][col].toString() + "  ");
                } else {
                    str.append("| " + UtilityAndAction[row][col].toString() + " ");
                }


                if (col==rows-1) {
                    str.append("|");
                }
            }
            str.append("\n");
        }
        str = addShortInBetweenDesign(str, cols);

        System.out.println(str.toString());
    }

    public static void displayUtilities(final GridState[][] grid, final UtilityAndAction[][] UtilityAndAction) {
        int rows = UtilityAndAction.length;
        int cols = UtilityAndAction[0].length;

        printSectionHeader("Reference utilities of states", true);
        printDetails("Coordinates are in (col,row) format. Top-left corner is (0,0) <");
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {

                if (grid[row][col].isVisitable()) {
                    System.out.printf("(%1d,%1d) : %-2.6f%n", col, row,
                            UtilityAndAction[row][col].getUtility());
                }
            }
        }
    }

    public static void displayUtilitiesGrid(final UtilityAndAction[][] UtilityAndAction) {
        int rows = UtilityAndAction.length;
        int cols = UtilityAndAction[0].length;

        String pattern = "0.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        StringBuilder str = new StringBuilder();
        double util;

        str.append(getSectionHeader("Utilities Grid", true));
        for (int row = 0; row < rows; row++) {
            if (row == 0) {
                for (int r = 0; r < rows + 1; r++) {
                    if (r-1 < 0) {
                        str.append("        "); continue;
                    }
                    str.append(r-1);
                    str.append("          ");
                }
                str.append("\n");
            }
            str = addLongInBetweenDesign(str, cols);
            str.append("\n");
            for (int col = 0; col < cols; col++) {
                if (col == 0) {
                    str.append(row);
                    str.append("  ");
                }
                util = UtilityAndAction[row][col].getUtility();
                if (util == 0) {
                    str.append("|  " + "██████" + "  ");
                } else if (util < 0) {
                    str.append("| " + decimalFormat.format(util) + "  ");
                } else {
                    str.append("|  " + decimalFormat.format(util) + "  ");
                }

                if (col == rows - 1) {
                    str.append("|");
                }
            }
            str.append("\n");
        }
        str = addLongInBetweenDesign(str, cols);

        System.out.println(str.toString());
    }

    public static StringBuilder addShortInBetweenDesign(StringBuilder str, int cols) {
        str.append("   ");
        for (int col = 0; col < cols; col++) {
            str.append("+————");
        }
        return str.append("+");
    }

    public static StringBuilder addLongInBetweenDesign(StringBuilder str, int cols) {
        str.append("   ");
        for (int col = 0; col < cols; col++) {
            str.append("+——————————");
        }
        return str.append("+");
    }

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getHeader(String msg, boolean toUpperCase) {
        StringBuilder str = new StringBuilder();
        msg = convertToUpperCase(msg, toUpperCase);
        str.append("\n▒▒▒▒▒▒▒▒▒ " + msg + " ▒▒▒▒▒▒▒▒▒\n");
        return str.toString();
    }

    public static String getSectionHeader(String msg, boolean toUpperCase) {
        StringBuilder str = new StringBuilder();
        msg = convertToUpperCase(msg, toUpperCase);
        str.append("=========== " + msg + " ===========\n");
        return str.toString();
    }

    public static String getDetails(String msg) {
        StringBuilder str = new StringBuilder();
        str.append("> ");
        str.append(msg + "\n");
        return str.toString();
    }

    public static void printHeader(String msg, boolean toUpperCase) {
        msg = getHeader(msg, toUpperCase);
        System.out.print(msg);
    }

    public static void printSectionHeader(String msg, boolean toUpperCase) {
        msg = getSectionHeader(msg, toUpperCase);
        System.out.print(msg);
    }

    public static void printDetails(String msg) {
        msg = getDetails(msg);
        System.out.print(msg);
    }


}
