package victor.perf.stock;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapPlay {
	
	static WeakHashMap<String, String> weakMap = new WeakHashMap<>();
	static WeakHashMap<String, MyDomain> weakMapObj = new WeakHashMap<>();
	static HashMap<String, WeakReference<MyDomain>> weakValuesMap= new HashMap<>();
	
	static class MyDomain {
		String id;
		public MyDomain(String id) {
			this.id = id;
		}
	}
	
	public static void main(String[] args) {
		
		String key1 = new String("a1"); // GC Root = Stack Frame (local meth variable)
		weakMap.put(key1, "v1");

		String v2 = "v2";
		weakMap.put(new String("a2"), v2);
		
		MyDomain v3 = new MyDomain("a3");
		weakMapObj.put(v3.id, v3);

		MyDomain v4 = new MyDomain("a4");
		weakMapObj.put(new String("a4"), v4); // daca puneam direct "a4" refolosea instanta din Strng Pool

		weakValuesMap.put("a5", new WeakReference<MyDomain>(new MyDomain("a5")));
		
		PhantomReference<MyDomain> x;
		
		
		System.gc();
		
		System.out.println(weakMap.get(new String("a1")));
		System.out.println(weakMap.get(new String("a2")));
		System.out.println(weakMap.size());
		System.out.println(weakMapObj.get(new String("a3")));
		System.out.println(weakMapObj.get(new String("a4")));
		System.out.println(weakValuesMap.get("a5").get());
		System.out.println(weakValuesMap.size());
	}
}
