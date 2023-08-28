package onlineLibrary;

public class Book {

	String title;
	String author;
	int publicationYear;
	private int iSBN;
	private boolean status;

	public Book() {
		this.title = new String();
		this.author = new String();
		this.status = true;
	}

	public int getiSBN() {
		return iSBN;
	}

	public void setiSBN(int iSBN) {
		this.iSBN = iSBN;
	}

	public boolean isAvailable() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public String toString() {
		String availability = null;
		return "Libri => { Titulli: " + title + ", Autori: " + author + ", Viti i Publikimit: " + publicationYear
				+ ", ISBN: " + iSBN + ", Disponueshmeria: "
				+ (availability = status ? "I Dispunueshem" : "I Padisponueshem") + " }\n";

	}

}
