package com.sam.jcc.cloud;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Саша on 14.06.2017.
 */
public class PropertyResolverHelper {

    private final String[] PROTOCOLS = new String[]{"http", "https", "jdbc:mysql"};

    public static void main(String[] args) {
        try {
            URL url = new URL("http", "localhost", 1000, null);
            url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns true if specified string is a valid protocol name.
     */
    private boolean isValidProtocol(String protocol) {
        int len = protocol.length();
        if (len < 1)
            return false;
        char c = protocol.charAt(0);
        if (!Character.isLetter(c))
            return false;
        for (int i = 1; i < len; i++) {
            c = protocol.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '+' &&
                    c != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean isProtocolValid(String protocol) {

        if(isEmpty(protocol)) {
            return false;
        }
        if (!Character.isLetter(protocol.charAt(0))) {
            return false;
        }
        for (String p : PROTOCOLS) {
            if(p.equals(protocol)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHostValid(String host) {

        if(isEmpty(host)) {
            return false;
        }
        //https://stackoverflow.com/questions/2345063/java-common-way-to-validate-and-convert-hostport-to-inetsocketaddress
        //https://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
        /*String ipPattern = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)";
        String ipV6Pattern = "\\[([a-zA-Z0-9:]+)\\]:(\\d+)";
        String hostPattern = "([\\w\\.\\-]+):(\\d+)";  // note will allow _ in host name
        Pattern p = Pattern.compile( ipPattern + "|" + ipV6Pattern + "|" + hostPattern );
        Matcher m = p.matcher( someString );
        if( m.matches() ) {
            if( m.group(1) != null ) {
                // group(1) IP address, group(2) is port
            } else if( m.group(3) != null ) {
                // group(3) is IPv6 address, group(4) is port
            } else if( m.group(5) != null ) {
                // group(5) is hostname, group(6) is port
            } else {
                // Not a valid address
            }
        }*/
        return false;
    }

    private boolean isEmpty(String param) {
        return (param == null || param.length() < 1);
    }
}
