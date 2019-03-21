/**
 * Tameable.java
 * 
 * Created on 09:59:43
 */
package net.yungtechboy1.CyberCore.entities;

import cn.nukkit.Player;

/**
 * Interface that is implemented in tameable entities
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public interface Tameable {

    public static final String NAMED_TAG_OWNER      = "Owner";

    public static final String NAMED_TAG_OWNER_UUID = "OwnerUUID";

    public static final String NAMED_TAG_SITTING    = "Sitting";

    Player getOwner();

    void setOwner(Player player);

}
