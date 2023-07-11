package org.vcs;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.regex.Pattern;

public class BodyProcessor {
    static private final Pattern pat = Pattern.compile("%([0-9a-fA-F]{2})", Pattern.DOTALL);
    static private final Pattern nlStrip = Pattern.compile("\r?\n", Pattern.DOTALL);
    static private Properties pro = new Properties();

    public static String convert2Vietnamese(String body) throws IOException {
        body = nlStrip.matcher(body).replaceAll("%0a");
        body = pat.matcher(body).replaceAll("\\\\u00$1");
        pro.load(new StringReader("vn = " + body));
        return pro.getProperty("vn");
    }
}
