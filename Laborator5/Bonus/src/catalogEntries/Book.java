package catalogEntries;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book extends CatalogEntry implements Serializable {
    private final String ISBN;
    private final String author;

    public Book(String bookName, String bookPath, String author, String newISBN) throws InvalidISBNException
    {
        super(bookName,bookPath);
        this.author = author;

        // Test if a corresponding string is an ISBN
        // I know it's hardcoded and a bit too long, but didn't want to store it in a file in order not to have to handle
        // another exception
        String ISBNRegex = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        var pattern = Pattern.compile(ISBNRegex);
        Matcher matcher = pattern.matcher(newISBN);

        if(!matcher.matches())
        {
            throw new InvalidISBNException("Invalid ISBN!");
        }

        this.ISBN = newISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", entryName='" + entryName + '\'' +
                ", filePath=" + filePath +
                '}';
    }
}
