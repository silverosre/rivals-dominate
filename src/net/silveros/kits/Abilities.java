package net.silveros.kits;

public enum Abilities {
    //Bunket
    SHIELD_UP(2, 55),
    EMERGENCY_REPAIRS(3, -1),
    SELF_DESTRUCT(3, -1),
    //Archer
    SNARE(1, 30),
    FLETCH(1, 5),
    FROM_ABOVE(3, 20),
    //Rogue
    INCANTATION(3, 30),
    CURSE(1, 10),
    SWIFT(2, 45),
    //Herobrine
    HEROBRINE_POWER(6, 55),
    FOG_CLOAK(2, 30),
    UNCLOAK(0, 2),
    //Gummybear
    NORMAL_BEAR,
    DEFENSE_BEAR,
    ATTACK_BEAR,
    SPEED_BEAR,
    NUMB,
    STINK_BOMB,
    CHAOS_ZONE,
    //Wizard
    ZAP(2, 5),
    FIREBALL(4, 5),
    FREEZE(1, 20),
    //Bandit
    STEAL(0, 1),
    GIVE(1, 2),
    RELOAD(2, 0),;

    private int abilityCost, abilityCooldown;

    private Abilities() {

    }

    private Abilities(int cost, int seconds) {
        this.abilityCost = cost;
        this.abilityCooldown = seconds * 20;
    }

    public int getCost() {
        return this.abilityCost;
    }

    public int getCooldown() {
        return this.abilityCooldown;
    }

    public double getCooldownForDisplay() {
        return this.abilityCooldown / 20;
    }
}
