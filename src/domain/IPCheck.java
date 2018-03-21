package domain;

import java.util.regex.Pattern;

public class IPCheck {

    public static boolean isIPValid(String ip) {
        Pattern p = Pattern.compile("^"
                + "((([0-9]{1,3}\\.){3})[0-9]{1,3})" // Ip
                + ":"
                + "[0-9]{1,5}$"); // Port

        return p.matcher(ip).matches();
    }
}
