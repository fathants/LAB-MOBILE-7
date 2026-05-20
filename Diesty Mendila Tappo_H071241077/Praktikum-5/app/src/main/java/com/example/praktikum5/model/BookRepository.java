package com.example.praktikum5.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.example.praktikum5.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static BookRepository instance;
    private List<Book> books;
    private Context context;
    private String currentNim = "";

    private static final String BOOK_PREF      = "book_pref";
    private static final String KEY_USER_BOOKS = "user_books_";
    private static final String KEY_LIKED      = "liked_";
    private static final String KEY_RATING     = "rating_";
    private static final String KEY_REVIEW     = "review_";

    private BookRepository() {
        books = new ArrayList<>();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public void init(Context context, String nim) {
        this.context    = context.getApplicationContext();
        this.currentNim = nim;
        books = new ArrayList<>();
        loadDummyBooks();
        loadUserBooks();
        loadUserInteractions();
    }

    public void reset() {
        books.clear();
        currentNim = "";
        context    = null;
        instance   = null;
    }

    private void loadDummyBooks() {
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960,
                "Sebuah kisah tentang ketidakadilan rasial dan kepolosan masa kecil di Amerika Selatan, dilihat melalui mata Scout Finch saat ayahnya membela pria kulit hitam yang dituduh melakukan kejahatan.",
                "Classic Fiction", R.drawable.a));
        books.add(new Book("1984", "George Orwell", 1949,
                "Di masa depan yang distopia, Winston Smith berjuang melawan Partai totaliter yang dipimpin oleh Big Brother.",
                "Dystopian", R.drawable.b));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925,
                "Berlatar di Jazz Age di Long Island, novel ini menggambarkan obsesi Jay Gatsby untuk bersatu kembali dengan cinta lamanya.",
                "Classic Fiction", R.drawable.c));
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997,
                "Seorang anak yatim piatu menemukan bahwa dirinya adalah seorang penyihir dan memulai pendidikannya di Hogwarts.",
                "Fantasy", R.drawable.d));
        books.add(new Book("The Alchemist", "Paulo Coelho", 1988,
                "Seorang gembala muda bernama Santiago melakukan perjalanan dari Spanyol ke Mesir mencari harta karun.",
                "Adventure", R.drawable.e));
        books.add(new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011,
                "Menjangkau seluruh sejarah manusia, buku ini mengeksplorasi bagaimana Homo sapiens datang untuk mendominasi Bumi.",
                "Non-Fiction", R.drawable.f));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937,
                "Bilbo Baggins terseret ke dalam pencarian epik untuk merebut kembali Lonely Mountain dari naga Smaug.",
                "Fantasy", R.drawable.g));
        books.add(new Book("Atomic Habits", "James Clear", 2018,
                "Cara mudah dan terbukti untuk membangun kebiasaan baik dan menghilangkan yang buruk.",
                "Self-Help", R.drawable.h));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951,
                "Remaja Holden Caulfield menceritakan kisah beberapa hari di New York City setelah dikeluarkan dari sekolah.",
                "Coming of Age", R.drawable.i));
        books.add(new Book("Dune", "Frank Herbert", 1965,
                "Berlatar di masa depan yang jauh, kisah epik ini mengikuti Paul Atreides di planet gurun Arrakis.",
                "Science Fiction", R.drawable.j));
        books.add(new Book("The Da Vinci Code", "Dan Brown", 2003,
                "Simbolog Harvard Robert Langdon menyelidiki pembunuhan di Louvre dan menemukan rahasia keagamaan besar.",
                "Thriller", R.drawable.k));
        books.add(new Book("Pride and Prejudice", "Jane Austen", 1813,
                "Elizabeth Bennet menavigasi masalah pernikahan dan kesalahpahaman di Inggris abad ke-19.",
                "Classic Romance", R.drawable.l));
        books.add(new Book("The Hunger Games", "Suzanne Collins", 2008,
                "Katniss Everdeen menjadi sukarelawan dalam Hunger Games, pertarungan mematikan yang disiarkan televisi.",
                "Dystopian", R.drawable.m));
        books.add(new Book("Thinking, Fast and Slow", "Daniel Kahneman", 2011,
                "Mengeksplorasi dua sistem yang mendorong cara kita berpikir dan dampaknya pada keputusan kita.",
                "Psychology", R.drawable.n));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954,
                "Kisah epik tentang Fellowship of the Ring dan misi menghancurkan One Ring sebelum Sauron menguasainya.",
                "Fantasy", R.drawable.o));
        books.add(new Book("Educated", "Tara Westover", 2018,
                "Memoar seorang gadis yang tumbuh tanpa sekolah dan akhirnya meraih PhD dari Universitas Cambridge.",
                "Memoir", R.drawable.p));
        books.add(new Book("The Martian", "Andy Weir", 2011,
                "Astronot terdampar di Mars dan berjuang bertahan hidup dengan kecerdasan dan gelar botaninya.",
                "Science Fiction", R.drawable.q));
    }

    private void loadUserBooks() {
        if (context == null || currentNim.isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_USER_BOOKS + currentNim, "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String uriStr = obj.optString("coverUri", "");
                Uri uri = uriStr.isEmpty() ? null : Uri.parse(uriStr);
                Book book = new Book(
                        obj.getString("title"),
                        obj.getString("author"),
                        obj.getInt("year"),
                        obj.getString("blurb"),
                        obj.getString("genre"),
                        uri
                );
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUserBooks() {
        if (context == null || currentNim.isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE);
        JSONArray arr = new JSONArray();
        for (Book book : books) {
            if (book.hasCustomCover() || book.getCoverResId() == 0) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("title",    book.getTitle());
                    obj.put("author",   book.getAuthor());
                    obj.put("year",     book.getYear());
                    obj.put("blurb",    book.getBlurb());
                    obj.put("genre",    book.getGenre());
                    obj.put("coverUri", book.getCoverUri() != null
                            ? book.getCoverUri().toString() : "");
                    arr.put(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        prefs.edit().putString(KEY_USER_BOOKS + currentNim, arr.toString()).apply();
    }


    private void loadUserInteractions() {
        if (context == null || currentNim.isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE);
        for (Book book : books) {
            String keyLiked  = KEY_LIKED  + currentNim + "_" + book.getId();
            String keyRating = KEY_RATING + currentNim + "_" + book.getId();
            String keyReview = KEY_REVIEW + currentNim + "_" + book.getId();
            boolean liked  = prefs.getBoolean(keyLiked, false);
            float   rating = prefs.getFloat(keyRating, 0f);
            String  review = prefs.getString(keyReview, "");
            if (liked)             book.setLiked(true);
            if (rating > 0f)       book.setUserRating(rating);
            if (!review.isEmpty()) book.setUserReview(review);
        }
    }

    public void saveLike(Book book) {
        if (context == null || currentNim.isEmpty()) return;
        context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_LIKED + currentNim + "_" + book.getId(), book.isLiked())
                .apply();
    }

    public void saveRating(Book book) {
        if (context == null || currentNim.isEmpty()) return;
        context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE)
                .edit()
                .putFloat(KEY_RATING + currentNim + "_" + book.getId(), book.getUserRating())
                .apply();
    }

    public void saveReview(Book book) {
        if (context == null || currentNim.isEmpty()) return;
        context.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_REVIEW + currentNim + "_" + book.getId(), book.getUserReview())
                .apply();
    }

    public void addBook(Book book) {
        books.add(0, book);
        saveUserBooks();
    }

    public List<Book> getAllBooks() {
        List<Book> sorted = new ArrayList<>(books);
        sorted.sort((a, b) -> Long.compare(b.getAddedAt(), a.getAddedAt()));
        return sorted;
    }

    public List<Book> getFavoriteBooks() {
        List<Book> favorites = new ArrayList<>();
        for (Book book : books) {
            if (book.isLiked()) favorites.add(book);
        }
        favorites.sort((a, b) -> Long.compare(b.getLikedAt(), a.getLikedAt()));
        return favorites;
    }

    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        String q = query.toLowerCase().trim();
        for (Book book : getAllBooks()) {
            if (book.getTitle().toLowerCase().contains(q) ||
                    book.getAuthor().toLowerCase().contains(q)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getBooksByGenre(String genre) {
        if (genre.equals("All")) return getAllBooks();
        List<Book> result = new ArrayList<>();
        for (Book book : getAllBooks()) {
            if (book.getGenre().equals(genre)) result.add(book);
        }
        return result;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        for (Book book : books) {
            if (!genres.contains(book.getGenre())) genres.add(book.getGenre());
        }
        return genres;
    }

    public Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public void updateBook(Book updated) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == updated.getId()) {
                books.set(i, updated);
                break;
            }
        }
        saveUserBooks();
    }

    public void deleteBook(int bookId) {
        books.removeIf(b -> b.getId() == bookId);
        saveUserBooks();
    }
}