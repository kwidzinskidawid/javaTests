package wej01;

import java.util.ArrayList;
import java.util.List;

public class BookManager implements IBookManager{

	private List<Book> books;
	
	public BookManager() {
		books = new ArrayList<Book>();
	}
	
	@Override
	public void add(Book b) {
		books.add(b);
	}

	@Override
	public List<Book> getAll() {
		return books;
	}

}
