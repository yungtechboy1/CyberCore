package net.yungtechboy1.CyberCore;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import java.util.Date;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class CyberUtils {

    public static final String NAME = TextFormat.DARK_AQUA+"Cyber"+TextFormat.GOLD+"Tech"+TextFormat.GREEN+"++";

    public static String toStringCode(Object... a) {
        String f = "";
        for (Object o : a) {
            f += o.toString() + "|";
        }
        f.substring(0, f.length() - 1);
        return f;
    }

    public static String[] fromStringCode(String s) {
        String[] ss = s.split("\\|");
        return ss;
    }

    public static void SendCommandUsage(Command c, CommandSender s) {
        s.sendMessage(TextFormat.YELLOW+"Usage: "+c.getUsage());
    }
    public static String getDifferenceBtwTime(Long dateTime) {

        long timeDifferenceMilliseconds = dateTime - new Date().getTime();
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = (long) (timeDifferenceMilliseconds / (1000 * 60 * 60 * 24 * 365));

        if (diffSeconds < 1) {
            return "one sec";
        } else if (diffMinutes < 1) {
            return diffSeconds + " seconds";
        } else if (diffHours < 1) {
            return diffMinutes + " minutes";
        } else if (diffDays < 1) {
            return diffHours + " hours";
        } else if (diffWeeks < 1) {
            return diffDays + " days";
        } else if (diffMonths < 1) {
            return diffWeeks + " weeks";
        } else if (diffYears < 12) {
            return diffMonths + " months";
        } else {
            return diffYears + " years";
        }
    }

    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }

    public static String convertBinaryToHexadecimal(String number) {
        String hexa = "";
        char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f' };
        if (number != null && !number.isEmpty()) {
            int decimal = convertBinaryToDecimal(number);
            while (decimal > 0) {
                hexa = hex[decimal % 16] + hexa;
                decimal /= 16;
            }
            System.out.println("The hexa decimal number is: " + hexa);
        }
        return hexa;
    }

    public static int convertBinaryToDecimal(String number) {
        int length = number.length() - 1;
        int decimal = 0;
        if (isBinary(number)) {
            char[] digits = number.toCharArray();
            for (char digit : digits) {
                if (String.valueOf(digit).equals("1")) {
                    decimal += Math.pow(2, length);
                }
                --length;
            }
            System.out.println("The decimal number is : " + decimal);
        }
        return decimal;
    }

    public static boolean isBinary(String number) {
        boolean isBinary = false;
        if (number != null && !number.isEmpty()) {
            long num = Long.parseLong(number);
            while (num > 0) {
                if (num % 10 <= 1) {
                    isBinary = true;
                } else {
                    isBinary = false;
                    break;
                }
                num /= 10;
            }
        }
        return isBinary;
    }
}
