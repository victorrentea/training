package yatzi;

import org.junit.*;

import static org.junit.Assert.*;

public class YatzyTest {

   @Test
   public void chance_scores_sum_of_all_dice() {
      assertEquals(15, Yatzy.chance(new DiceHand(2, 3, 4, 5, 1)));
      assertEquals(16, Yatzy.chance(new DiceHand(3, 3, 4, 5, 1)));
   }

   @Test
   public void yatzy_scores_50() {
      assertEquals(50, Yatzy.yatzy(new DiceHand(4, 4, 4, 4, 4)));
      assertEquals(50, Yatzy.yatzy(new DiceHand(6, 6, 6, 6, 6)));
      assertEquals(0, Yatzy.yatzy(new DiceHand(6, 6, 6, 6, 3)));
   }

   @Test
   public void test_1s() {
      assertTrue(Yatzy.ones(new DiceHand(1, 2, 3, 4, 5)) == 1);
      assertEquals(2, Yatzy.ones(new DiceHand(1, 2, 1, 4, 5)));
      assertEquals(0, Yatzy.ones(new DiceHand(6, 2, 2, 4, 5)));
      assertEquals(4, Yatzy.ones(new DiceHand(1, 2, 1, 1, 1)));
   }

   @Test
   public void test_2s() {
      assertEquals(4, Yatzy.twos(new DiceHand(1, 2, 3, 2, 6)));
      assertEquals(10, Yatzy.twos(new DiceHand(2, 2, 2, 2, 2)));
   }

   @Test
   public void test_threes() {
      assertEquals(6, Yatzy.threes(new DiceHand(1, 2, 3, 2, 3)));
      assertEquals(12, Yatzy.threes(new DiceHand(2, 3, 3, 3, 3)));
   }

   @Test
   public void fours_test() {
      assertEquals(12, Yatzy.fours(new DiceHand(4, 4, 4, 5, 5)));
      assertEquals(8, Yatzy.fours(new DiceHand(4, 4, 5, 5, 5)));
      assertEquals(4, Yatzy.fours(new DiceHand(4, 5, 5, 5, 5)));
   }

   @Test
   public void fives() {
      assertEquals(10, Yatzy.fives(new DiceHand(4, 4, 4, 5, 5)));
      assertEquals(15, Yatzy.fives(new DiceHand(4, 4, 5, 5, 5)));
      assertEquals(20, Yatzy.fives(new DiceHand(4, 5, 5, 5, 5)));
   }

   @Test
   public void sixes_test() {
      assertEquals(0, Yatzy.sixes(new DiceHand(4, 4, 4, 5, 5)));
      assertEquals(6, Yatzy.sixes(new DiceHand(4, 4, 6, 5, 5)));
      assertEquals(18, Yatzy.sixes(new DiceHand(6, 5, 6, 6, 5)));
   }

   @Test
   public void one_pair() {
      assertEquals(6, Yatzy.score_pair(3, 4, 3, 5, 6));
      assertEquals(10, Yatzy.score_pair(5, 3, 3, 3, 5));
      assertEquals(12, Yatzy.score_pair(5, 3, 6, 6, 5));
   }

   @Test
   public void two_Pair() {
      assertEquals(16, Yatzy.two_pair(3, 3, 5, 4, 5));
      assertEquals(16, Yatzy.two_pair(3, 3, 5, 5, 5));
   }

   @Test
   public void three_of_a_kind() {
      assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 4, 5));
      assertEquals(15, Yatzy.three_of_a_kind(5, 3, 5, 4, 5));
      assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 3, 5));
   }

   @Test
   public void four_of_a_knd() {
      assertEquals(12, Yatzy.four_of_a_kind(3, 3, 3, 3, 5));
      assertEquals(20, Yatzy.four_of_a_kind(5, 5, 5, 4, 5));
      assertEquals(9, Yatzy.three_of_a_kind(3, 3, 3, 3, 3));
   }

   @Test
   public void smallStraight() {
      assertEquals(15, Yatzy.smallStraight(1, 2, 3, 4, 5));
      assertEquals(15, Yatzy.smallStraight(2, 3, 4, 5, 1));
      assertEquals(0, Yatzy.smallStraight(1, 2, 2, 4, 5));
   }

   @Test
   public void largeStraight() {
      assertEquals(20, Yatzy.largeStraight(6, 2, 3, 4, 5));
      assertEquals(20, Yatzy.largeStraight(2, 3, 4, 5, 6));
      assertEquals(0, Yatzy.largeStraight(1, 2, 2, 4, 5));
   }

   @Test
   public void fullHouse() {
      assertEquals(18, Yatzy.fullHouse(6, 2, 2, 2, 6));
      assertEquals(0, Yatzy.fullHouse(2, 3, 4, 5, 6));
   }
}
