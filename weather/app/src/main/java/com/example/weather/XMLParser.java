package com.example.weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;



public class XMLParser {

    public Map<String, String> getMap(InputStream is) {
        Map<String, String> map = new HashMap<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new BufferedReader(new InputStreamReader(is)));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName();
                    if ("key".equals(name)) {
                        String key = parser.nextText();
                        parser.next();
                        parser.next();
                        String value = parser.nextText();
                        map.put(key, value);
                    }
                }
                parser.next();
                eventType = parser.getEventType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}