package caplin.tdd;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by nicolaes on 08/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LibraryTest {
		@Mock Title mockTitle;

		@Test
		public void libraryAddsDonatedTitle() {
				Library library = initLibrary();
				Assert.assertTrue(library.getTitles().contains(mockTitle));
		}

		private Library initLibrary() {
				Library library = new Library();
				library.donateAndRegisterTitle(mockTitle);
				return library;
		}

		@Test
		public void libraryAddsNewTitle() {
				Library library = initLibrary();
				Assert.assertTrue(library.getNewTitles().contains(mockTitle));
		}

		@Test
		public void tellsTitleToRegisterRentalCopy() {

		}
}
