package victor.perf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HomeResource {
	
	@GET
	public String home() {
		String s = "";
		s+="<a href='./leak1'/>leak1</a><br />";
		s+="<a href='./leak2'/>leak2</a><br />";
		s+="<a href='./leak3'/>leak3</a><br />";
		s+="<a href='./leak4'/>leak4</a><br />";
		s+="<a href='./leak5'/>leak5</a><br />";
		return s;
	}
}
