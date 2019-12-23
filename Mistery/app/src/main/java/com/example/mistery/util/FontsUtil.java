package com.example.mistery.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontsUtil {
    public static void setDefaultFont(Context context,String staticTypefaceFieldName,String fontAssetName){
        Typeface regular = Typeface.createFromAsset(context.getAssets(),fontAssetName);
        replaceFont(staticTypefaceFieldName,regular);
    }

    private static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null,newTypeface);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
