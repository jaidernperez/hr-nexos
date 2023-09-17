
package com.nexos.hr.utilities;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

public class Properties {

    public static String getBundle(String file, String key){
        Locale userLocale = FacesContext.getCurrentInstance().getELContext().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(file, userLocale);
        return bundle.getString(key);
    }
}
