package batch.model.domain;


public enum BoxType  {

    SMALL(false),
    LARGE(false),
    SMALL_COOLED(true),
    LARGE_COOLED(true),
    NON_BOX(false);

    private boolean temperatureApplicable;

    private BoxType(boolean temperatureApplicable) {
        this.temperatureApplicable = temperatureApplicable;
    }

    public boolean isTemperatureApplicable() {
        return temperatureApplicable;
    }

}