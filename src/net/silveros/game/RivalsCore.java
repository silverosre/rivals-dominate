package net.silveros.game;

import net.silveros.entity.RivalsTags;
import net.silveros.entity.User;
import net.silveros.kits.Kit;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.*;

public class RivalsCore implements Color {
    public ScoreboardManager manager;
    public Scoreboard board;

    public Team TEAM_RED, TEAM_BLUE, TEAM_SPECTATOR;
    public Objective energyBlockCooldown, restockBlockCooldown, scoreBlockCooldown;

    public Objective displayBoard;

    public BossBar redPointsBar, bluePointsBar;
    public RivalsMap currentMap = RivalsMap.TERRA;

    public static Vec3 LOBBY_SPAWN = new Vec3(101, -40, 136);
    private static final float LOBBY_SPAWN_YAW = 180;

    public static int redTeamScoreDelay = 0;
    public static int blueTeamScoreDelay = 0;

    public static Gamemode gamemode = Gamemode.DOMINATE;
    public static boolean gameInProgress = false;

    public static Set<Firework> fireworks;

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
    private static final int MAX_ARROW_LIFE = 5 * 20;

    //Misc
    private static final int DEFAULT_TIME = 6000;
    public boolean gameInit = false;
    private static int GAME_COUNTDOWN = 20 * 20;
    private static int DISPLAY_COUNTDOWN = 5;
    private static final float radius = 2f;
    private static float angle = 0f;
    public double randomPosition (double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public RivalsCore() {
        this.manager = Bukkit.getScoreboardManager();
        this.board  = this.manager.getNewScoreboard();

        this.initTeams();
        this.addViablePointBlocks();
        CapturePointLocations.init();

        fireworks = new HashSet<>();

        this.energyBlockCooldown = this.board.registerNewObjective(RivalsTags.ENERGY_BLOCK_COOLDOWN, Criteria.DUMMY, "EnergyBlockCooldown");
        this.restockBlockCooldown = this.board.registerNewObjective(RivalsTags.RESTOCK_BLOCK_COOLDOWN, Criteria.DUMMY, "RestockBlockCooldown");
        this.scoreBlockCooldown = this.board.registerNewObjective(RivalsTags.SCORE_BLOCK_COOLDOWN, Criteria.DUMMY, "ScoreBlockCooldown");

        this.capturePointScore = this.board.registerNewObjective(RivalsTags.CAPTURE_POINT_SCORE, Criteria.DUMMY, "CapturePointScore");

        this.displayBoard = this.board.registerNewObjective(RivalsTags.DISPLAY_BOARD, Criteria.DUMMY, RED + BOLD + "RIVALS: " + RESET + BLUE + "Dominate");
        //this.displayBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.setupSideBar(false);

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

        this.setupSideBar(true);

        for (User user : RivalsPlugin.players) {
            this.addPlayerToBossbars(user.getPlayer());

            user.getPlayer().setScoreboard(this.board);
        }

        gameInProgress = true;
    }

    private void setupCapturePoints(RivalsMap map) {
        World world = RivalsPlugin.getWorld();

        if (world != null) {
            Map<Points, Vec3> points = CapturePointLocations.getPointLocations(this.currentMap);

            Vec3 vecA = points.get(Points.POINT_A);
            Location pointA = new Location(world, vecA.posX, vecA.posY, vecA.posZ);
            this.placeCapturePoint(world, pointA, Points.POINT_A);

            Vec3 vecB = points.get(Points.POINT_B);
            Location pointB = new Location(world, vecB.posX, vecB.posY, vecB.posZ);
            this.placeCapturePoint(world, pointB, Points.POINT_B);

            Vec3 vecC = points.get(Points.POINT_C);
            Location pointC = new Location(world, vecC.posX, vecC.posY, vecC.posZ);
            this.placeCapturePoint(world, pointC, Points.POINT_C);

            Vec3 vecD = points.get(Points.POINT_D);
            Location pointD = new Location(world, vecD.posX, vecD.posY, vecD.posZ);
            this.placeCapturePoint(world, pointD, Points.POINT_D);

            Vec3 vecE = points.get(Points.POINT_E);
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
            world.setTime(map.time);
        }
    }

    public void endDominateGame(Team team) {
        this.removeCapturePoints();

        this.redPointsBar.setProgress(0);
        this.bluePointsBar.setProgress(0);
        this.toggleDominateProgressBars(false);

        World world = RivalsPlugin.getWorld();
        Location spawn = new Location(world, LOBBY_SPAWN.posX, LOBBY_SPAWN.posY, LOBBY_SPAWN.posZ);
        spawn.setYaw(LOBBY_SPAWN_YAW);

        Scoreboard newBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        for (Player player : world.getPlayers()) {
            player.teleport(spawn);
            player.setScoreboard(newBoard);// spigot jank

            User user = Util.getUserFromId(player.getUniqueId());
            user.resetKit();

            player.playSound(player, Sound.ENTITY_WITHER_DEATH, 0.75f, 1);
            player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 1.5f, 1);
            player.sendTitle((team.equals(this.TEAM_BLUE) ? BLUE + "BLUE" : RED + "RED") + " won the game!", "", 5, 50, 5);

            player.playSound(player, user.getTeam(team) ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
        }

        //reset time
        world.setTime(DEFAULT_TIME);

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

            this.endDominateGame(team);
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

        this.setupSideBar(true);
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
            if (player != null) {
                world.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                world.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                user.addEnergy(1);
            }
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
        if (!gameInProgress) {
            return;
        }

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

                    this.endDominateGame(this.TEAM_RED);
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

                    this.endDominateGame(this.TEAM_BLUE);
                }

                blueBar.setProgress(sum);
            }
        } else {
            blueTeamScoreDelay = 0;
        }
    }

    public void onTick(World world) {
        this.tallyScore();

        //Game Initialization and Map randomizer
        if (this.gameInit) {
            int l = RivalsMap.values().length;
            RivalsMap randomMap = RivalsMap.values()[Util.rand.nextInt(l)];
            RivalsMap selectedMap = null;

            //was gonna do titles for this but it was givin me issues trying to cast to a player from the core --roasty
            if (GAME_COUNTDOWN > 0) {
                if (GAME_COUNTDOWN == 20 * 20) {
                    Bukkit.broadcastMessage(GREEN + BOLD + "Game starting in 20 seconds!");
                    Bukkit.broadcastMessage(GREEN + BOLD + "Selected map: " + WHITE + randomMap.displayName);
                }

                if (GAME_COUNTDOWN == 15 * 20) {
                    Bukkit.broadcastMessage(GREEN + "Game starting in 15 seconds!");
                }

                if (GAME_COUNTDOWN == 10 * 20) {
                    Bukkit.broadcastMessage(GREEN + "Game starting in 10 seconds!");
                }

                if (GAME_COUNTDOWN < 100) {
                    if (GAME_COUNTDOWN % 20 == 0) {
                        Bukkit.broadcastMessage(GREEN + "Countdown: " + WHITE + DISPLAY_COUNTDOWN);
                        DISPLAY_COUNTDOWN--;
                    }
                }

                GAME_COUNTDOWN--;
            } else {
                GAME_COUNTDOWN = 20 * 20;
                DISPLAY_COUNTDOWN = 5;
                if (!gameInProgress) {
                    for (RivalsMap map : RivalsMap.values()) {
                        if (randomMap == map) {
                            selectedMap = map;
                        }
                    }

                    this.startDominateGame(selectedMap);

                    for (Player p : world.getPlayers()) {
                        User user = Util.getUserFromId(p.getUniqueId());
                        user.resetKit();

                        p.setHealth(0);
                    }
                } else {
                    Bukkit.broadcastMessage(RED + "There is a game currently running! Startup cancelled.");
                }

                this.gameInit = false;
            }
        }

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

        //Score block, energy block, & restock block item displays
        for (ItemDisplay e : world.getEntitiesByClass(ItemDisplay.class)) {
            if (e.getScoreboardTags().contains(RivalsTags.ITEM_DISPLAY)) {
                boolean isBlockUsed = world.getBlockAt(e.getLocation().subtract(0, 2, 0)).getType() == Material.IRON_BLOCK;
                Material block = Material.DIAMOND_BLOCK;
                Particle particle = isBlockUsed ? Particle.BLOCK_CRACK : Particle.REDSTONE;
                org.bukkit.Color color = org.bukkit.Color.AQUA;

                int count = isBlockUsed ? 5 : 10;
                float xd = isBlockUsed ? 0 : 0.25f;
                float yd = 0.05f;
                float subs = 1.375f;

                int ticks = e.getTicksLived() * 4;
                Location l = e.getLocation();
                Location prl = l.subtract(0, subs, 0);

                e.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.GROUND);
                e.setRotation(Util.lerp_f(Util.getTickDelta(), ticks, ticks + 1), 0);

                if (e.getScoreboardTags().contains(RivalsTags.SCORE_BLOCK_ITEM_DISPLAY)) {
                    e.setCustomName((isBlockUsed ? DARK_GREEN : GREEN) + BOLD + "Score Block");
                    block = Material.EMERALD_BLOCK;
                    color = org.bukkit.Color.LIME;
                } else if (e.getScoreboardTags().contains(RivalsTags.RESTOCK_BLOCK_ITEM_DISPLAY)) {
                    e.setCustomName((isBlockUsed ? GOLD : YELLOW) + BOLD + "Restock Block");
                    block = Material.GOLD_BLOCK;
                    color = org.bukkit.Color.YELLOW;
                } else {
                    e.setCustomName((isBlockUsed ? DARK_AQUA : AQUA) + BOLD + "Energy Block");
                }

                world.spawnParticle(particle, prl, count, xd, yd, xd, 0.01, isBlockUsed ? block.createBlockData() : new Particle.DustOptions(color, 1));
            }
        }

        for (Marker e : world.getEntitiesByClass(Marker.class)) {
            //Team setters
            if (e.getScoreboardTags().contains(RivalsTags.SET_TEAM_RED)) {
                for (Entity entity : e.getNearbyEntities(2, 1, 2)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        User user = Util.getUserFromId(player.getUniqueId());

                        if (player.getGameMode() != GameMode.SPECTATOR) {
                            if (!user.getTeam(this.TEAM_RED)) {
                                user.setTeam(this.TEAM_RED);
                                player.sendMessage(GREEN + "You have joined " + RED + BOLD + "Red Team" + RESET + "!");
                                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                            }
                        }
                    }
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.SET_TEAM_BLUE)) {
                for (Entity entity : e.getNearbyEntities(2, 1, 2)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        User user = Util.getUserFromId(player.getUniqueId());

                        if (player.getGameMode() != GameMode.SPECTATOR) {
                            if (!user.getTeam(this.TEAM_BLUE)) {
                                user.setTeam(this.TEAM_BLUE);
                                player.sendMessage(GREEN + "You have joined " + BLUE + BOLD + "Blue Team" + RESET + "!");
                                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                            }
                        }
                    }
                }
            }

            if (e.getScoreboardTags().contains(RivalsTags.SET_TEAM_SPECTATOR)) {
                for (Entity entity : e.getNearbyEntities(2, 1, 2)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        User user = Util.getUserFromId(player.getUniqueId());

                        if (player.getGameMode() != GameMode.SPECTATOR) {
                            if (!user.getTeam(this.TEAM_SPECTATOR)) {
                                user.setTeam(this.TEAM_SPECTATOR);
                                player.sendMessage(GREEN + "You have joined " + WHITE + BOLD + "Spectators" + RESET + "!");
                                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
                            }
                        }
                    }
                }
            }

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
                        List<Entity> nearbyPlayers = e.getNearbyEntities(3, 1, 3);
                        if (nearbyPlayers.contains(player)) {
                            User user = Util.getUserFromId(player.getUniqueId());

                            for (Player other : world.getPlayers()) {
                                if (other.getGameMode() != GameMode.SPECTATOR) {
                                    User otherUser = Util.getUserFromId(other.getUniqueId());

                                    if (otherUser.playerId != user.playerId) {
                                        if (nearbyPlayers.contains(other)) {
                                            if (otherUser.getTeam() == user.getTeam()) {
                                                teammates.add(otherUser);
                                            } else {
                                                enemyOnPoint = true;
                                            }
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

            //Gummy Bear "Chaos Zone" Ability
            if (e.getScoreboardTags().contains(RivalsTags.CHAOS_ZONE_ENTITY)) {
                Location l = e.getLocation();
                double x = (radius * Math.sin(angle));
                double z = (radius * Math.cos(angle));
                angle += 0.1;

                world.spawnParticle(Particle.REDSTONE, l.getBlockX()+x, l.getBlockY(), l.getBlockZ()+z, 0, 0.001, 0,0,0, new Particle.DustOptions(org.bukkit.Color.AQUA, 5));
                world.spawnParticle(Particle.REDSTONE, l.getBlockX()-x, l.getBlockY(), l.getBlockZ()-z, 0, 0.001, 0,0,0, new Particle.DustOptions(org.bukkit.Color.AQUA, 5));
                for (Entity entity : e.getNearbyEntities(2, 1, 2)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        User user = Util.getUserFromId(player.getUniqueId());

                        if(player.getGameMode() != GameMode.SPECTATOR) {
                            if (!matchingTeams(user.getTeam(), e, player)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1, true, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5, 0, true, true));
                            }
                            if (matchingTeams(user.getTeam(), e, player)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 1, true, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2, true, true));
                            }
                        }
                    }
                }
                if (e.getTicksLived() > 200) {
                    e.remove();
                }
            }
            //Gummy Bear "Stink Bomb" ability
            if (e.getScoreboardTags().contains(RivalsTags.STINK_BOMB_ENTITY)) {
                Location l = e.getLocation();
                double x = l.getBlockX()+this.randomPosition(-2, 2);
                double y = l.getBlockY()+this.randomPosition(-1, 3);
                double z = l.getBlockZ()+this.randomPosition(-2, 2);
                double dX = this.randomPosition(-0.7, 0.7);
                double dY = this.randomPosition(-0.7, 0.7);
                double dZ = this.randomPosition(-0.7, 0.7);
                double dA = this.randomPosition(-0.4, 0.4);
                world.spawnParticle(Particle.REDSTONE, x, y, z, 10, dX, dY, dZ, dA, new Particle.DustOptions(org.bukkit.Color.YELLOW, 20));
                for (Entity entity : e.getNearbyEntities(3, 2, 3)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        User user = Util.getUserFromId(player.getUniqueId());
                        if (player.getGameMode() != GameMode.SPECTATOR) {
                            if(matchingTeams(user.getTeam(), e, player)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, true, true));
                            }
                            if(!matchingTeams(user.getTeam(), e, player)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5, 1, true, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 5, true, true));
                            }
                        }
                    }
                }
                if (e.getTicksLived() > 200) {
                    e.remove();
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

        //arrow decay
        for (Arrow e : world.getEntitiesByClass(Arrow.class)) {
            if (e.isInBlock()) {
                if (e.getTicksLived() > MAX_ARROW_LIFE) {
                    e.remove();
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
                        User user = Util.getUserFromId(e.getOwner());

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
                            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);

                            if (user != null) {
                                arrow.setShooter(user.getPlayer());
                            }
                        }

                        e.remove();
                    }
                }
            }

            //Rogue "Curse" Ability
            if (e.getScoreboardTags().contains(RivalsTags.CURSE_ENTITY)) {
                world.spawnParticle(Particle.SMOKE_NORMAL, e.getLocation(), 3, 0, 0, 0, 0.1);
                if (e.getTicksLived() > 40) {
                    world.playSound(e.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.75f, 1);

                    double d = 0.05;
                    world.spawnParticle(Particle.SMOKE_NORMAL, e.getLocation(), 200, d, d, d, 0.1);

                    for (Entity entity : e.getNearbyEntities(2, 2, 2)) {
                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            User user = Util.getUserFromId(player.getUniqueId());

                            if (!matchingTeams(user.getTeam(), e, player)) {
                                if (player.getGameMode() != GameMode.SPECTATOR) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false, false));
                                }
                            }
                        }
                    }

                    e.remove();
                }
            }

            //Wizard "Zap" ability
            if (e.getScoreboardTags().contains(RivalsTags.ZAP_ENTITY)) {
                if (e.getTicksLived() > 40) {
                    for (Entity entity : e.getNearbyEntities(2, 1, 2)) {
                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            User user = Util.getUserFromId(player.getUniqueId());

                            if (!matchingTeams(user.getTeam(), e, player)) {
                                if (player.getGameMode() != GameMode.SPECTATOR) {
                                    player.damage(6);
                                }
                            }
                        }
                    }

                    world.spawnParticle(Particle.REDSTONE, e.getLocation().add(0, 0.25, 0), 200, 1.25, 0, 1.25, 0.1, new Particle.DustOptions(org.bukkit.Color.YELLOW, 1));
                    world.playSound(e.getLocation(), Sound.ENTITY_BEE_HURT, 1, 1);

                    e.remove();
                }
            }
        }

        for (ArmorStand e : world.getEntitiesByClass(ArmorStand.class)) {
            //Kit selectors
            if (e.getScoreboardTags().contains(RivalsTags.KIT_STAND)) {
                ItemStack helmet = e.getEquipment().getHelmet();

                if (helmet != null) {
                    ItemMeta meta = helmet.getItemMeta();

                    boolean kitInUse = false;
                    boolean isBlue = e.getScoreboardTags().contains("rivals.team_blue");

                    for (User user : RivalsPlugin.players) {
                        for (Kit kit : Kit.KIT_LIST.values()) {
                            if (kit.kitName.equalsIgnoreCase(meta.getDisplayName())) {
                                if (user.currentKit == kit.kitID && ((isBlue && user.getTeam(this.TEAM_BLUE)) || (!isBlue && user.getTeam(this.TEAM_RED)))) {
                                    kitInUse = true;
                                }
                            }
                        }
                    }

                    for (Entity entity : e.getNearbyEntities(0.25, 1, 0.25)) {
                        if (entity instanceof Player) {
                            User user = Util.getUserFromId(entity.getUniqueId());

                            if (user.getPlayer().getGameMode() != GameMode.SPECTATOR) {
                                for (Kit kit : Kit.KIT_LIST.values()) {
                                    if (kit.kitName.equalsIgnoreCase(meta.getDisplayName())) {
                                        if (!kitInUse && user.currentKit != kit.kitID) {
                                            user.setKit(kit);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (kitInUse) {
                        world.spawnParticle(Particle.REDSTONE, e.getLocation().add(0, 0.25, 0), 10, 0.2, 0, 0.2, 0.1, new Particle.DustOptions(isBlue ? org.bukkit.Color.BLUE : org.bukkit.Color.RED, 1));
                    }

                    //Util.print("blue:" + isBlue + ", kit:" + meta.getDisplayName() + ", in use:" + kitInUse);
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
        firework.setMetadata("nodamage", new FixedMetadataValue(Util.getPlugin(), true));

        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect.Type burst = FireworkEffect.Type.BURST;
        //FireworkEffect.Type star = FireworkEffect.Type.STAR;
        org.bukkit.Color b = org.bukkit.Color.WHITE;

        FireworkEffect effect = FireworkEffect.builder().with(burst).withColor(mainColor).withColor(b).build();
        fireworkMeta.addEffect(effect);

        fireworkMeta.setPower(0);
        firework.setFireworkMeta(fireworkMeta);
        firework.addScoreboardTag(RivalsTags.FIREWORK_EFFECT);

        firework.detonate();
    }

    private void setupSideBar(boolean registered) {
        if (registered) {
            this.displayBoard.unregister();

            this.displayBoard = this.board.registerNewObjective(RivalsTags.DISPLAY_BOARD, Criteria.DUMMY, RED + BOLD + "RIVALS: " + RESET + BLUE + "Dominate");
            this.displayBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        Score s0 = this.displayBoard.getScore(GOLD + "Map" + DARK_AQUA + ": " + GREEN + this.currentMap.displayName);
        s0.setScore(7);
        Score s1 = this.displayBoard.getScore("");
        s1.setScore(6);
        Score s2 = this.displayBoard.getScore(GRAY + "[" + pointAState.color.toString() + "Point A" + GRAY + "]");
        s2.setScore(5);
        Score s3 = this.displayBoard.getScore(GRAY + "[" + pointBState.color.toString() + "Point B" + GRAY + "]");
        s3.setScore(4);
        Score s4 = this.displayBoard.getScore(GRAY + "[" + pointCState.color.toString() + "Point C" + GRAY + "]");
        s4.setScore(3);
        Score s5 = this.displayBoard.getScore(GRAY + "[" + pointDState.color.toString() + "Point D" + GRAY + "]");
        s5.setScore(2);
        Score s6 = this.displayBoard.getScore(GRAY + "[" + pointEState.color.toString() + "Point E" + GRAY + "]");
        s6.setScore(1);
    }
}
