package tphy.peis.util;


import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class StringUtil {
    public static List quoteStrList(List<String> list) {
        List<String> tmpList = list;
        list = new ArrayList();
        Iterator<String> i = tmpList.iterator();
        while (i.hasNext()) {
            String str = i.next();
            str = "'" + str + "'";
            list.add(str);
        }
        return list;
    }


    public static String join(List list, String delim) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        Iterator<String> i = list.iterator();
        while (i.hasNext()) {
            buf.append(i.next());
            if (i.hasNext()) {
                buf.append(delim);
            }
        }
        return buf.toString();
    }


    public static String bytesToStringCN(byte[] b) {
        StringBuffer result = new StringBuffer("");
        int length = b.length;
        for (int i = 0; i < length; i++) {
            result.append((char) (b[i] & 0xFF));
        }
        String res = result.toString();
        String resu = null;
        try {
            resu = new String(res.trim().getBytes("ISO-8859-1"), "GBK");
        } catch (Exception exception) {
        }

        return resu;
    }


    public static List split(String str, String delim) {
        List<String> splitList = null;
        StringTokenizer st = null;

        if (str == null) {
            return splitList;
        }

        if (delim != null) {
            st = new StringTokenizer(str, delim);
        } else {
            st = new StringTokenizer(str);
        }

        if (st != null && st.hasMoreTokens()) {
            splitList = new ArrayList();

            while (st.hasMoreTokens()) {
                splitList.add(st.nextToken());
            }
        }
        return splitList;
    }

    public static String createBreaks(String input, int maxLength) {
        char[] chars = input.toCharArray();
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);
        int count = 0;
        int cur = 0;
        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(chars[i])) {
                count = 0;
            }
            if (count >= maxLength) {
                count = 0;
                buf.append(chars, cur, i - cur).append(" ");
                cur = i;
            }
            count++;
        }
        buf.append(chars, cur, len - cur);
        return buf.toString();
    }


    public static String escapeSQLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\\') {
                buf.append("\\\\");
            } else if (ch == '\'') {
                buf.append("''");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }


    public static String deHTML(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer();
        String ch = "%20";
        int i = 0;
        do {
            i = input.indexOf(ch);
            buf.append(String.valueOf(input.substring(0, i)) + " ");
            input = input.substring(i + 3, input.length());
        } while (input.indexOf(ch) > -1);
        buf.append(input);
        return buf.toString();
    }


    public static String enHTML(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == ' ') {
                buf.append("%20");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }


    public static String escapeHTMLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '&') {
                buf.append("&amp;");
            } else if (ch == '"') {
                buf.append("&quot;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }


    public static String convertNewlines(String input) {
        input = replace(input, "\r\n", "\n");
        input = replace(input, "\n", "<BR />");
        return input;
    }


    public static String replace(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        int i = mainString.lastIndexOf(oldString);
        if (i < 0) {
            return mainString;
        }
        StringBuffer mainSb = new StringBuffer(mainString);
        while (i >= 0) {
            mainSb.replace(i, i + oldString.length(), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }


    public static boolean nullOrBlank(String param) {
        return (param == null || param.trim().equals(""));
    }

    public static String notNull(String param) {
        return (param == null) ? "" : param.trim();
    }


    public static int parseInt(String param) {
        int i = 0;
        try {
            i = Integer.parseInt(param);
        } catch (Exception e) {
            i = (int) parseFloat(param);
        }
        return i;
    }

    public static long parseLong(String param) {
        long l = 0L;
        try {
            l = Long.parseLong(param);
        } catch (Exception e) {
            l = (long) parseDouble(param);
        }
        return l;
    }

    public static float parseFloat(String param) {
        float f = 0.0F;
        try {
            f = Float.parseFloat(param);
        } catch (Exception exception) {
        }


        return f;
    }

    public static double parseDouble(String param) {
        double d = 0.0D;
        try {
            d = Double.parseDouble(param);
        } catch (Exception exception) {
        }


        return d;
    }


    public static boolean isDouble(String param) {
        try {
            Double.parseDouble(param);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isInt(String param) {
        try {
            Integer.parseInt(param);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static boolean parseBoolean(String param) {
        if (nullOrBlank(param)) {
            return false;
        }
        switch (param.charAt(0)) {
            case '1':
            case 'T':
            case 'Y':
            case 't':
            case 'y':
                return true;
        }
        return false;
    }


    public static String convertURL(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length() + 25);
        char[] chars = input.toCharArray();
        int len = input.length();
        int index = -1;
        int i = 0;
        int j = 0;
        int oldend = 0;
        while (++index < len) {
            char cur = chars[i = index];
            j = -1;
            if (((cur == 'f' && index < len - 6 && chars[++i] == 't' &&
                    chars[++i] == 'p') || (cur == 'h' && (i = index) < len - 7 &&
                    chars[++i] == 't' && chars[++i] == 't' &&
                    chars[++i] == 'p' && (
                    chars[++i] == 's' || chars[--i] == 'p'))) &&
                    i < len - 4 &&
                    chars[++i] == ':' &&
                    chars[++i] == '/' &&
                    chars[++i] == '/') {
                j = ++i;
            }
            if (j > 0) {
                if (index == 0 || ((cur = chars[index - 1]) != '\'' &&
                        cur != '"' && cur != '<' && cur != '=')) {
                    cur = chars[j];
                    while (j < len &&
                            cur != ' ' && cur != '\t' && cur != '\'' &&
                            cur != '"' && cur != '<' && cur != '[' &&
                            cur != '\n' && (cur != '\r' || j >= len - 1 ||
                            chars[j + 1] != '\n')) {


                        if (++j < len) {
                            cur = chars[j];
                        }
                    }
                    cur = chars[j - 1];
                    if (cur == '.' || cur == ',' || cur == ')' || cur == ']') {
                        j--;
                    }
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href=\"");
                    buf.append(chars, index, j - index);
                    buf.append('"');
                    buf.append(" target=\"_blank\"");
                    buf.append('>');
                    buf.append(chars, index, j - index);
                    buf.append("</a>");
                } else {
                    buf.append(chars, oldend, j - oldend);
                }
                oldend = index = j;
                continue;
            }
            if (cur == '[' && index < len - 6 &&
                    chars[i = index + 1] == 'u' && chars[++i] == 'r' &&
                    chars[++i] == 'l' && (
                    chars[++i] == '=' || chars[i] == ' ')) {
                j = ++i;

                int u2 = input.indexOf("]", j), u1 = u2;
                if (u1 > 0) {
                    u2 = input.indexOf("[/url]", u1 + 1);
                }
                if (u2 < 0) {
                    buf.append(chars, oldend, j - oldend);
                    oldend = j;
                } else {
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href =\"");
                    String href = input.substring(j, u1).trim();
                    if (href.indexOf("javascript:") == -1 &&
                            href.indexOf("file:") == -1) {
                        buf.append(href);
                    }
                    buf.append("\" target=\"_blank");
                    buf.append("\">");
                    buf.append(input.substring(u1 + 1, u2).trim());
                    buf.append("</a>");
                    oldend = u2 + 6;
                }
                index = oldend;
            }
        }
        if (oldend < len) {
            buf.append(chars, oldend, len - oldend);
        }
        return buf.toString();
    }


    public static String dspHtml(String input) {
        String str = input;
        str = createBreaks(str, 80);
        str = escapeHTMLTags(str);
        str = convertURL(str);
        str = convertNewlines(str);
        return str;
    }

    public static String lpad(String s, int iLength, String sNewChar) {
        String sTmp = s;
        if (sTmp != null) {
            int iCount = iLength - sTmp.length();
            for (int i = 0; i < iCount; i++) {
                sTmp = String.valueOf(sNewChar) + sTmp;
            }
        }
        return sTmp;
    }

    public static String rpad(String s, int iLength, String sNewChar) {
        String sTmp = s;
        if (sTmp != null) {
            int iCount = iLength - sTmp.length();
            for (int i = 0; i < iCount; i++) {
                sTmp = String.valueOf(sTmp) + sNewChar;
            }
        }
        return sTmp;
    }

    public static String md5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(message.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString(array[i] & 0xFF | 0x100)
                        .substring(1, 3));
            }
            md5 = sb.toString();
        } catch (Exception exception) {
        }

        return md5.toLowerCase();
    }

    public static String getpasswd(String strkey, String passwd) {
        String pass = "";
        String str = "0123456789abcdefghijklmnopqrstrvwxyz";
        for (int i = 0; i < passwd.length(); i++) {
            char a = passwd.charAt(i);
            int j = str.indexOf(String.valueOf(a));
            pass = String.valueOf(pass) + strkey.substring(j, j + 1);
        }
        return pass;
    }


    public static List getnorepeat(String str) {
        String[] zy = str.split(",");
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < zy.length; i++) {

            if (!list.contains(zy[i]))
                list.add(zy[i]);
        }
        return list;
    }

    public static String gbEncoding(String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = String.valueOf(unicodeBytes) + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    public static String decodeUnicode(String dataStr) {
        int start = 0;
        int end = 0;
        StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append((new Character(letter)).toString());
            start = end;
        }
        return buffer.toString();
    }


    public static String comparesort(String dataStr) {
        String[] pss = dataStr.split(",");

        String newstr1 = null;
        for (int i = 0; i < pss.length; i++) {
            if (newstr1 == null) {
                newstr1 = pss[i];
            } else {
                newstr1 = String.valueOf(newstr1) + "," + pss[i];
            }

        }


        return newstr1;
    }


    public static String addOne(String testStr) {
        String[] strs = testStr.split("[^0-9]");
        String numStr = strs[strs.length - 1];
        if (numStr != null && numStr.length() > 0) {
            int n = numStr.length();
            Double num = Double.valueOf(Double.parseDouble(numStr) + 1.0D);
            BigDecimal bigDecimal = new BigDecimal(num.doubleValue());
            String added = bigDecimal.toString();
            n = Math.min(n, added.length());

            return testStr.subSequence(0, testStr.length() - n) + added;
        }
        throw new NumberFormatException();
    }



    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength++;
            }
        }
        return valueLength;
    }


    public static List getCheckStringInt(String str) {
        String[] zy = str.split(",");
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < zy.length; i++) {
            if (isInt(zy[i]))
                list.add(zy[i]);
        }
        return list;
    }


    public static String numToUpper(int num) {
        String[] u = {"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] u1 = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        if (num >= 10 && num < 20) {
            rstr = "十" + u1[Integer.parseInt((new StringBuilder(String.valueOf(str[1]))).toString())];
        } else {
            for (int i = 0; i < str.length; i++) {
                rstr = String.valueOf(rstr) + u[Integer.parseInt((new StringBuilder(String.valueOf(str[i]))).toString())];
            }
        }
        return "（" + rstr + "）";
    }


}


