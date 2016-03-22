package lab03;

public interface IBookManager {
	public void add(Book b);
	public IMyList getAll();
	public Book get(int index);
}
