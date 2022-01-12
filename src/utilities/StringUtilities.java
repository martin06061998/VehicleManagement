/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author marti
 */
public class StringUtilities {
    public static String generateRepeatedString(String target, int times) {
        String response = "";
        while (times > 0) {
            response = response + target;
            times--;
        }
        return response;
    }

    public static String toCamelCase(String target) {
        String response = "";
        target = target.toLowerCase();
        String[] split = target.split("\\s");
        for (String e : split)
            response += e.substring(0, 1).toUpperCase() + e.substring(1) + " ";
        return response.trim();
    }

    /*public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s", string);
    }*/
}
