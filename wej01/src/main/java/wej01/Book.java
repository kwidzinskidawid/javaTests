package wej01;

public class Book {
	private String title;
	private String authorName;
	private String category;
	private int yearOfPublishing;
	
	
	public Book(String title, String authorName, String category,
			int yearOfPublishing) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.category = category;
		this.yearOfPublishing = yearOfPublishing;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getYearOfPublishing() {
		return yearOfPublishing;
	}
	public void setYearOfPublishing(int yearOfPublishing) {
		this.yearOfPublishing = yearOfPublishing;
	}
}
