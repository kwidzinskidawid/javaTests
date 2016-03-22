package lab03;


public class BookManager implements IBookManager{

	private IMyList container;
	
	public BookManager(IMyList container) {
		this.container = container;
	}
	
	@Override
	public void add(Book b) {
		container.add(b);
	}

	@Override
	public IMyList getAll() {
		return container.getAll();
	}
	
	@Override
	public Book get(int index) {
		return container.get(index);
	}

}
