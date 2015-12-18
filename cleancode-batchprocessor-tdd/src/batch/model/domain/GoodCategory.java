package batch.model.domain;

import java.util.EnumSet;

public enum GoodCategory  {

	FROZEN(-18, -18), PREP_MEAT_FISH(4, 3), FRESH_COOL(4, 3), FRESH_NONCOOL(null, null), NON_PERISH(null, null), NON_FOOD(null, null);

    private Integer maxTemperature;
    private Integer defaultTemperature;

    private GoodCategory(Integer maxTemperature, Integer defaultTemperature) {
        this.maxTemperature = maxTemperature;
        this.defaultTemperature = defaultTemperature;
    }

    public Integer getMaxTemperature(){
        return maxTemperature;
    }

    public Integer getDefaultTemperature() {
        return defaultTemperature;
    }

    public static EnumSet<GoodCategory> getGoodCategories(BoxType type) {
		if (type == BoxType.LARGE_COOLED || type == BoxType.SMALL_COOLED ) {
		    return EnumSet.of(FROZEN, PREP_MEAT_FISH, FRESH_COOL,FRESH_NONCOOL);
		    
		}else {
		    return EnumSet.of(NON_FOOD,NON_PERISH);
		}
	}
}
