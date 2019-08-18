package gregtech.api.unification.material.properties;

public class FuelRodProperties {

    private final int fuelEfficiency;
    private final int maxDurability;
    private final float heatPerTick;
    private final boolean consumable;

    public FuelRodProperties(int fuelEfficiency, int maxDurability, float heatPerTick, boolean consumable) {
        this.fuelEfficiency = fuelEfficiency;
        this.maxDurability = maxDurability;
        this.heatPerTick = heatPerTick;
        this.consumable = consumable;
    }

    public float getHeatPerTick() {
        return heatPerTick;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public boolean isConsumable() {
        return consumable;
    }
}
