package net.yungtechboy1.CyberCore.Manager;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossBarAPI implements Listener {

    private List<String> texts;

    private final Map<Player, Long> bb = new HashMap<>();
    private final Map<Player, Long> bbMarquee = new HashMap<>();
    private int index = 0;
    private Map<Long,String> marqueeText = new HashMap<>();
    private Map<Long, Integer> marqueeIndex = new HashMap<>();

    private CyberCoreMain plugin;

    public BossBarAPI(CyberCoreMain plugin){
        this.plugin = plugin;
        texts = plugin.MainConfig.getStringList("BossBar-Messages");
        for (int i = 0; i < this.texts.size(); i++) {
            this.texts.set(i, plugin.colorize(this.texts.get(i)));
        }
        new NukkitRunnable() {
            @Override
            public void run() {
                int percentage = 100;
                for (Player p : plugin.getServer().getOnlinePlayers().values()) {
                    Map<Long, DummyBossBar> dbb = p.getDummyBossBars();
                    long idMarquee = bbMarquee.containsKey(p) ? bbMarquee.get(p) : -1;
                    if(idMarquee != -1) {
                        if (dbb.containsKey(idMarquee)) {
                            String text = "";
                            String layout = plugin.colorize(texts.get(index), p);
                            int mIndex = marqueeIndex.get(idMarquee);
                            DummyBossBar bar = getBossBar(p, true);
                            if (mIndex == 0) {
                                marqueeText.replace(idMarquee, "                          ");
                                mIndex = 1;
                            }
                            if (mIndex <= layout.length()) {
                                marqueeText.replace(idMarquee, marqueeText.get(idMarquee).replaceFirst(" ", ""));
                                text = layout.substring(0, mIndex);
                                if (text.contains("§") && text.lastIndexOf("§") == mIndex - 1) {
                                    text = layout.substring(0, ++mIndex);
                                    bar.setText(marqueeText.get(idMarquee) + text);
                                    bar.setLength(percentage);
                                    marqueeIndex.replace(idMarquee, ++mIndex);
                                    continue;
                                }
                            }
                            if (mIndex > layout.length()) {
                                if (mIndex >= layout.length() * 2) {
                                    bar.setText(" ");
                                    bar.setLength(percentage);
                                    marqueeIndex.replace(idMarquee, 0);
                                    if(index != texts.size() - 1) {
                                        index = ++index;
                                    } else index = 0;
                                    continue;
                                }
                                text = layout;
                                text = text + " ";
                                marqueeText.replace(idMarquee, text);
                                bar.setText(marqueeText.get(idMarquee));
                                bar.setLength(percentage);
                                marqueeIndex.replace(idMarquee,++mIndex);
                                continue;
                            }
                            bar.setText(marqueeText.get(idMarquee) + text);
                            bar.setLength(percentage);
                            marqueeIndex.replace(idMarquee, ++mIndex);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 5);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        createBossBar(p, "WELCOME", true);
    }

    public long createBossBar(Player p, String text, boolean marquee) {
        DummyBossBar bossBar = (new DummyBossBar.Builder(p)).text(text).length((float)100).build();
        Long id = p.createBossBar(bossBar);
        if (marquee) {
            bbMarquee.put(p, id);
            marqueeIndex.put(id, 0);
            marqueeText.put(id, text);
        } else {
            bb.put(p, id);
        }
        return p.createBossBar(bossBar);
    }

    public DummyBossBar getBossBar(Player p, boolean marquee) {
        return marquee ? p.getDummyBossBar(bbMarquee.get(p)) : p.getDummyBossBar(bb.get(p));
    }

    private void logLoadException(String text) {
        plugin.log("An error occurred while reading the configuration '" + text + "'. Use the default value.");
    }
}
