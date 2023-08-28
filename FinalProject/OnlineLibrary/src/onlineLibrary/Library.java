package onlineLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Library extends Book {

	private ArrayList<Book> borrowed;
	private ArrayList<Book> books;

	public Library() {
		super();
		this.books = new ArrayList<>();
		this.borrowed = new ArrayList<>();
	}

	public ArrayList<Book> getBorrowed() {
		return borrowed;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	void addBooks(Book book) {
		books.add(book);
		System.out.println("Libri u shtua me sukses!\n");
	}

	void displayBorrowedBooks() {

		if (borrowed.isEmpty()) {
			System.out.println("Nuk ka asnje liber te huazuar per momentin.\n");
		} else {
			for (Book borrow : borrowed) {
				System.out.println(
						"Librat e huazuar jane:\n" + borrow.getTitle() + " nga autori:  " + borrow.getAuthor() + "\n");
			}
			System.out.println();
		}

	}

	void borrowBooks(String title) {

		for (Book book : books) {
			if (book.getTitle().trim().equalsIgnoreCase(title)) {
				book.setStatus(false);
				borrowed.add(book);
				System.out.println("Libri u huazua me sukses.\n");
			}
		}
	}

	public boolean isBookAvailable(String title) {
		for (Book book : books) {
			if (book.getTitle().trim().equalsIgnoreCase(title) && book.isAvailable()) {
				return true;
			}
		}
		return false;
	}

	public void displayAvailableBooks() {

		System.out.println("Librat e disponueshem jane:");
		for (Book book : books) {
			if (book.isAvailable()) {
				System.out.println("\"" + book.getTitle() + "\" nga autori:  " + book.getAuthor());
			}
		}
		System.out.println();

	}

	public boolean returnBorrowedBook(String title) {

		for (Book book : books) {
			if (book.getTitle().trim().equalsIgnoreCase(title) && !book.isAvailable()) {
				book.setStatus(true);
				borrowed.remove(book);
				return true;
			}
		}
		return false;

	}

	public boolean checkIfBookIsEmpty() {

		if (books.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public String searchBookByTitleOrAuthor(String searchTitle) {

		for (Book book : books) {
			if (book.getTitle().trim().equalsIgnoreCase(searchTitle)) {
				return "Libri qe kerkuat u gjet:\n" + book.toString();
			}
		}

		return "Libri nuk u gjet!\n";

	}

	public void saveLibraryToFile(String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (Book book : books) {
				writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPublicationYear() + ","
						+ book.getiSBN() + "," + book.isAvailable() + "\n");
			}
			for (Book book : borrowed) {
				writer.write("Borrowed: " + book.getTitle() + "," + book.getAuthor() + "," + book.getPublicationYear()
						+ "," + book.getiSBN() + "," + book.isAvailable() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadLibraryFromFile(String filename) {
		books.clear();
		borrowed.clear();

		if (!new File(filename).exists()) {
			System.out.println("Library data file does not exist. Starting with an empty library.");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Borrowed: ")) {
					line = line.substring(10);
					Book book = parseBookFromString(line);
					if (book != null) {
						borrowed.add(book);
					}
				} else {
					Book book = parseBookFromString(line);
					if (book != null) {
						books.add(book);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createDataFileIfNotExists(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Book parseBookFromString(String bookData) {
		String[] parts = bookData.split(",");
		if (parts.length >= 5) {
			Book book = new Book();
			book.setTitle(parts[0]);
			book.setAuthor(parts[1]);
			book.setPublicationYear(Integer.parseInt(parts[2]));
			book.setiSBN(Integer.parseInt(parts[3]));
			book.setStatus(Boolean.parseBoolean(parts[4]));
			return book;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Librat e bibliotekes\n[Librat=" + getBooks() + "]";
	}

}
