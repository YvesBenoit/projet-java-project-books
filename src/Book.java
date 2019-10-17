import java.util.Objects;

public class Book {
    private String name;
    private int nbLines;
    private int nbWords;

    public Book(String name, int nbLines, int nbWords) {
        this.name = name;
        this.nbLines = nbLines;
        this.nbWords = nbWords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbLines() {
        return nbLines;
    }

    public void setNbLines(int nbLines) {
        this.nbLines = nbLines;
    }

    public int getNbWords() {
        return nbWords;
    }

    public void setNbWords(int nbWords) {
        this.nbWords = nbWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return nbLines == book.nbLines &&
                nbWords == book.nbWords &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nbLines, nbWords);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", nbLines=" + nbLines +
                ", nbWords=" + nbWords +
                '}';
    }
}






