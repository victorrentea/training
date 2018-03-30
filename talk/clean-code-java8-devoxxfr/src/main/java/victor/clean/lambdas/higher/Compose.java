package victor.clean.lambdas.higher;

public class Compose {
	
	public static void main(String[] args) {
		String s = "a <    B";
		s = collapseSpaces(escapeHtml(s.toUpperCase())); // that's a lot simpler ! just do f(g(h(x)))
		System.out.println(s);
	}

	// existing function
	private static String escapeHtml(String original) {
		return original.replace("<", "&lt;");
	}

	private static String collapseSpaces(String original) {
		return original.replaceAll("\\s+", " ");
	}
	
}
