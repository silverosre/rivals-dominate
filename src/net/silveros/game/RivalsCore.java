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

import java.awt.*;
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
    public RivalsMap currentMap = RivalsMap.TERRA;

    public static Vec3 LOBBY_SPAWN = new Vec3(0, 0, 0);

    public static int redTeamScoreDelay = 0;
    public static int blueTeamScoreDelay = 0;

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
    public Objective capturePointScore;
    public static PointState pointAState = PointState.UNDEFINED;
    public static PointState pointBState = PointState.UNDEFINED;
    public static PointState pointCState = PointState.UNDEFINED;
    public static PointState pointDState = PointState.UNDEFINED;
    public static PointState pointEState = PointState.UNDEFINED;
    public static final Material[] WOOLS = {Material.WHITE_WOOL, Material.RED_WOOL, Material.BLUE_WOOL};
    public static final Material[] GLASS = {Material.WHITE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.BLUE_STAINED_GLASS};

    //formula: seconds * 20
    public static final int ENERGY_BLOCK_TIMER = 60 * 20;
    public static final int RESTOCK_TIMER = 60 * 20;
    public static final int SCORE_BLOCK_TIMER = 90 * 20;
    public static final int POINT_CAPTURE_LIMIT = 5 * 20;
    public static final int RESPAWN_TIME = 10 * 20;

    public RivalsCore() {
        this.manager = Bukkit.getScoreboardManager();
        this.board  = this.manager.getNewScoreboard();

        this.initTeams();
        this.addViablePointBlocks();
        CapturePointLocations.init();

        this.energyBlockCooldown = this.board.registerNewObjective(RivalsTags.ENERGY_BLOCK_COOLDOWN, Criteria.DUMMY, "EnergyBlockCooldown");
        this.restockBlockCooldown = this.board.registerNewObjective(RivalsTags.RESTOCK_BLOCK_COOLDOWN, Criteria.DUMMY, "RestockBlockCooldown");
        this.scoreBlockCooldown = this.board.registerNewObjective(RivalsTags.SCORE_BLOCK_COOLDOWN, Criteria.DUMMY, "ScoreBlockCooldown");

        this.capturePointScore = this.board.registerNewObjective(RivalsTags.CAPTURE_POINT_SCORE, Criteria.DUMMY, "CapturePointScore");

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
            /*for (Points point : pointsMap.keySet()) {
                Vec3 vec = pointsMap.get(point);
                Location location = new Location(world, vec.posX, vec.posY, vec.posZ);
                this.placeCapturePoint(world, location, point);
            }*/

            Vec3 vecA = CapturePointLocations.TERRA_pointLocations.get(Points.POINT_A);
            Location pointA = new Location(world, vecA.posX, vecA.posY, vecA.posZ);
            this.placeCapturePoint(world, pointA, Points.POINT_A);

            Vec3 vecB = CapturePointLocations.TERRA_pointLocations.get(Points.POINT_B);
            Location pointB = new Location(world, vecB.posX, vecB.posY, vecB.posZ);
            this.placeCapturePoint(world, pointB, Points.POINT_B);

            Vec3 vecC = CapturePointLocations.TERRA_pointLocations.get(Points.POINT_C);
            Location pointC = new Location(world, vecC.posX, vecC.posY, vecC.posZ);
            this.placeCapturePoint(world, pointC, Points.POINT_C);

            Vec3 vecD = CapturePointLocations.TERRA_pointLocations.get(Points.POINT_D);
            Location pointD = new Location(world, vecD.posX, vecD.posY, vecD.posZ);
            this.placeCapturePoint(world, pointD, Points.POINT_D);

            Vec3 vecE = CapturePointLocations.TERRA_pointLocations.get(Points.POINT_E);
            Location pointE = new Location(world, vecE.posX, vecE.posY, vecE.posZ);
            this.placeCapturePoint(world, pointE, Points.POINT_E);

            pointAState = PointState.UNDEFINED;
            pointBState = PointState.UNDEFINED;
            pointCState = PointState.UNDEFINED;
            pointDState = PointState.UNDEFINED;
            pointEState = PointState.UNDEFINED;
            this.setBlocksForPoint(world, pointA, PointState.UNDEFINED);
            this.setBlocksForPoint(world, pointB, PointState.UNDEFINED);
            this.setBlocksForPoint(world, pointC, PointState.UNDEFINED);
            this.setBlocksForPoint(world, pointD, PointState.UNDEFINED);
            this.setBlocksForPoint(world, pointE, PointState.UNDEFINED);

            redTeamScoreDelay = blueTeamScoreDelay = 0;
        }
    }

    public void endDominateGame() {
        this.removeCapturePoints();

        //TODO:
        //teleport all players back to lobby
        //finish game

        World world = RivalsPlugin.getWorld();
        Location spawn = new Location(world, LOBBY_SPAWN.posX, LOBBY_SPAWN.posY, LOBBY_SPAWN.posZ);

        for (Player player : world.getPlayers()) {
            player.teleport(spawn);

            User user = Util.getUserFromId(player.getUniqueId());
            user.resetKit();
        }

        gameInProgress = false;
    }

    private void removeCapturePoints() {
        World world = RivalsPlugin.getWorld();

        if (world != null) {
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
        e.addScoreboardTag(RivalsTags.REMOVABLE);
        e.addScoreboardTag(RivalsTags.CAPTURE_POINT);
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

        double added = 0.025;
        double sum = bar.getProgress() + added;

        if (sum > 1) {
            sum = 1;

            this.endDominateGame();
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

    private void setPointScore(List<User> teammates, Points point, Marker entity, Score score, int rate, boolean blueTeam) {
        int captureRate = blueTeam ? rate : -rate;
        int cur = score.getScore();

        score.setScore(cur + captureRate);

        Util.print(point.toString() + ": " + score.getScore());

        if (Math.abs(score.getScore()) >= POINT_CAPTURE_LIMIT) {
            score.setScore(blueTeam ? POINT_CAPTURE_LIMIT : -POINT_CAPTURE_LIMIT);
            PointState state = blueTeam ? PointState.BLUE : PointState.RED;

            switch (point) {
                case POINT_A:
                    if (pointAState == PointState.UNDEFINED) {
                        pointAState = state;
                    }
                    break;
                case POINT_B:
                    if (pointBState == PointState.UNDEFINED) {
                        pointBState = state;
                    }
                    break;
                case POINT_C:
                    if (pointCState == PointState.UNDEFINED) {
                        pointCState = state;
                    }
                    break;
                case POINT_D:
                    if (pointDState == PointState.UNDEFINED) {
                        pointDState = state;
                    }
                    break;
                case POINT_E:
                    if (pointEState == PointState.UNDEFINED) {
                        pointEState = state;
                    }
                    break;
            }

            this.capturePoint(teammates, point, entity.getLocation(), blueTeam);
            this.checkForDomination(entity.getWorld(), state);
        } else {
            if (blueTeam && score.getScore() > 0) {
                switch (point) {
                    case POINT_A:
                        if (pointAState == PointState.RED) {
                            pointAState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_B:
                        if (pointBState == PointState.RED) {
                            pointBState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_C:
                        if (pointCState == PointState.RED) {
                            pointCState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_D:
                        if (pointDState == PointState.RED) {
                            pointDState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_E:
                        if (pointEState == PointState.RED) {
                            pointEState = PointState.UNDEFINED;
                        }
                        break;
                }

                this.setBlocksForPoint(entity.getWorld(), entity.getLocation(), this.getPointState(point));
            } else if (!blueTeam && score.getScore() < 0) {
                switch (point) {
                    case POINT_A:
                        if (pointAState == PointState.BLUE) {
                            pointAState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_B:
                        if (pointBState == PointState.BLUE) {
                            pointBState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_C:
                        if (pointCState == PointState.BLUE) {
                            pointCState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_D:
                        if (pointDState == PointState.BLUE) {
                            pointDState = PointState.UNDEFINED;
                        }
                        break;
                    case POINT_E:
                        if (pointEState == PointState.BLUE) {
                            pointEState = PointState.UNDEFINED;
                        }
                        break;
                }

                this.setBlocksForPoint(entity.getWorld(), entity.getLocation(), this.getPointState(point));
            }
        }
    }

    private void checkForDomination(World world, PointState state) {
        if (this.getPointsCaptured(state) == 5) {
            for (Player player : world.getPlayers()) {
                player.playSound(player, Sound.ENTITY_WITHER_SPAWN, 1.5f, 1);
                player.sendTitle("☠" + (state == PointState.BLUE ? BLUE + "BLUE" : RED + "RED") + " IS DOMINATING" + RESET + "☠", "", 5, 50, 5);
            }
        }
    }

    private void capturePoint(List<User> teammates, Points point, Location l, boolean blueTeam) {
        World world = l.getWorld();

        spawnFirework(l, blueTeam ? org.bukkit.Color.BLUE : org.bukkit.Color.RED);
        this.setBlocksForPoint(world, l, this.getPointState(point));

        for (Player player : world.getPlayers()) {
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f, 1);
            player.sendTitle((blueTeam ? BLUE + "BLUE" : RED + "RED") + " captured " + point.displayName, "", 5, 50, 5);
        }

        for (User user : teammates) {
            Player player = user.getPlayer();
            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
            world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
            user.addEnergy(1);
        }
    }

    private Points getPointFromMarker(Marker e) {
        return e.getScoreboardTags().contains(RivalsTags.POINT_A) ? Points.POINT_A : e.getScoreboardTags().contains(RivalsTags.POINT_B) ? Points.POINT_B : e.getScoreboardTags().contains(RivalsTags.POINT_C) ? Points.POINT_C : e.getScoreboardTags().contains(RivalsTags.POINT_D) ? Points.POINT_D : Points.POINT_E;
    }

    private PointState getPointState(Points point) {
        return point == Points.POINT_A ? pointAState : point == Points.POINT_B ? pointBState : point == Points.POINT_C ? pointCState : point == Points.POINT_D ? pointDState : pointEState;
    }

    private void setBlocksForPoint(World world, Location l, PointState state) {
        int ox = l.getBlockX();
        int y = l.getBlockY() - 1;
        int oz = l.getBlockZ();

        //boolean isStateDefined = state == PointState.BLUE || state == PointState.RED;

        for (int x=ox-3; x<ox+3; x++) {
            for (int z=oz-3; z<oz+3; z++) {
                Material block = world.getBlockAt(x, y, z).getType();
                boolean whiteWool = block == Material.WHITE_WOOL;
                boolean blueWool = block == Material.BLUE_WOOL;
                boolean redWool = block == Material.RED_WOOL;

                if (whiteWool || blueWool || redWool) {
                    world.setBlockData(x, y, z, WOOLS[state.ordinal()].createBlockData());
                }
            }
        }

        world.setBlockData(ox, y, oz, GLASS[state.ordinal()].createBlockData());
    }

    private int getPointsCaptured(PointState state) {
        int p = 0;

        if (pointAState == state) {
            p++;
        }

        if (pointBState == state) {
            p++;
        }

        if (pointCState == state) {
            p++;
        }

        if (pointDState == state) {
            p++;
        }

        if (pointEState == state) {
            p++;
        }

        return p;
    }

    public void tallyScore() {
        BossBar redBar = this.getTeamProgressBar(TEAM_RED);
        BossBar blueBar = this.getTeamProgressBar(TEAM_BLUE);

        int redPoints = this.getPointsCaptured(PointState.RED);
        int bluePoints = this.getPointsCaptured(PointState.BLUE);

        double added = 0.003;
        int redDelay = 40 - (redPoints * 4);
        int blueDelay = 40 - (bluePoints * 4);

        if (redPoints > 0) {
            if (redTeamScoreDelay < redDelay) {
                redTeamScoreDelay++;
            }

            if (redTeamScoreDelay >= redDelay) {
                redTeamScoreDelay = 0;

                double sum = redBar.getProgress() + added;
                if (sum > 1) {
                    sum = 1;

                    this.endDominateGame();
                }

                redBar.setProgress(sum);
            }
        } else {
            redTeamScoreDelay = 0;
        }

        if (bluePoints > 0) {
            if (blueTeamScoreDelay < blueDelay) {
                blueTeamScoreDelay++;
            }

            if (blueTeamScoreDelay >= blueDelay) {
                blueTeamScoreDelay = 0;

                double sum = blueBar.getProgress() + added;
                if (sum > 1) {
                    sum = 1;

                    this.endDominateGame();
                }

                blueBar.setProgress(sum);
            }
        } else {
            blueTeamScoreDelay = 0;
        }
    }

    public void onTick(World world) {
        this.tallyScore();

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
            //Capture points
            if (e.getScoreboardTags().contains(RivalsTags.CAPTURE_POINT)) {
                List<Player> playersList = new ArrayList<>();

                for (Player player : world.getPlayers()) {
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        playersList.add(player);
                    }
                }

                if (!playersList.isEmpty()) {
                    List<User> teammates = new ArrayList<>();
                    boolean enemyOnPoint = false;

                    for (Player player : playersList) {
                        if (e.getNearbyEntities(3, 1, 3).contains(player)) {
                            User user = Util.getUserFromId(player.getUniqueId());

                            for (Player other : world.getPlayers()) {
                                if (other.getGameMode() != GameMode.SPECTATOR) {
                                    User otherUser = Util.getUserFromId(other.getUniqueId());

                                    if (otherUser.playerId != user.playerId) {
                                        if (otherUser.getTeam() == user.getTeam()) {
                                            teammates.add(otherUser);
                                        } else {
                                            enemyOnPoint = true;
                                        }
                                    }
                                }
                            }

                            Score pointScore = this.capturePointScore.getScore(e.getUniqueId().toString());
                            int score = pointScore.getScore();
                            int rate = (1 + teammates.size());

                            if (!enemyOnPoint) {
                                boolean canBlueCapture = user.getTeam(this.TEAM_BLUE) && score < POINT_CAPTURE_LIMIT;
                                boolean canRedCapture = user.getTeam(this.TEAM_RED) && score > -POINT_CAPTURE_LIMIT;

                                if (canBlueCapture || canRedCapture) {
                                    Material block = world.getBlockAt(player.getLocation().subtract(0, 1, 0)).getType();
                                    if (viablePointBlocks.contains(block)) {
                                        //set capture score
                                        List<User> team = new ArrayList<>();
                                        team.add(user);
                                        team.addAll(teammates);

                                        this.setPointScore(team, this.getPointFromMarker(e), e, pointScore, rate, user.getTeam(this.TEAM_BLUE));

                                        //spawn particles
                                        Location l = e.getLocation();

                                        for (int i=0; i<10; i++) {
                                            int modX = Util.rand.nextInt(5);
                                            int modZ = Util.rand.nextInt(5);
                                            int x = l.getBlockX() + modX - 2;
                                            int z = l.getBlockZ() + modZ - 2;

                                            if (capturePointParticleBlocks[modX][modZ]) {
                                                world.spawnParticle(Particle.BLOCK_CRACK, x + 0.5, l.getY(), z + 0.5, 5, 0, 0, 0, 0, world.getBlockAt(x, l.getBlockY() - 1, z).getBlockData());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

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
