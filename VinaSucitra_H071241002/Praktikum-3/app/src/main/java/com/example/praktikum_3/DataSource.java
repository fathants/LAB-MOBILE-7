package com.example.praktikum_3;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static List<Book> books = new ArrayList<>();

    static {
        // Menambahkan 15 dummy buku Indonesia
        books.add(new Book("1", "Laskar Pelangi", "Andrea Hirata", 2005, "Kisah perjuangan 10 anak di Belitong untuk mengejar pendidikan di tengah kemiskinan.", "Drama", 4.9f, "laskar_pelangi", false));
        books.add(new Book("2", "Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Kisah Minke di akhir abad ke-19 yang memperjuangkan keadilan di zaman kolonial.", "Historical", 5.0f, "bumi_manusia", false));
        books.add(new Book("3", "Filosofi Kopi", "Dee Lestari", 2006, "Kumpulan cerita pendek tentang kopi dan pencarian jati diri manusia.", "Short Story", 4.5f, "filosofi_kopi", false));
        books.add(new Book("4", "Perahu Kertas", "Dee Lestari", 2009, "Kisah cinta antara Kugy dan Keenan yang terhalang oleh ambisi dan takdir.", "Romance", 4.6f, "perahu_kertas", false));
        books.add(new Book("5", "Negeri 5 Menara", "A. Fuadi", 2009, "Perjuangan Alif di pesantren yang memegang prinsip 'Man Jadda Wajada'.", "Inspirational", 4.7f, "negeri_5_menara", false));
        books.add(new Book("6", "Dilan 1990", "Pidi Baiq", 2014, "Kisah romansa masa SMA antara Dilan dan Milea di kota Bandung tahun 90-an.", "Romance", 4.4f, "dilan_1990", false));
        books.add(new Book("7", "Pulang", "Leila S. Chudori", 2012, "Drama sejarah tentang keluarga eksil politik Indonesia pasca tragedi 1965.", "Historical", 4.8f, "pulang", false));
        books.add(new Book("8", "Laut Bercerita", "Leila S. Chudori", 2017, "Mengenang perjuangan mahasiswa yang diculik dan hilang pada tahun 1998.", "Drama", 5.0f, "laut_bercerita", false));
        books.add(new Book("9", "Cantik Itu Luka", "Eka Kurniawan", 2002, "Epos sejarah fiktif yang dibalut realisme magis tentang kecantikan dan kutukan.", "Fantasy", 4.7f, "cantik_itu_luka", false));
        books.add(new Book("10", "Ronggeng Dukuh Paruk", "Ahmad Tohari", 1982, "Kisah Srintil dan Rasus dalam latar tradisi ronggeng di tengah pergolakan politik.", "Classic", 4.8f, "ronggeng_dukuh_paruk", false));
        books.add(new Book("11", "Tenggelamnya Kapal Van der Wijck", "Hamka", 1938, "Tragedi cinta antara Zainuddin, Hayati, dan Aziz karena perbedaan adat Minangkabau.", "Classic", 4.9f, "van_der_wijck", false));
        books.add(new Book("12", "Gadis Kretek", "Ratih Kumala", 2012, "Pencarian cinta masa lalu yang mengungkap sejarah industri rokok kretek di Indonesia.", "Historical", 4.7f, "gadis_kretek", false));
        books.add(new Book("13", "Supernova", "Dee Lestari", 2001, "Eksplorasi sains, spiritualitas, dan cinta dalam balutan narasi fiksi ilmiah.", "Sci-Fi", 4.6f, "supernova", false));
        books.add(new Book("14", "Ayat-Ayat Cinta", "Habiburrahman El Shirazy", 2004, "Kisah cinta relijius Fahri saat menempuh pendidikan tinggi di Al-Azhar Mesir.", "Religious", 4.5f, "ayat_ayat_cinta", false));
        books.add(new Book("15", "Sang Pemimpi", "Andrea Hirata", 2006, "Sekuel Laskar Pelangi yang menceritakan masa SMA Ikal dalam mengejar mimpi ke Eropa.", "Inspirational", 4.8f, "sang_pemimpi", false));
    }

    public static List<Book> getBooks() {
        return books;
    }

    public static void addBook(Book book) {
        books.add(0, book);
    }

    public static void updateBookLikeStatus(String bookId, boolean isLiked) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book.setLiked(isLiked);
                break;
            }
        }
    }
}
