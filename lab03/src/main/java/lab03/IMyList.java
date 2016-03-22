package lab03;



public interface IMyList {
	public void add(Book b);
	public Book get(int i);
	public IMyList getAll();
	public int size();
	public boolean isEmpty();
}
