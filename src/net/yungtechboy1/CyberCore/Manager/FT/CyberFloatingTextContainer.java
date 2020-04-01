package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import cn.nukkit.utils.ConfigSection;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by carlt on 2/14/2019.
 */
public class CyberFloatingTextContainer {
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
//    public Level Lvl;
    public boolean _CE_Lock = false;
    public boolean _CE_Done = false;
    public boolean Vertical = false;
    public FloatingTextFactory FTF;

    @Override
    public String toString() {
        return "CyberFloatingTextContainer{" +
                "TYPE=" + TYPE +
                ", Syntax='" + Syntax + '\'' +
                ", PlayerUnique=" + PlayerUnique +
                ", Active=" + Active +
                ", Formated=" + Formated +
                ", UpdateTicks=" + UpdateTicks +
                ", LastUpdate=" + LastUpdate +
                ", Range=" + Range +
                ", EID=" + EID +
                ", Pos=" + Pos +
                ", Vertical=" + Vertical +
//                ", Lvl=" + Lvl +
                ", _CE_Lock=" + _CE_Lock +
                ", _CE_Done=" + _CE_Done +
                ", FTF=" + FTF +
                ", lastSyntax='" + lastSyntax + '\'' +
                ", uuid=" + uuid +
                ", metadata=" + metadata +
                '}';
    }

    public CyberFloatingTextContainer(FloatingTextFactory ftf, Position pos, String syntax) {
        FTF = ftf;
        generateEID();
        Syntax = syntax;
        Pos = pos;
//        Lvl = pos.level;
        long flags = (
                1L << Entity.DATA_FLAG_NO_AI
        );
        metadata = new EntityMetadata().putLong(Entity.DATA_FLAGS, flags)
                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
                .putFloat(Entity.DATA_SCALE, 0.01f) //zero causes problems on debug builds?
                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0.01f)
                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0.01f);


    }

    public Long generateEID() {
        this.EID = 1095216660480L + ThreadLocalRandom.current().nextLong(0L, 2147483647L);
        return EID;
    }

    public ConfigSection getSave() {
        //todo
        return new ConfigSection() {{
            put("Syntax", Syntax);
            put("PlayerUnique", PlayerUnique);
            put("UpdateTicks", UpdateTicks);
            put("LastUpdate", LastUpdate);
            put("Vertical", Vertical);
            put("X", Pos.getX());
            put("Y", Pos.getY());
            put("Z", Pos.getZ());
            if(Pos.getLevel() != null)put("Level", Pos.getLevel().getName());
        }};
    }

//    public class CFTCS extends CustomConfigSection {
//        public CFTCS() {
//        }
//
//        public CFTCS(ConfigSection c){
//            super(c);
//        }
//
//    }

    public String GetText(Player p) {
        return GetText(p,false);
    }
    public String GetText(Player p, boolean vertical) {
        return FTF.FormatText(Syntax, p, vertical);
    }

    //Generate Flaoting Text for following players
    public void HaldleSend(ArrayList<String> ap) {
//        System.out.println("HS");
//        ArrayList<DataPacket> tosend = new ArrayList<>();
        HashMap<String, ArrayList<DataPacket>> tosend;
//        sync(_CE_Lock)//TODO
        if (_CE_Lock || _CE_Done) return;
        _CE_Lock = true;
        for (String pn : ap) {
            Player p = Server.getInstance().getPlayerExact(pn);
            if (p == null) continue;
            for (DataPacket dp : encode(p)) p.dataPacket(dp);
        }
        _CE_Lock = false;
    }
private String lastSyntax = null;
    public boolean _CE_NeedToResend() {
        if(lastSyntax == null){
            lastSyntax = Syntax;
            return true;
        }
        if(lastSyntax == Syntax){
            return false;
        }
        lastSyntax = Syntax;
        return true;
    }
    public boolean _CE_Dynamic() {
        return Syntax.contains("{name}");
    }

    //Generate Flaoting Text for following players
    public void HaldleSendP(ArrayList<Player> ap) {
//        System.out.println("HS");
//        ArrayList<DataPacket> tosend = new ArrayList<>();
        HashMap<String, ArrayList<DataPacket>> tosend;
//        sync(_CE_Lock)//TODO
        if (_CE_Lock || _CE_Done) return;
        _CE_Lock = true;
        if (_CE_Dynamic() || _CE_NeedToResend()) {
            for (Player p : ap) {
//            Player p = Server.getInstance().getPlayerExact(pn);
                if (p == null) continue;
                for (DataPacket dp : encode(p)) p.dataPacket(dp);
            }
            _CE_Lock = false;
        }
    }
        protected UUID uuid = UUID.randomUUID();
        EntityMetadata metadata = new EntityMetadata();
        private void sendMetadata (Player p){
            if (Pos.getLevel() != null) {
                SetEntityDataPacket packet = new SetEntityDataPacket();
                packet.eid = EID;

//            if (!Strings.isNullOrEmpty(text)) {
//                metadata.putString(Entity.DATA_SCORE_TAG, text);
//            }
                packet.metadata = metadata;
                Pos.getLevel().addChunkPacket(Pos.getChunkX(), Pos.getChunkZ(), packet);
            }
        }

        public ArrayList<DataPacket> encode (Player p){
            ArrayList<DataPacket> packets = new ArrayList<>();

            if (Active) {
                RemoveEntityPacket pk = new RemoveEntityPacket();
                pk.eid = EID;

                packets.add(pk);
            }


            AddPlayerPacket pk = new AddPlayerPacket();
            pk.uuid = uuid;
            pk.username = "";
            pk.entityUniqueId = EID;
            pk.entityRuntimeId = EID;
            pk.x = (float) Pos.x;
            pk.y = (float) (Pos.y - 0.75);
            pk.z = (float) Pos.z;
            pk.speedX = 0;
            pk.speedY = 0;
            pk.speedZ = 0;
            pk.yaw = 0;
            pk.pitch = 0;
            if (!Strings.isNullOrEmpty(Syntax)) {
                metadata.putString(Entity.DATA_NAMETAG, GetText(p, Vertical));
            }
            pk.metadata = metadata;
            pk.item = Item.get(Item.AIR);
            packets.add(pk);


//        AddEntityPacket pk = new AddEntityPacket ();
//        pk.entityUniqueId = EID;
//        pk.entityRuntimeId = EID;
//        pk.type = 61; //
//        pk.x = (float) Pos.x;
//        pk.y = (float) (Pos.y - 1.62);
//        pk.z = (float) Pos.z;
//        pk.speedX = 0;
//        pk.speedY = 0;
//        pk.speedZ = 0;
//        pk.yaw = 0;
//        pk.pitch = 0;
//        long flags = 0;
//        flags |= 1 << Entity.DATA_FLAG_INVISIBLE;
//        flags |= 1 << Entity.DATA_FLAG_CAN_SHOW_NAMETAG;
//        flags |= 1 << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG;
//        flags |= 1 << Entity.DATA_FLAG_IMMOBILE;
//        pk.metadata = new EntityMetadata()
//                .putLong(Entity.DATA_FLAGS, flags)
//                .putString(Entity.DATA_NAMETAG, FTF.FormatText(Syntax, p))
//                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
//                .putByte(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1)
//                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0)
//                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0);
////                    .putByte(Entity.DATA_LEAD, 0);
//        pk.metadata = new EntityMetadata().putLong(Entity.DATA_FLAGS, (
//                (1L << Entity.DATA_FLAG_CAN_SHOW_NAMETAG) |
//                        (1L << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG) |
//                        (1L << Entity.DATA_FLAG_IMMOBILE) |
//                        (1L << Entity.DATA_FLAG_SILENT)
////                            (1L << Entity.DATA_FLAG_INVISIBLE)
//        ))
//                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0)
//                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0)
//                .putFloat(Entity.DATA_SCALE, 0f)
////            .putFloat(Entity.DATA_HEALTH, 100)
//                .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
//                .putByte(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1)
//                .putString(Entity.DATA_NAMETAG, FTF.FormatText(Syntax, p));
//        packets.add(pk);
            Active = true;
            return packets;

        }

        public DataPacket[] arryListToArray (ArrayList < DataPacket > packets) {
            return packets.stream().toArray(DataPacket[]::new);
        }

        public void OnUpdate ( int tick){
            LastUpdate = tick;
        }

        public void kill () {
            _CE_Done = true;
        }

    public String getKeyPos() {
            return Pos.getX()+"|"+Pos.getY()+"|"+Pos.getZ()+"|"+Pos.getLevel().getName()+"|";
    }

    public boolean isValid() {
            if(Pos == null)return false;
            return true;
    }
}