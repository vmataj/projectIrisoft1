package onlineLibrary;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static Library library;
	private Scanner scanner;

	public Main() {
		library = new Library();
		scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {

		Main main = new Main();
		main.run();

	}

	public void run() {

		library.createDataFileIfNotExists("library_data.txt");

		library.loadLibraryFromFile("library_data.txt");

		boolean exit = false;

		while (!exit) {
			System.out.println("Vendos numrin per veprimin perkates:");
			System.out.println("1 -> Per te shtuar libra");
			System.out.println("2 -> Per te pare librat e disponueshem");
			System.out.println("3 -> Per te huazuar nje liber");
			System.out.println("4 -> Per te pare librat e huazuar");
			System.out.println("5 -> Per te kthyer nje liber te huazuar");
			System.out.println("6 -> Per te kerkuar nje liber");
			System.out.println("0 -> Per te dale nga menu");
			System.out.println();

			try {
				int choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {

				case 1:
					addBooksToLibrary();
					break;

				case 2:

					if (library.checkIfBookIsEmpty()) {
						System.out.println("Nuk ka asnje liber te disponueshem per momentin.\n");
					} else {
						library.displayAvailableBooks();
					}
					break;

				case 3:

					if (library.checkIfBookIsEmpty()) {
						System.out.println("Per momentin nuk ka asnje liber te disponueshem per huazim.\n");
					} else {
						borrowBookByTitle();
					}
					break;

				case 4:

					library.displayBorrowedBooks();
					break;

				case 5:

					if (library.checkIfBookIsEmpty()) {
						System.out.println("Libri nuk mund te kthehet prane bibliotekes.\n");

					} else {
						returnBorrowedBooks();
					}
					break;

				case 6:
					if (!library.checkIfBookIsEmpty()) {
						searching();
					} else {
						System.out.println("Nuk ka libra ne librari.\n");
					}
					break;

				case 0:

					library.saveLibraryToFile("library_data.txt");
					exit = true;
					break;

				default:

					System.out.println("Numri qe vendoset nuk eshte i sakte!");
				}
			} catch (InputMismatchException e) {
				System.err.println("Input i pavlefshem, JU LUTEM fusni nje numer te vlefshem!\n");
				scanner.nextLine();
			}

		}

	}

	private void searching() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Vendos Titullin: ");
		String searchTitle = scan.nextLine();

		System.out.println(library.searchBookByTitleOrAuthor(searchTitle));
	}

	public void returnBorrowedBooks() {

		System.out.print("Fusni titullin e librit qe doni te ktheni: ");
		String title = scanner.nextLine();

		if (library.returnBorrowedBook(title)) {
			System.out.println("Libri me titull '" + title + "' u kthye me sukses!\n");
		} else {
			System.out.println("Libri me titull '" + title + "' nuk mund te kthehet prane bibliotekes.");
		}

	}

	public static void addBooksToLibrary() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Vendos titullin e librit: ");
		String titleInput = scanner.nextLine();
		String str1 = titleInput.substring(0, 1).toUpperCase();
		String str2 = titleInput.substring(1);
		String title = str1 + str2;

		System.out.println("Vendos autorin e librit: ");
		String authorInput = scanner.nextLine();
		String s1 = authorInput.substring(0, 1).toUpperCase();
		String s2 = authorInput.substring(1);
		String author = s1 + s2;

		int publicationYear = 0;

		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.println("Vendos vitin e publikimit te librit: ");
				publicationYear = scanner.nextInt();
				validInput = true;
			} catch (InputMismatchException e) {
				System.err.println("Viti i publikimit duhet te jete nje numer i plote.");
				scanner.nextLine();
			}
		}

		Random rand = new Random();
		int randomISBN = rand.nextInt(999999);
		System.out.println("'ISBN' e librit eshte: " + randomISBN + "\n");

		Book book = new Book();
		book.setAuthor(author);
		book.setTitle(title);
		book.setiSBN(randomISBN);
		book.setPublicationYear(publicationYear);

		try {
			library.addBooks(book);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(library.toString() + "\n");

	}

	public void borrowBookByTitle() {
		System.out.print("Fusni titullin e librit qe doni te huazoni: ");
		String title = scanner.nextLine();

		if (library.isBookAvailable(title)) {
			library.borrowBooks(title);
		} else {
			System.out.println("Libri me titull '" + title + "' nuk eshte i disponueshem per huazim.");
		}
	}

}
