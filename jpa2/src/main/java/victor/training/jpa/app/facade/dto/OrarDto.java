package victor.training.jpa.app.facade.dto;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrarDto {
	public static class CellDto {
		public int colskip = 0;
		public int colspan = 1;
		public int rowspan = 1;
		public String label;
	}
	
	
	public String yearCode;
	public List<String> groups = new ArrayList<>();
	public Map<DayOfWeek, Map<String,List<CellDto>>> lines = new LinkedHashMap<>();
}
