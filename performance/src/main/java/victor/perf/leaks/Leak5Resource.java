package victor.perf.leaks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("leak5")
public class Leak5Resource {
	
	public static void main(String[] args) {
		Integer i1 = 128;
		Integer i2 = 128;
		System.out.println(i1 == i2);
//		"a".equals(s);/
	}
	
	@GET
	public String test() throws Exception {
		return "call <a href='./one'>/one</a> and <a href='./two'>/two</a> withing 3 secs..";
	}
	
	@GET
	@Path("/one")
	public String test1() throws Exception {
		KillOne.entryPoint();
		return "--> Please call /two in 3 secs..";
	}
	
	@GET
	@Path("/two")
	public String test2() throws Exception {
		KillTwo.entryPoint();
		return "--> Please call /one in 3 secs..";
	}
}
