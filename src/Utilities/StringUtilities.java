/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

/**
 *
 * @author marti
 */
public class StringUtilities {
    public static String Generate_Repeated_String(String str, int times) {
        String response = "";
        while (times > 0) {
            response = response + str;
            times--;
        }
        return response;
    }

    public static String Standard_Lowercase_Str(String str) {
        String response = "";
        str = str.toLowerCase();
        String[] split = str.split(" ");
        for (String e : split)
            response += e + " ";
        return response.trim();
    }

    public static String Standard_CamelCase_Str(String str, String delimiter) {
        String response = "";
        str = str.toLowerCase();
        String[] split = str.split(delimiter);
        for (String e : split)
            response += e.substring(0, 1).toUpperCase() + e.substring(1) + " ";
        return response.trim();
    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s", string);
    }
}
