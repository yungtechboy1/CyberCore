package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.utils.Config;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/28/2017.
 */
public class AuctionFactoryPE  extends AuctionFactory {
    public AuctionFactoryPE(CyberCoreMain CCM) {
        super(CCM);
    }

    /**
     * Settings:
     * Key: {
     * id:
     * meta:
     * count:
     * namedtag:
     * cost:
     * soldby:
     * }
     */
    Config Settings;

}
