package wej01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class BookManagerTest {
	
	BookManager bm = new BookManager();
	
	@Test
	public void checkAdding() {
		
		Book b = new Book("The Sun Also Rises", "Ernest Hemingway", "novel", 1926);

		bm.add(b);
		
		List<Book> books = bm.getAll();
		
		Book bTest = books.get(0);
		
		assertEquals(bTest.getTitle(), "The Sun Also Rises");
		assertSame(bTest.getAuthorName(), "Ernest Hemingway");
		assertTrue(bTest.getCategory() == "novel");
		assertEquals(bTest.getYearOfPublishing(), 1926);
	}
	
	@Test
	public void checkGettingAll() {
		Book b = new Book("The Sun Also Rises", "Ernest Hemingway", "novel", 1926);
		Book b2 = new Book("Last Stop on Market Street", "Matt De La Peña", "childrens book", 2015);
		Book b3 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "fantasy", 1999);
		
		bm.add(b);
		bm.add(b2);
		bm.add(b3);
		
		List<Book> books = bm.getAll();
		
		assertEquals(books.size(), 3);
		assertEquals(books.get(0), b);
		assertEquals(books.get(1), b2);
		assertEquals(books.get(0).getTitle(), "The Sun Also Rises");
		assertEquals(books.get(1).getCategory(), "childrens book");
		assertEquals(books.get(2).getAuthorName(), "J.K. Rowling");
	}
}
