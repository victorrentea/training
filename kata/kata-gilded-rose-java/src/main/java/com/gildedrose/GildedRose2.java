package com.gildedrose;

import java.util.Arrays;
import java.util.function.Function;

public class GildedRose2 {
   Item[] items;

   public GildedRose2(Item[] items) {
      this.items = items;
   }

   private static int calculateSulfurasQuality(Item item) {
      return item.quality;
   }

   private static int calculateNormalQuality(Item item) {
      int quality = item.quality;
      quality--;
      if (item.sellIn <= 0) quality--;
      return getZeroQualityIfNegative(quality);
   }

   private static int getZeroQualityIfNegative(int quality) {
      return Math.max(quality, 0);
   }

   private static int calculateConjuredQuality(Item item) {
      int quality = item.quality;
      quality -= 2;
      if (item.sellIn <= 0) quality -= 2;
      return getZeroQualityIfNegative(quality);
   }

   private static int calculateAgeBrieQuality(Item item) {
      int quality = item.quality;
      quality++;
      if (item.sellIn <= 0) quality++;
      return getMaxQualityIfMore(quality);
   }

   private static int getMaxQualityIfMore(int quality) {
      return Math.min(quality, 50);
   }

   private static int calculateBackstageQuality(Item item) {
      int quality = item.quality;
      int sellIn = item.sellIn;
      quality++;
      if (sellIn <= 10) quality++;
      if (sellIn <= 5) quality++;
      if (sellIn <= 0) quality = 0;
      return getMaxQualityIfMore(quality);
   }

   public void updateQuality() {
      for (Item item : items) {
         item.quality = ItemType.getTypeOf(item.name).calculateQuality.apply(item);
         if (!ItemType.SULFURAS.name.equals(item.name)) {
            item.sellIn -= 1;
         }
      }
   }

   public enum ItemType {
      AGED_BRIE("Aged Brie", GildedRose2::calculateAgeBrieQuality),
      BACKSTAGE("Backstage passes to a TAFKAL80ETC concert", GildedRose2::calculateBackstageQuality),
      SULFURAS("Sulfuras, Hand of Ragnaros", GildedRose2::calculateSulfurasQuality),
      CONJURED("Conjured", GildedRose2::calculateConjuredQuality),
      DEFAULT("Default", GildedRose2::calculateNormalQuality);

      public final Function<Item, Integer> calculateQuality;
      private String name;

      ItemType(String name, Function<Item, Integer> calculateQuality) {
         this.name = name;
         this.calculateQuality = calculateQuality;
      }

      public static ItemType getTypeOf(String name) {
         return Arrays.stream(values()).filter(itemType -> itemType.name.equals(name)).findFirst().orElse(DEFAULT);
      }
   }
}
