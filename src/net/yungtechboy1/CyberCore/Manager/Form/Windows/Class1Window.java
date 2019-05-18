package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;

import static net.yungtechboy1.CyberCore.FormType.MainForm.Class_1;

public class Class1Window extends CyberFormSimple {
    public Class1Window(FormType.MainForm ttype, String title, String content) {
        super(ttype, title, content);
    }

    public Class1Window() {
        super(FormType.MainForm.Class_1, "Choose your Class Catagory!", "Visit Cybertechpp.com for more info on classes!",
                new ArrayList<ElementButton>() {{
                    add(new ElementButton("Offense"));
                    add(new ElementButton("Defense"));
                    add(new ElementButton("Minning"));
                    add(new ElementButton("Intelligence"));
                    add(new ElementButton("TEMP-GIVE SPAWNER"));
                }});
    }

    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple frs = getResponse();
        int k = frs.getClickedButtonId();
//        if (cp.LastSentSubMenu == FormType.SubMenu.MainMenu) {
            if (k == 0) {//Offense
                cp.showFormWindow(new Class1OffenseWindow());
//                cp.LastSentSubMenu = FormType.SubMenu.Offense;
            } else if (k == 1) {
                cp.showFormWindow(new Class1MinnerWindow());
//                cp.LastSentFormType = Class_1;
//                cp.LastSentSubMenu = FormType.SubMenu.Miner;
            } else if (k == 4) {
                //TEMP-GIVE SPAWNER
                Item i = Item.get(Item.MONSTER_SPAWNER, 0, 1);
                i.setCompoundTag(new CompoundTag() {{
                    putInt("Level", 1);
                    putInt("Type", 12);
                    putShort("MinSpawnDelay", 20 * 10);
                    putShort("MaxSpawnDelay", 20 * 10 + 10);
                }});
                cp.sendMessage("Gave ITem!");
                cp.getInventory().addItem(i);
            }
//            }
//        } else if (cp.LastSentSubMenu == FormType.SubMenu.Miner) {
//            switch (k) {
//                case 0:
////                            cp.SetPlayerClass();
//                    TNTSpecialist ts = new TNTSpecialist(plugin, cp);
//                    cp.SetPlayerClass(ts);
//                    plugin.ClassFactory.SaveClassToFile(cp);
//                    cp.sendMessage("Class Set!");
//                    break;//TNT-Specialist
//                case 1:
//                    break;//Knight
//                case 2:
//                    break;//Raider
//                case 3:
//                    break;//Theif
//            }
//        }
//        }else if (cp.LastSentSubMenu == FormType.SubMenu.Offense) {
//            switch (k) {
//                case 0:
//                    break;//Assassin
//                case 1:
//                    break;//Knight
//                case 2:
//                    break;//Raider
//                case 3:
//                    break;//Theif
//            }
//        }
    }
}
