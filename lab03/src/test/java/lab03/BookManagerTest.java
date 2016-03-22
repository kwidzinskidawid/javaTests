package lab03;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import lab03.BookManager;
import lab03.Book;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;


public class BookManagerTest {
	

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);
	
	@Mock
	private IMyList mock;

	@TestSubject
	private IBookManager bm = new BookManager(mock);
	

	@Test
	public void checkAdding() {
		Book b = new Book("The Sun Also Rises", "Ernest Hemingway", "novel", 1926);
		List<Book> books = new ArrayList<Book>();
		
		mock.add(b);
		expectLastCall();
		expect(mock.size()).andReturn(1);
		expect(mock.getAll()).andReturn(mock);
		expect(mock.get(0)).andReturn(b).anyTimes();
		replay(mock);
		bm.add(b);
		
		assertEquals(1, bm.getAll().size());
		assertEquals("The Sun Also Rises", bm.get(0).getTitle());
		assertSame("Ernest Hemingway", bm.get(0).getAuthorName());
		assertTrue("novel" == bm.get(0).getCategory());
		assertEquals(1926, bm.get(0).getYearOfPublishing());
	}
	
}
