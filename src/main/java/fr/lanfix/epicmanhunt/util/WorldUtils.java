package fr.lanfix.epicmanhunt.util;

import org.bukkit.GameRule;
import org.bukkit.World;

public class WorldUtils {

    @SuppressWarnings("unchecked")
    public static boolean setGameRuleIfExists(World world, boolean value, String... candidates) {
        for (String name : candidates) {
            try {
                GameRule<Boolean> gamerule = (GameRule<Boolean>) GameRule.class.getField(name).get(null);
                world.setGameRule(gamerule, value);
                return true;
            } catch (NoSuchFieldException | IllegalAccessException ignored) {

            }
        }
        return false;
    }

}
