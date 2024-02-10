package net.silveros.game;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.kits.KitArcher;
import net.silveros.main.RivalsPlugin;
import net.silveros.utility.Color;
import net.silveros.utility.Util;
import net.silveros.utility.Vec3;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RivalsCore implements Color {
    public ScoreboardManager manager;
    public Scoreboard board;

    public Team TEAM_RED, TEAM_BLUE, TEAM_SPECTATOR;
    public Objective energyBlockCooldown, restockBlockCooldown, scoreBlockCooldown;

    public BossBar redPointsBar, bluePointsBar;
    public Map<Points, Marker> capturePoints = new HashMap<>();
    public RivalsMap currentMap = RivalsMap.GRASSLANDS;

    public static Gamemode gamemode = Gamemode.DOMINATE;
    public static boolean gameInProgress = false;

    public static boolean[][] capturePointParticleBlocks = {
            {false, true, true, true, false},
            {true, true, true, true, true},
            {true, true, false, true, true},
            {true, true, true, true, true},
            {false, true, true, true, false},
    };
    public static List<Material> viablePointBlocks = new ArrayList<>(6);

    //formula: seconds * 20
    public static final int ENERGY_BLOCK_TIMER = 60 * 20;
    public static final int RESTOCK_TIMER = 60 * 20;
    public static final int SCORE_BLOCK_TIMER = 90 * 20;

    public RivalsCore() {
        this.manager = Bukkit.getScoreboardManager();
        this.board  = this.manager.getNewScoreboard();

        this.initTeams();
        this.addViablePointBlocks();
        CapturePointLocations.init();

        this.energyBlockCooldown = this.board.registerNewObjective(RivalsTags.ENERGY_BLOCK_COOLDOWN, Criteria.DUMMY, "EnergyBlockCooldown");
        this.restockBlockCooldown = this.board.registerNewObjective(RivalsTags.RESTOCK_BLOCK_COOLDOWN, Criteria.DUMMY, "RestockBlockCooldown");
        this.scoreBlockCooldown = this.board.registerNewObjective(RivalsTags.SCORE_BLOCK_COOLDOWN, Criteria.DUMMY, "ScoreBlockCooldown");

        this.redPointsBar = Bukkit.createBossBar(WHITE + "Red Team's Points", BarColor.RED, BarStyle.SOLID);
        this.bluePointsBar = Bukkit.createBossBar(WHITE + "Blue Team's Points", BarColor.BLUE, BarStyle.SOLID);

        //this.startDominateGame(RivalsMap.GRASSLANDS);
    }

    public void startDominateGame(RivalsMap map) {
        gamemode = Gamemode.DOMINATE;
        this.currentMap = map;
        this.setupCapturePoints(this.currentMap);
        this.toggleDominateProgressBars(true);

        this.redPointsBar.setProgress(0);
        this.bluePointsBar.setProgress(0);

        for (User user : RivalsPlugin.players) {
            this.addPlayerToBossbars(user.getPlayer());
        }

        gameInProgress = true;
    }

    private void setupCapturePoints(RivalsMap map) {
        World world = RivalsPlugin.getWorld();

        if (world != null) {
            this.capturePoints.clear();

            /*for (Points point : pointsMap.keySet()) {
                Vec3 vec = pointsMap.get(point);
                Location location = new Location(world, vec.posX, vec.posY, vec.posZ);
                this.placeCapturePoint(world, location, point);
            }*/

            Vec3 vecA = CapturePointLocations.GRASSLANDS_pointLocations.get(Points.POINT_A);
            Location pointA = new Location(world, vecA.posX, vecA.posY, vecA.posZ);
            this.placeCapturePoint(world, pointA, Points.POINT_A);

            Vec3 vecB = CapturePointLocations.GRASSLANDS_pointLocations.get(Points.POINT_B);
            Location pointB = new Location(world, vecB.posX, vecB.posY, vecB.posZ);
            this.placeCapturePoint(world, pointB, Points.POINT_B);

            Vec3 vecC = CapturePointLocations.GRASSLANDS_pointLocations.get(Points.POINT_C);
            Location pointC = new Location(world, vecC.posX, vecC.posY, vecC.posZ);
            this.placeCapturePoint(world, pointC, Points.POINT_C);

            Vec3 vecD = CapturePointLocations.GRASSLANDS_pointLocations.get(Points.POINT_D);
            Location pointD = new Location(world, vecD.posX, vecD.posY, vecD.posZ);
            this.placeCapturePoint(world, pointD, Points.POINT_D);

            Vec3 vecE = CapturePointLocations.GRASSLANDS_pointLocations.get(Points.POINT_E);
            Location pointE = new Location(world, vecE.posX, vecE.posY, vecE.posZ);
            this.placeCapturePoint(world, pointE, Points.POINT_E);
        }
    }

    public void endDominateGame() {
        this.removeCapturePoints();

        //TODO:
        //teleport all playyers back to lobby
        //finish game

        gameInProgress = false;
    }

    private void removeCapturePoints() {
        World world = RivalsPlugin.getWorld();

        if (world != null) {
            //this.capturePoints.clear();

            for (Marker e : world.getEntitiesByClass(Marker.class)) {
                if (e.getScoreboardTags().contains(RivalsTags.CAPTURE_POINT)) {
                    e.remove();
                }
            }
        }
    }

    private void placeCapturePoint(World world, Location l, Points point) {
        Marker e = (Marker)world.spawnEntity(l, EntityType.MARKER);
        e.addScoreboardTag(point.tag);
        e.addScoreboardTag(RivalsTags.CAPTURE_POINT);

        //this.capturePoints.put(point, e);
    }

    public void addPlayerToBossbars(Player player) {
        this.redPointsBar.addPlayer(player);
        this.bluePointsBar.addPlayer(player);
    }

    public void toggleDominateProgressBars(boolean state) {
        this.redPointsBar.setVisible(state);
        this.bluePointsBar.setVisible(state);
    }

    public BossBar getTeamProgressBar(Team team) {
        return team.getName().equals("rivals.red") ? this.redPointsBar : this.bluePointsBar;
    }

    public void addScoreBlockPoints(Team team) {
        BossBar bar = this.getTeamProgressBar(team);

        double added = 0.05;
        double sum = bar.getProgress() + added;

        if (sum > 1) {
            sum = 1;
        }

        bar.setProgress(sum);
    }

    private void initTeams() {
        this.TEAM_RED = this.board.registerNewTeam("rivals.red");
        this.TEAM_BLUE = this.board.registerNewTeam("rivals.blue");
        this.TEAM_SPECTATOR = this.board.registerNewTeam("rivals.spectator");

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

    private void addViablePointBlocks() {
        viablePointBlocks.add(Material.WHITE_WOOL);
        viablePointBlocks.add(Material.BLUE_WOOL);
        viablePointBlocks.add(Material.RED_WOOL);
        viablePointBlocks.add(Material.WHITE_STAINED_GLASS);
        viablePointBlocks.add(Material.BLUE_STAINED_GLASS);
        viablePointBlocks.add(Material.RED_STAINED_GLASS);
    }

    public void onTick(World world) {
        //Energy block & restock cooldown text displays
        for (TextDisplay e : world.getEntitiesByClass(TextDisplay.class)) {
            if (e.getScoreboardTags().contains(RivalsTags.ENERGY_BLOCK_COOLDOWN_TEXT_ENTITY)) {
                Score cooldown = this.energyBlockCooldown.getScore(e.getUniqueId().toString());
                e.setText(AQUA + ((cooldown.getScore()/20) + 1)); // add 1 to make the final second look like 1 instead of 0

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    world.playSound(e.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.75f, 1);
                    e.remove();
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.RESTOCK_BLOCK_COOLDOWN_TEXT_ENTITY)) {
                Score cooldown = this.restockBlockCooldown.getScore(e.getUniqueId().toString());
                e.setText(YELLOW + ((cooldown.getScore()/20) + 1)); // add 1 to make the final second look like 1 instead of 0

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    world.playSound(e.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.75f, 1);
                    e.remove();
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.SCORE_BLOCK_COOLDOWN_TEXT_ENTITY)) {
                Score cooldown = this.scoreBlockCooldown.getScore(e.getUniqueId().toString());
                e.setText(GREEN + ((cooldown.getScore()/20) + 1)); // add 1 to make the final second look like 1 instead of 0

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    world.playSound(e.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.75f, 1);
                    e.remove();
                }
            }
        }

        for (Marker e : world.getEntitiesByClass(Marker.class)) {
            //Energy block & restock block cooldowns
            if (e.getScoreboardTags().contains(RivalsTags.ENERGY_BLOCK_ENTITY)) {
                Score cooldown = this.energyBlockCooldown.getScore(e.getUniqueId().toString());

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    Location l = e.getLocation().subtract(0, 1, 0);
                    if (world.getBlockAt(l).getType() == Material.IRON_BLOCK) {
                        world.setBlockData(l, Material.DIAMOND_BLOCK.createBlockData());
                    }
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.RESTOCK_BLOCK_ENTITY)) {
                Score cooldown = this.restockBlockCooldown.getScore(e.getUniqueId().toString());

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    Location l = e.getLocation().subtract(0, 1, 0);
                    if (world.getBlockAt(l).getType() == Material.IRON_BLOCK) {
                        world.setBlockData(l, Material.GOLD_BLOCK.createBlockData());
                    }
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.SCORE_BLOCK_ENTITY)) {
                Score cooldown = this.scoreBlockCooldown.getScore(e.getUniqueId().toString());

                if (cooldown.getScore() > 0) {
                    cooldown.setScore(cooldown.getScore() - 1);
                } else {
                    Location l = e.getLocation().subtract(0, 1, 0);
                    if (world.getBlockAt(l).getType() == Material.IRON_BLOCK) {
                        world.setBlockData(l, Material.EMERALD_BLOCK.createBlockData());
                    }
                }
            }
        }

        for (Item e : world.getEntitiesByClass(Item.class)) {
            //Archer "From Above" ability
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

    public static void spawnFirework(Location location, org.bukkit.Color mainColor) {
        Firework firework = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect.Type burst = FireworkEffect.Type.BURST;
        //FireworkEffect.Type star = FireworkEffect.Type.STAR;
        org.bukkit.Color b = org.bukkit.Color.WHITE;

        FireworkEffect effect = FireworkEffect.builder().with(burst).withColor(mainColor).withColor(b).build();
        fireworkMeta.addEffect(effect);

        fireworkMeta.setPower(0);
        firework.setFireworkMeta(fireworkMeta);
        firework.detonate();
    }
}
