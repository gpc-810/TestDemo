package com.demo.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCF_Util {
    /*
     * 解码
     */
    public static String qpDecoding(String str) {
        if (str == null) {
            return "";
        }
        try {
            str = str.replaceAll("=\n", "");
            byte[] bytes = str.getBytes("US-ASCII");
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if (b != 95) {
                    bytes[i] = b;
                } else {
                    bytes[i] = 32;
                }
            }
            if (bytes == null) {
                return "";
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i];
                if (b == '=') {
                    try {
                        int u = Character.digit((char) bytes[++i], 16);
                        int l = Character.digit((char) bytes[++i], 16);
                        if (u == -1 || l == -1) {
                            continue;
                        }
                        buffer.write((char) ((u << 4) + l));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.write(b);
                }
            }
            return new String(buffer.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 编码
     */

    public static String qpEncodeing(String str) {
        char[] encode = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < encode.length; i++) {
            if ((encode[i] >= '!') && (encode[i] <= '~') && (encode[i] != '=') && (encode[i] !=
                    '\n')) {
                sb.append(encode[i]);
            } else if (encode[i] == '=') {
                sb.append("=3D");
            } else if (encode[i] == '\n') {
                sb.append("\n");
            } else {
                StringBuffer sbother = new StringBuffer();
                sbother.append(encode[i]);
                String ss = sbother.toString();
                byte[] buf = null;
                try {
                    buf = ss.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (buf.length == 3) {
                    for (int j = 0; j < 3; j++) {
                        String s16 = String.valueOf(Integer.toHexString(buf[j]));
                        // 抽取中文字符16进制字节的后两位,也就是=E8等号后面的两位,
                        // 三个代表一个中文字符
                        char c16_6;
                        char c16_7;
                        if (s16.charAt(6) >= 97 && s16.charAt(6) <= 122) {
                            c16_6 = (char) (s16.charAt(6) - 32);
                        } else {
                            c16_6 = s16.charAt(6);
                        }
                        if (s16.charAt(7) >= 97 && s16.charAt(7) <= 122) {
                            c16_7 = (char) (s16.charAt(7) - 32);
                        } else {
                            c16_7 = s16.charAt(7);
                        }
                        sb.append("=" + c16_6 + c16_7);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 导入联系人
     *
     * @param in
     * @return
     */
    public static String importVCFFileContact(InputStream in, StringBuffer contentBuffer) {

        String name = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            if (contentBuffer == null) {
                throw new Exception("接受字符串是null");
            }
            String line;
            while ((line = nextLine(reader)) != null) {
                contentBuffer.append(line + "\r\n");
            }
            Pattern p = Pattern.compile("BEGIN:VCARD(\\r\\n)([\\s\\S\\r\\n\\.]*?)END:VCARD");

            Matcher m = p.matcher(contentBuffer.toString());
            while (m.find()) {
                String str = m.group(0);
                Pattern pn = Pattern.compile("N;([\\s\\S\\r\\n\\.]*?)([\\r\\n])");// 姓名
                Matcher mn = pn.matcher(str);

                while (mn.find()) {
                    if (mn.group(1).indexOf("ENCODING=QUOTED-PRINTABLE") > -1) {
                        name = mn.group(1).substring(mn.group(1).indexOf
                                ("ENCODING=QUOTED-PRINTABLE:") + "ENCODING=QUOTED-PRINTABLE:"
                                .length());
                        name = name.substring(name.indexOf(":") + 1);
                        if (name.indexOf(";") > -1) {
                            name = name.substring(0, name.indexOf(";"));
                        }
                        name = qpDecoding(name);
                    } else {
                        Pattern pnn = Pattern.compile("CHARSET=([A-Za-z0-9-]*?):");
                        Matcher mnn = pnn.matcher(mn.group(1));
                        while (mnn.find()) {
                            name = mn.group(1).substring(mn.group(1).indexOf(mnn.group(0)) + mnn
                                    .group(0).length());
                        }
                    }
                }


                //
                //                String cell = "";
                //                Pattern p1 = Pattern.compile("TEL;CELL:
                // ([\\s*\\d*\\s*\\d*\\s*\\d*]*?)([\\r\\n])");
                //                Matcher m1 = p1.matcher(str);
                //                while (m1.find()) {
                //                    String tel = m1.group(0);
                //                    cell = tel.substring("TEL;CELL:".length());
                //                }
                //                be.setMobile(cell);
                //
                //                String work = "";
                //                Pattern p2 = Pattern.compile("TEL;WORK:\\d*
                // ([\\s*\\d*\\s*\\d*\\s*\\d*]*?)([\\r\\n])");
                //                Matcher m2 = p2.matcher(str);
                //                while (m2.find()) {
                //                    System.out.println("workTel :  " + m2.group(0));
                //                    work = m2.group(0).substring(m2.group(0).indexOf("TEL;
                // WORK:") + "TEL;WORK:".length());
                //                }
                //                be.setWorkMobile(work);
                //
                //                String home = "";
                //                Pattern p3 = Pattern.compile("TEL;HOME:
                // ([\\s*\\d*\\s*\\d*\\s*\\d*]*?)([\\r\\n])");
                //                Matcher m3 = p3.matcher(str);
                //                while (m3.find()) {
                //                    home = m3.group(0).substring(m3.group(0).indexOf("TEL;
                // HOME:") + "TEL;HOME:".length());
                //                }
                //                be.setTelePhone(home);
                //
                //                String email = "";
                //                Pattern p4 = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
                //                Matcher m4 = p4.matcher(str);
                //                while (m4.find()) {
                //                    email = m4.group(0);
                //                }
                //                be.setEmail(email);
                //                addressBeans.add(be);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }


    public static String nextLine(BufferedReader reader) throws IOException {
        String line;
        String nextLine;
        do {
            line = reader.readLine();
            if (line == null) {
                return null;
            }
        } while (line.length() == 0);
        while (line.endsWith("=")) {
            line = line.substring(0, line.length() - 1);
            line += reader.readLine();
        }
        reader.mark(1000);
        nextLine = reader.readLine();
        if ((nextLine != null) && (nextLine.length() > 0) && ((nextLine.charAt(0) == 0x20) ||
                (nextLine.charAt(0) == 0x09))) {
            line += nextLine.substring(1);
        } else {
            reader.reset();
        }
        line = line.trim();
        return line;
    }


}
