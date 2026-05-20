package com.example.BukaMata;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static BookRepository instance;
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    private BookRepository() { loadDummyData(); }

    public static BookRepository getInstance() {
        if (instance == null) instance = new BookRepository();
        return instance;
    }

    private void loadDummyData() {
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997,
                "A young boy discovers he's a wizard and attends Hogwarts School of Witchcraft and Wizardry, where he learns about his magical heritage and faces the dark wizard Voldemort.",
                "Fantasy", 4.8f,
                "A timeless classic that captures the magic of childhood and the power of friendship. Rowling's world-building is extraordinary.",
                R.drawable.harrypotter));

        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960,
                "Set in the American South during the 1930s, this novel follows young Scout Finch as her father defends a Black man unjustly accused of a crime.",
                "Classic", 4.7f,
                "A powerful exploration of racial injustice and moral growth. Lee's prose is both simple and deeply moving.",
                R.drawable.tokilla));

        books.add(new Book("1984", "George Orwell", 1949,
                "In a dystopian future, Winston Smith lives under the oppressive rule of Big Brother and the Party, which controls all aspects of life including thought.",
                "Dystopia", 4.7f,
                "A chilling and prophetic novel that remains strikingly relevant. Orwell's vision of a totalitarian state is hauntingly prescient.",
                R.drawable.matalahmu));

        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925,
                "Nick Carraway narrates the story of mysterious millionaire Jay Gatsby and his obsession with the beautiful Daisy Buchanan in Jazz Age New York.",
                "Classic", 4.2f,
                "A beautifully written critique of the American Dream. Fitzgerald's lyrical prose creates an atmosphere of melancholy and longing.",
                R.drawable.thegreatgatsby));

        books.add(new Book("Pride and Prejudice", "Jane Austen", 1813,
                "Elizabeth Bennet navigates issues of marriage, morality, and misconceptions in 19th century England, eventually finding love with the proud Mr. Darcy.",
                "Romance", 4.6f,
                "Austen's wit and social commentary shine brilliantly. Elizabeth Bennet remains one of literature's most beloved heroines.",
                R.drawable.prideandprejudice));

        books.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937,
                "Bilbo Baggins, a comfortable hobbit, is swept into an epic quest to reclaim a dwarf kingdom from the dragon Smaug, guided by the wizard Gandalf.",
                "Fantasy", 4.7f,
                "A perfect adventure story that serves as a gateway to Tolkien's rich Middle-earth. Whimsical yet full of depth and heart.",
                R.drawable.thehobbit));

        books.add(new Book("The Alchemist", "Paulo Coelho", 1988,
                "Santiago, an Andalusian shepherd boy, travels from Spain to the Egyptian desert in search of a treasure, discovering along the way that the real treasure lies within.",
                "Philosophy", 4.3f,
                "An inspiring fable about following your dreams. Coelho's simple yet profound writing resonates with readers worldwide.",
                R.drawable.thealchemist));

        books.add(new Book("Brave New World", "Aldous Huxley", 1932,
                "In a future society where humans are genetically engineered and conditioned for happiness, Bernard Marx begins to question the perfection of his world.",
                "Dystopia", 4.4f,
                "A fascinating and disturbing vision of a pleasure-controlled society. Huxley's satire feels increasingly relevant in our modern world.",
                R.drawable.bravenewworld));

        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951,
                "Teenage Holden Caulfield wanders New York City after being expelled from prep school, railing against the phoniness of the adult world.",
                "Coming of Age", 4.0f,
                "Salinger captures teenage alienation with remarkable authenticity. A controversial but deeply resonant portrait of adolescent disillusionment.",
                R.drawable.tity));

        books.add(new Book("Lord of the Flies", "William Golding", 1954,
                "A group of British boys stranded on an uninhabited island attempt to govern themselves but descend into chaos and savagery.",
                "Classic", 4.2f,
                "A dark and compelling examination of human nature. Golding's allegory about civilization versus savagery is both gripping and disturbing.",
                R.drawable.lordoftheflies));

        books.add(new Book("The Da Vinci Code", "Dan Brown", 2003,
                "Harvard symbologist Robert Langdon investigates a murder in the Louvre and uncovers a hidden society protecting a secret about Jesus Christ.",
                "Thriller", 4.1f,
                "An incredibly fast-paced and gripping thriller. Brown masterfully blends art history, religion, and mystery into an addictive read.",
                R.drawable.davincicode));

        books.add(new Book("Dune", "Frank Herbert", 1965,
                "On the desert planet Arrakis, young Paul Atreides navigates political intrigue, religious prophecy, and ecological warfare in a battle for the universe's most valuable resource.",
                "Sci-Fi", 4.6f,
                "The greatest science fiction novel ever written. Herbert's world-building is unmatched, creating a fully realized universe with incredible depth.",
                R.drawable.dune));

        books.add(new Book("Atomic Habits", "James Clear", 2018,
                "An easy and proven way to build good habits and break bad ones, based on the science of human behavior and the power of small incremental changes.",
                "Self-Help", 4.6f,
                "Practical, actionable, and backed by science. Clear's framework for building habits has genuinely helped millions of people transform their lives.",
                R.drawable.atomichabits));

        books.add(new Book("The Midnight Library", "Matt Haig", 2020,
                "Nora Seed finds a library between life and death containing books of all the lives she could have lived, forcing her to confront her regrets and desires.",
                "Fiction", 4.4f,
                "A beautifully moving exploration of regret, hope, and the beauty of living. Haig writes with warmth and profound understanding of human experience.",
                R.drawable.themidnightlibrary));

        books.add(new Book("Project Hail Mary", "Andy Weir", 2021,
                "A lone astronaut wakes up with no memory on a spacecraft millions of miles from Earth and must figure out how to save humanity from extinction.",
                "Sci-Fi", 4.9f,
                "Possibly the most fun science fiction novel in years. Weir's hard science is impressive, but it's the heart of the story that makes it unforgettable.",
                R.drawable.hailmarry));

        books.add(new Book("The Psychology of Money", "Morgan Housel", 2020,
                "Timeless lessons on wealth, greed, and happiness, exploring the strange ways people think about money and how to make better financial decisions.",
                "Non-Fiction", 4.5f,
                "Insightful and accessible. Housel reveals how our relationship with money is shaped by psychology, not just numbers. A must-read for everyone.",
                R.drawable.pys));
    }

    public List<Book> getAllBooks() {
        List<Book> reversed = new ArrayList<>();
        for (int i = books.size() - 1; i >= 0; i--) {
            reversed.add(books.get(i));
        }
        return reversed;
    }

    public List<Book> getFavoriteBooks() {
        List<Book> favorites = new ArrayList<>();
        for (Book book : books) {
            if (book.isLiked()) favorites.add(book);
        }
        return favorites;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public List<String> getGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        for (Book book : books) {
            if (!genres.contains(book.getGenre())) {
                genres.add(book.getGenre());
            }
        }
        return genres;
    }
}
