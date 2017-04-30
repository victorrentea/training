package caplin.tdd;

import org.w3c.dom.DOMStringList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaes on 08/01/2016.
 */
public class Library {
		private List<Title> titles = new ArrayList<Title>();
		private List<Title> newTitles = new ArrayList<Title>();

		public boolean donateAndRegisterTitle(Title mockTitle) {
				titles.add(mockTitle);
				newTitles.add(mockTitle);
				return true;
		}

		public List getTitles() {
				return titles;
		}

		public List getNewTitles() {
				return newTitles;
		}
}
