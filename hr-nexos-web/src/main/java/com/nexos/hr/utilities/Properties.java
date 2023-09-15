
package com.nexos.hr.utilities;

import java.util.ResourceBundle;

public class Properties {

    public static String getBundle(String file, String key){
        return ResourceBundle.getBundle(file).getString(key);
    }
}
