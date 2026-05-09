package com.example.Tuprak4LAB;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Book> books = new ArrayList<>();

    public static void initDummyData() {
        if (books.isEmpty()) {
            books.add(new Book("1", "Laskar Pelangi", "Andrea Hirata", "2005", "Kisah perjuangan 10 anak di Belitung.", R.drawable.laskarpelangi, 4.8f, "Novel"));
            books.add(new Book("2", "Bumi", "Tere Liye", "2014", "Petualangan Raib di dunia paralel.", R.drawable.bumi, 4.8f, "Fantasi"));
            books.add(new Book("3", "Laut Bercerita", "Leila S. Chudori", "2017", "Kisah aktivis yang dihilangkan.", R.drawable.lautbercerita, 4.9f, "Fiksi Sejarah"));
            books.add(new Book("4", "Dilan 1990", "Pidi Baiq", "2014", "Kisah romansa masa SMA.", R.drawable.dilan, 4.5f, "Romansa"));
            books.add(new Book("5", "Dear J", "Luluk HF", "2018", "Kisah Rayyan dan gadis bernama Naira.", R.drawable.dearj, 4.6f, "Romansa"));
            books.add(new Book("6", "Dikta & Hukum", "Dhia'an Farah", "2021", "Kisah hukum dan cinta Dikta dan Nadhira.", R.drawable.diktadanhukum, 4.7f, "Romansa"));
            books.add(new Book("7", "Mariposa", "Luluk HF", "2018", "Perjuangan Acha mengejar hati Iqbal.", R.drawable.mariposa, 4.8f, "Romansa"));
            books.add(new Book("8", "00.00", "Ameylia Falensia", "2021", "Perjuangan hidup dan mental Lengkara.", R.drawable.nolnolnolnol, 4.5f, "Drama"));
            books.add(new Book("9", "Malioboro at Midnight", "Skysphire", "2023", "Kisah romansa manis Sera dan Malioboro.", R.drawable.malioboroatmidnight, 4.8f, "Romansa"));
            books.add(new Book("10", "Teluk Alaska", "Eka Aryani", "2019", "Kisah Ana dan Alister yang bertolak belakang.", R.drawable.telukalaska, 4.6f, "Romansa"));
            books.add(new Book("11", "Azzamine", "Sophie Aulia", "2022", "Perjodohan manis Jasmine dan Azzam.", R.drawable.azzamine, 4.8f, "Romansa"));
            books.add(new Book("12", "Antares", "Rweinda", "2020", "Kisah ketua geng motor Antares dan Zea.", R.drawable.antares, 4.7f, "Romansa"));
            books.add(new Book("13", "Geez & Ann", "Nadhifa Allya Tsana", "2017", "Kisah komitmen dan cinta Geez dan Ann.", R.drawable.geezdanann, 4.7f, "Romansa"));
            books.add(new Book("14", "Septihan", "Poppi Pertiwi", "2020", "Kisah Septian yang pendiam dan Jihan.", R.drawable.septihan, 4.7f, "Romansa"));
            books.add(new Book("15", "Argantara", "Falistiyana", "2021", "Perjodohan usia muda Arga dan Syera.", R.drawable.argantara, 4.6f, "Romansa"));
        }
    }
}