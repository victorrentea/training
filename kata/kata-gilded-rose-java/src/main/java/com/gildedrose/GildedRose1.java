package com.gildedrose;

public class GildedRose1 {
   public Item[] items;

   public GildedRose1(Item[] items) {
      this.items = items;
   }

   public void updateQuality() {
      for (Item item : items) {
         item.sellIn--;
         GildedRose1.this.calculateNewQuality(item);
      }
   }

   private void calculateNewQuality(Item item) {
      switch (item.name) {
         case "Backstage passes to a TAFKAL80ETC concert":
            bumpBackstagePassesQuality(item);
            break;
         case "Aged Brie":
            bumpQuality(item);
            break;
         case "Sulfuras, Hand of Ragnaros":
            item.quality=80;
            break;
         case "Conjured Mana Cake":
            degrade(item);
            if (wasNotSoldOnTIme(item)) degrade(item);
         default:
            degrade(item);
            if (wasNotSoldOnTIme(item)) degrade(item);
            break;
      }
   }

   private boolean wasNotSoldOnTIme(Item item) {
      return item.sellIn < 0;
   }

   private void degrade(Item item) {
      if (item.quality > 0) item.quality--;
   }

   private void bumpBackstagePassesQuality(Item item) {
      if (item.sellIn <= 5) bumpQuality(item);
      if (item.sellIn <= 10) bumpQuality(item);
      bumpQuality(item);
      if (wasNotSoldOnTIme(item)) item.quality = 0;
   }

   private void bumpQuality(Item item) {
      if (item.quality < 50) item.quality++;
   }
}