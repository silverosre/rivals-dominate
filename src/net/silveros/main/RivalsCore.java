package net.silveros.main;

import net.silveros.entity.RivalsTags;
import net.silveros.kits.KitArcher;
import net.silveros.utility.Util;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;

public class RivalsCore {
    public void onTick(World world) {



        //Archer "From Above" ability
        for (Entity e : world.getEntitiesByClass(Item.class)) {
            if (e.getScoreboardTags().contains(RivalsTags.FLARE_ENTITY)) {
                world.spawnParticle(Particle.SMOKE_LARGE, e.getLocation(), 5, 0, 0, 0, 0.1);
                if (e.getTicksLived() > 40) {
                    world.playSound(e.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f, 0.75f);

                    if (e.getTicksLived() > 60) {
                        for (int i = 0; i< KitArcher.FROMABOVE_ARROWS; i++) {
                            double x = Util.range(2);
                            double y = 10 + (0.25f * i);
                            double z = Util.range(2);
                            Location l = e.getLocation().clone().add(x, y, z);

                            Arrow arrow = (Arrow)world.spawnEntity(l, EntityType.ARROW);
                            arrow.setFireTicks(200);
                            arrow.setPierceLevel(4);
                            arrow.setShotFromCrossbow(true);
                            arrow.setDamage(8);
                        }

                        e.remove();
                    }
                }
            }
        }




    }
}
