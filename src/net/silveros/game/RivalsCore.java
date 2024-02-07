package net.silveros.game;

import net.silveros.entity.RivalsTags;
import net.silveros.kits.KitArcher;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class RivalsCore implements Color {
    public ScoreboardManager manager;
    public Scoreboard board;

    public Team TEAM_RED, TEAM_BLUE, TEAM_SPECTATOR;

    public RivalsCore() {
        this.manager = Bukkit.getScoreboardManager();
        this.board  = this.manager.getNewScoreboard();

        this.TEAM_RED = this.board.registerNewTeam("rivals.red");
        this.TEAM_BLUE = this.board.registerNewTeam("rivals.blue");
        this.TEAM_SPECTATOR = this.board.registerNewTeam("rivals.spectator");

        this.initTeams();
    }

    private void initTeams() {
        this.TEAM_RED.setPrefix(GRAY + "[" + RED + "Red" + GRAY + "]" + RED);
        this.TEAM_BLUE.setPrefix(GRAY + "[" + BLUE + "Blue" + GRAY + "]" + BLUE);
        this.TEAM_SPECTATOR.setPrefix(GRAY + "[" + WHITE + "Spectator" + GRAY + "]" + GRAY);
        this.TEAM_RED.setDisplayName(RED + "Red Team");
        this.TEAM_BLUE.setDisplayName(BLUE + "Blue Team");
        this.TEAM_SPECTATOR.setDisplayName(GRAY + "Spectators");
        this.TEAM_RED.setColor(ChatColor.RED);
        this.TEAM_BLUE.setColor(ChatColor.BLUE);
        this.TEAM_SPECTATOR.setColor(ChatColor.GRAY);

        this.TEAM_RED.setAllowFriendlyFire(false);
        this.TEAM_BLUE.setAllowFriendlyFire(false);
        this.TEAM_SPECTATOR.setAllowFriendlyFire(false);
    }

    public static void addEntryToTeam(Team team, Entity entity) {
        String entry = entity instanceof Player ? entity.getName() : entity.getUniqueId().toString();
        team.addEntry(entry);
    }

    public static void removeEntryFromTeam(Team team, Entity entity) {
        String entry = entity instanceof Player ? entity.getName() : entity.getUniqueId().toString();
        team.removeEntry(entry);
    }

    public static boolean isEntityOnTeam(Team team, Entity entity) {
        String entry = entity instanceof Player ? entity.getName() : entity.getUniqueId().toString();
        return team.hasEntry(entry);
    }

    public static boolean matchingTeams(Team team, Entity a, Entity b) {
        String entryA = a instanceof Player ? a.getName() : a.getUniqueId().toString();
        String entryB = b instanceof Player ? b.getName() : b.getUniqueId().toString();

        return team.hasEntry(entryA) && team.hasEntry(entryB);
    }

    public void onTick(World world) {



        //Archer "From Above" ability
        for (Entity e : world.getEntitiesByClass(Item.class)) {
            if (e.getScoreboardTags().contains(RivalsTags.FLARE_ENTITY)) {
                world.spawnParticle(Particle.SMOKE_LARGE, e.getLocation(), 5, 0, 0, 0, 0.1);
                if (e.getTicksLived() > 40) {
                    world.playSound(e.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.75f, 0.75f);

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
