package org.example.helpers;

public class StringUtils {

    public static String formatProductNameWithHyphens(String productName){
        return productName.toLowerCase().replace(" ", "-");
    }
}
