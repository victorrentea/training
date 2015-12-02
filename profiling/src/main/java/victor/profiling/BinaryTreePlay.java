package victor.profiling;

import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class BinaryTreePlay {
	
	private TreeSet<Integer> tree = new TreeSet<>();
	
	private void play() throws InterruptedException {
		Random generator = new Random();
		for (int i=0; i<Integer.MAX_VALUE - 1; i ++) {
			tree.add(generator.nextInt());
			Thread.sleep(10);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNext()) {
				int n = scanner.nextInt();
			}
		}
		new BinaryTreePlay().play();
	}
	
}
