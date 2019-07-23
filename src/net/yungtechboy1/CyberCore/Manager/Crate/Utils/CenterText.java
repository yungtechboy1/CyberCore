package Crate;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class CenterText {
    String Text = "";
    Integer size = 0;
    private final static int CENTER_PX = 20;

    public CenterText(String text, int Chars) {
        Text = text;
        size = Chars;
    }

    public static String sendCenteredMessage(String message, Integer cp){
        if(message == null || message.equals("")) return "";

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        cp = cp / 2;
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = cp - halvedMessageSize;
        if ((toCompensate & 1) != 0 )toCompensate -= 1;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength*2;
        }
        return sb.toString() + message+sb.toString();//+"{|"+halvedMessageSize+" | "+toCompensate;
    }

    public static int getsize(String message){
        if(message == null || message.equals("")) return 0;

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        return messagePxSize;
    }
}
