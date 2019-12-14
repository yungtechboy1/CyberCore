package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;

public class CustomElementBlock extends Block {

    @Override
    public String getName() {
        return "Element 1";
    }

    @Override
    public int getId() {
        return 267;
    }
    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (this.getLevel().setBlock(this, this, true, true)) {
            System.out.println("PLACE SUCCESSFULL");
            return true;
        }
        System.out.println("PLACE NOOOOOOOOOOOOOOOOOOOOOOOTTTTTTTTTTTTTT SUCCESSFULL");
        return false;
    }
}
