package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerMapInfoRequestEvent;
import cn.nukkit.item.ItemMap;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ClientboundMapItemDataPacket;
import cn.nukkit.utils.MainLogger;
import net.yungtechboy1.CyberCore.Manager.PositionImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by carlt on 5/16/2019.
 */
public class CustomItemMap extends ItemMap {
int MapScale = 1;
ArrayList<ClientboundMapItemDataPacket.MapDecorator> md = new ArrayList<>();


    public void setImage(PositionImage file) {
        super.setImage(file.CreateImg());

    }

    @Override
    public void sendImage(Player p) {
        BufferedImage image = this.loadImageFromNBT();
        ClientboundMapItemDataPacket pk = new ClientboundMapItemDataPacket();
        pk.mapId = this.getMapId();
        pk.update = 2;
        pk.scale = 0;
        pk.width = image.getWidth();
        pk.height = image.getHeight();
        pk.offsetX = 0;
        pk.offsetZ = 0;
        pk.image = image;
        if(md.size() != 0)pk.decorators = md.toArray(new ClientboundMapItemDataPacket.MapDecorator[md.size()-1]);
        p.dataPacket(pk);
    }

    @Override
    public boolean canBeActivated() {
        return super.canBeActivated();
    }

    private int kk = 0;

    public void AddDecoration(int decorationid,int x, int z,double rot){
        kk++;
        ClientboundMapItemDataPacket.MapDecorator m;
        m = new ClientboundMapItemDataPacket().new MapDecorator();
        m.color = new Color(250,0,0);
        m.icon = (byte)decorationid;
        m.offsetX = (byte)x;
        m.offsetZ = (byte)z;
        m.rotation = (byte)rot;
        m.label = "TESTTT"+kk;
        md.add(m);
    }

    @Override
    public void setImage(BufferedImage img) {
        try {
//            BufferedImage image = img;
//            if(img.getHeight() != 128 || img.getWidth() != 128) {
//                image = new BufferedImage(128, 128, img.getType());
//                Graphics2D g = image.createGraphics();
//                g.drawImage(img, 0, 0, 128, 128, (ImageObserver)null);
//                g.dispose();
//            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            this.getNamedTag().putByteArray("Colors", baos.toByteArray());
        } catch (IOException var4) {
            MainLogger.getLogger().logException(var4);
        }

    }

}
