package net.silveros.utility;

public class Vec3 {
    public double posX, posY, posZ;

    public Vec3(int x, int y, int z) {
        this.posX = 0.5 + x;
        this.posY = y;
        this.posZ = 0.5 + z;
    }

    @Override
    public String toString() {
        return "[" + this.posX + "," + this.posY + "," + this.posZ + "]";
    }
}
