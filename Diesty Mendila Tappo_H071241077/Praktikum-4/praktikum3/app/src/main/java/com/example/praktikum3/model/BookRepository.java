package com.example.praktikum3.model;

import com.example.praktikum3.R;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static BookRepository instance;
    private List<Book> books;

    private BookRepository() {
        books = new ArrayList<>();
        loadDummyBooks();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private void loadDummyBooks() {
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960,
            "Sebuah kisah tentang ketidakadilan rasial dan kepolosan masa kecil di Amerika Selatan, dilihat melalui mata Scout Finch saat ayahnya membela pria kulit hitam yang dituduh melakukan kejahatan.",
            "Classic Fiction", R.drawable.a));

        books.add(new Book("1984", "George Orwell", 1949,
            "Di masa depan yang distopia, Winston Smith berjuang melawan Partai totaliter yang dipimpin oleh Big Brother. Peringatan mengerikan tentang pengawasan, propaganda, dan penghancuran kebenaran.",
            "Dystopian", R.drawable.b));

        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925,
            "Berlatar di Jazz Age di Long Island, novel ini menggambarkan interaksi Nick Carraway dengan jutawan misterius Jay Gatsby dan obsesi Gatsby untuk bersatu kembali dengan cinta lamanya.",
            "Classic Fiction", R.drawable.c));

        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997,
            "Seorang anak yatim piatu menemukan bahwa dirinya adalah seorang penyihir dan memulai pendidikannya di Sekolah Sihir Hogwarts, di mana ia berteman dan menghadapi penyihir gelap yang membunuh orang tuanya.",
            "Fantasy", R.drawable.d));

        books.add(new Book("The Alchemist", "Paulo Coelho", 1988,
            "Seorang gembala muda bernama Santiago melakukan perjalanan dari Spanyol ke Mesir mencari harta karun. Di sepanjang jalan ia bertemu banyak orang menarik dan menemukan rahasia 'Jiwa Dunia'.",
            "Adventure", R.drawable.e));

        books.add(new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011,
            "Menjangkau seluruh sejarah manusia, buku ini mengeksplorasi bagaimana Homo sapiens datang untuk mendominasi Bumi dan menelusuri cara biologi, ekonomi, dan budaya membentuk spesies kita.",
            "Non-Fiction", R.drawable.f));

        books.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937,
            "Bilbo Baggins, seorang hobbit yang nyaman, terseret ke dalam pencarian epik untuk merebut kembali Lonely Mountain dari naga Smaug, ditemani tiga belas kurcaci dan penyihir Gandalf.",
            "Fantasy", R.drawable.g));

        books.add(new Book("Atomic Habits", "James Clear", 2018,
            "Cara mudah dan terbukti untuk membangun kebiasaan baik dan menghilangkan yang buruk. James Clear menyajikan kerangka kerja untuk memperbaiki rutinitas harian melalui perubahan kecil.",
            "Self-Help", R.drawable.h));

        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951,
            "Remaja Holden Caulfield menceritakan kisah beberapa hari di New York City setelah dikeluarkan dari sekolah persiapan, bergulat dengan tema identitas, keterasingan, dan kepalsuan dunia dewasa.",
            "Coming of Age", R.drawable.i));

        books.add(new Book("Dune", "Frank Herbert", 1965,
            "Berlatar di masa depan yang jauh, kisah epik ini mengikuti Paul Atreides ketika keluarganya menerima pengawasan planet gurun Arrakis — satu-satunya sumber zat paling berharga di alam semesta.",
            "Science Fiction", R.drawable.j));

        books.add(new Book("The Da Vinci Code", "Dan Brown", 2003,
            "Simbolog Harvard Robert Langdon menyelidiki pembunuhan di museum Louvre dan menemukan pertempuran antara Priory of Sion dan Opus Dei atas rahasia keagamaan.",
            "Thriller", R.drawable.k));

        books.add(new Book("Pride and Prejudice", "Jane Austen", 1813,
            "Kisah ini mengikuti keluarga Bennet, terutama Elizabeth Bennet, saat ia menavigasi masalah pernikahan, moralitas, dan kesalahpahaman di Inggris abad ke-19.",
            "Classic Romance", R.drawable.l));

        books.add(new Book("The Hunger Games", "Suzanne Collins", 2008,
            "Di masa depan yang distopia, remaja Katniss Everdeen menjadi sukarelawan menggantikan adiknya dalam Hunger Games, pertarungan mematikan yang disiarkan televisi.",
            "Dystopian", R.drawable.m));

        books.add(new Book("Thinking, Fast and Slow", "Daniel Kahneman", 2011,
            "Pemenang Nobel Daniel Kahneman mengeksplorasi dua sistem yang mendorong cara kita berpikir — Sistem 1 yang cepat dan intuitif, dan Sistem 2 yang lebih lambat — dan dampaknya pada keputusan kita.",
            "Psychology", R.drawable.n));

        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954,
            "Kisah epik tentang Fellowship of the Ring dan misi mereka untuk menghancurkan One Ring sebelum Dark Lord Sauron menggunakannya untuk memperbudak seluruh Middle-earth.",
            "Fantasy", R.drawable.o));

        books.add(new Book("Educated", "Tara Westover", 2018,
            "Memoar tentang seorang gadis yang tumbuh dalam keluarga survivalis di Idaho pedesaan, tidak pernah bersekolah, dan perjalanannya untuk akhirnya meraih gelar PhD dari Universitas Cambridge.",
            "Memoir", R.drawable.p));

        books.add(new Book("The Martian", "Andy Weir", 2011,
            "Seorang astronot terdampar di Mars setelah krunya meninggalkannya karena dikira sudah mati. Dengan kecerdasan dan gelar botani, ia harus mencari cara untuk bertahan di planet merah yang hostile.",
            "Science Fiction", R.drawable.q));
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

    public void addBook(Book book) {
        books.add(0, book);
    }

    public Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }
}
