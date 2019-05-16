package net.yungtechboy1.CyberCore.Classes.New;

/**
 * Created by carlt on 5/16/2019.
 */
public class Power {
    int AvailbleQuantity = 0;
    int MaxAvailbleQuantity = 0;

    public Power(int aq, int maq) {
        AvailbleQuantity = aq;
        MaxAvailbleQuantity = maq;
    }

    public void AddAvailbleQuantity(int i) {
        AvailbleQuantity = AvailbleQuantity + i;
    }

    public void AddAvailbleQuantity() {
        AvailbleQuantity++;
    }

    public void TakeAvailbleQuantity() {
        AvailbleQuantity--;
    }

    public void TakeAvailbleQuantity(int a) {
        AvailbleQuantity = AvailbleQuantity - a;
    }

    public int getAvailbleQuantity() {
        return AvailbleQuantity;
    }


}
