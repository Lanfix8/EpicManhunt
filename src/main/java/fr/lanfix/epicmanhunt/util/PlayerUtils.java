package fr.lanfix.epicmanhunt.util;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class PlayerUtils {

    public static void resetAdvancements(Player player) {
        Iterator<Advancement> advancementIterator = Bukkit.advancementIterator();
        while (advancementIterator.hasNext()) {
            AdvancementProgress advancementProgress = player.getAdvancementProgress(advancementIterator.next());
            for (String criteria : advancementProgress.getAwardedCriteria()) {
                advancementProgress.revokeCriteria(criteria);
            }
        }
    }

}
