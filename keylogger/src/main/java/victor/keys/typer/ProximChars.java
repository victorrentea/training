package victor.keys.typer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class ProximChars {
	static Map<Character, Set<Character>> proximals = new HashMap<>();
	
	static {
	    double[] d = new double[26];
	    d['q'-97] = 100d;
	    d['w'-97] = 101d;
	    d['e'-97] = 102d;
	    d['r'-97] = 103d;
	    d['t'-97] = 104d;
	    //(optionally, put a space of 5 between right hand and left hand for each row)
	    d['y'-97] = 105d;
	    d['u'-97] = 106d;
	    d['i'-97] = 107d;
	    d['o'-97] = 108d;
	    d['p'-97] = 109d;


	    //my keyboard middle row is about 20% indented from first row
	    d['a'-97] = 200.2;
	    d['s'-97] = 201.2;
	    d['d'-97] = 202.2;
	    d['f'-97] = 203.2;
	    d['g'-97] = 204.2;
	    d['h'-97] = 205.2;
	    d['j'-97] = 206.2;
	    d['k'-97] = 207.2;
	    d['l'-97] = 208.2;

	    //third row is about 50% indented from middle row
	    d['z'-97] = 300.5;
	    d['x'-97] = 301.5;
	    d['c'-97] = 302.5;
	    d['v'-97] = 303.5;
	    d['b'-97] = 304.5;
	    d['n'-97] = 305.5;
	    d['m'-97] = 306.5;

	    for (char a = 'a'; a <= 'z'; a++) {
	        for (char b = 'a'; b <= 'z'; b++)
	            if (a != b && prox(a,b,d)) {
	            	addKeyToValue(proximals, a, b);
	            	addKeyToValue(proximals, b, a);
	            }
	    }
	}
	
	public static void main(String[] args) {
		for (Entry<Character, Set<Character>> e : proximals.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}
	
	public static void addKeyToValue(Map<Character, Set<Character>> map, char k, char v) {
		map.putIfAbsent(k, new HashSet<>());
		map.get(k).add(v);
	}

	static boolean prox(char a, char b, double[] m) {
	    double a1 = m[a-97];
	    double a2 = m[b-97];

	    double d = Math.abs(a1-a2);
	    //TODO: add in d == 5 if there is a spacing for left and right hand gap (since it's more unlikely of a crossover)
	    return d == 0 || d == 1 || (d >= 99 && d <= 101);
	}
	
	static char getRandomProximal(char c) {
		List<Character> proxies = new ArrayList<>(proximals.get(Character.toLowerCase(c)));
		Character out = proxies.get(new Random().nextInt(proxies.size()));
		if (Character.isUpperCase(c)) {
			out = Character.toUpperCase(out);
		}
		return out;
	}
}
