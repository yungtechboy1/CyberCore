package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.ConfigSection;
import javafx.geometry.Pos;
import net.yungtechboy1.CyberCore.FloatingTextMain;

import javax.xml.crypto.Data;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by carlt on 2/14/2019.
 */
public class FloatingTextContainer {
    public FloatingTextType TYPE = FloatingTextType.FT_Standard;
    public String Syntax;
    public Boolean PlayerUnique = false;
    public Boolean Active = false;
    public Boolean Formated = false;
    public int UpdateTicks = 20;
    public int LastUpdate = 0;
    public int Range = 64;
    public long EID = -1;
    public Position Pos;
    public Level Lvl;
    public boolean _CE_Lock = false;
    public FloatingTextFactory FTF;

    public FloatingTextContainer(FloatingTextFactory ftf, Position pos, String syntax) {
        FTF = ftf;
        generateEID();


    }

    public Long generateEID() {
        this.EID = 1095216660480L + ThreadLocalRandom.current().nextLong(0L, 2147483647L);
        return EID;
    }

    public ConfigSection GetSave() {
        //todo
        return new ConfigSection() {{
            put("Syntax", Syntax);
            put("PlayerUnique", PlayerUnique);
            put("UpdateTicks", UpdateTicks);
            put("LastUpdate", LastUpdate);
        }};
    }

    public String GetText(Player p) {
        return FTF.FormatText(Syntax, p);
    }

    //Generate Flaoting Text for following players
    public void HaldleSend(ArrayList<String> ap) {
//        ArrayList<DataPacket> tosend = new ArrayList<>();
        HashMap<String,ArrayList<DataPacket>> tosend;
//        sync(_CE_Lock)//TODO
        if (_CE_Lock) return;
        _CE_Lock = true;
        for (String pn : ap) {
            Player p = Server.getInstance().getPlayerExact(pn);
            if (p == null) continue;
            for(DataPacket dp : encode (p))p.dataPacket(dp);
        }
        _CE_Lock = false;
    }

    public ArrayList<DataPacket> encode(Player p) {
        ArrayList<DataPacket> packets = new ArrayList<>();

        if (Active) {
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = EID;

            packets.add(pk);
        }

        AddPlayerPacket pk = new AddPlayerPacket();
        pk.uuid = UUID.randomUUID();
        pk.username = "";
        pk.entityUniqueId = EID;
        pk.entityRuntimeId = EID;
        pk.x = (float) Pos.x;
        pk.y = (float) (Pos.y - 1.62);
        pk.z = (float) Pos.z;
        pk.speedX = 0;
        pk.speedY = 0;
        pk.speedZ = 0;
        pk.yaw = 0;
        pk.pitch = 0;
        long flags = 0;
        flags |= 1 << Entity.DATA_FLAG_INVISIBLE;
        flags |= 1 << Entity.DATA_FLAG_CAN_SHOW_NAMETAG;
        flags |= 1 << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG;
        flags |= 1 << Entity.DATA_FLAG_IMMOBILE;
        pk.metadata = new EntityMetadata()
                .putLong(Entity.DATA_FLAGS, flags)
                .putString(Entity.DATA_NAMETAG, FTF.FormatText(Syntax, p))
                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1);
//                    .putByte(Entity.DATA_LEAD, 0);
        pk.item = Item.get(Item.AIR);
        packets.add(pk);
        Active = true;
        return packets;

    }

    public DataPacket[] arryListToArray(ArrayList<DataPacket> packets) {
        return packets.stream().toArray(DataPacket[]::new);
    }

    public void OnUpdate(int tick){

    }

}