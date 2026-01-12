package lc;

import java.util.ArrayList;
import java.util.List;

class RedstoreIP93 {
    String s;
    List<String> ips = new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        this.s = s;
        restore(1, 0, "");
        return ips;
    }

    public void restore(int pos, int i, String buf) {
        // validate remaining characters
        int remaining = s.length() - i;
        if (!(remaining >= (5 - pos) && remaining <= (5 - pos)*3)) {
            return;
        }

        if (pos == 4) {
            String part = s.substring(i);
            if (isInvalid(part)) {
                return;
            }
            String ip = String.format("%s.%s", buf, part);
            ips.add(ip);
            return;
        }

        for (int j = 1; j <= 3; j++) {
            int end = i + j;
            if (end > s.length()) end = s.length();
            String part = s.substring(i, end);
            if (isInvalid(part)) {
                return;
            }
            
            String ss = pos == 1 ? part : String.format("%s.%s", buf, part);
            restore(pos + 1, end, ss);
        }

    } 

    boolean isInvalid(String part) {
        if (part.length() > 1 && part.startsWith("0")) {
            return true;
        }

        if (Integer.parseInt(part) > 255) {
            return true;
        }

        return false;
    }
}