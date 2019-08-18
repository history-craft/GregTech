package gregtech.api.unification.material.properties;

public class FluidCellProperties {

    public final int maxCapacity;
    public final int minFluidTemperature;
    public final int maxFluidTemperature;

    public FluidCellProperties(int maxCapacity, int minFluidTemperature, int maxFluidTemperature) {
        this.maxCapacity = maxCapacity;
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getMaxFluidTemperature() {
        return maxFluidTemperature;
    }

    public int getMinFluidTemperature() {
        return minFluidTemperature;
    }
}
