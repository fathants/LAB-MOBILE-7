package com.example.tuprak4;

import java.util.ArrayList;

public class DataDummy {
    private static ArrayList<BookModel> bookList;

    public static ArrayList<BookModel> getBooks() {
        if (bookList == null) {
            bookList = new ArrayList<>();
            bookList.add(new BookModel("Atomic Habits", "James Clear", 2018, "A book about building good habits", "https://picsum.photos/200?random=1", false));
            bookList.add(new BookModel("Thinking, Fast and Slow", "Daniel Kahneman", 2011, "Explores the two systems that drive the way we think", "https://picsum.photos/200?random=2", false));
            bookList.add(new BookModel("The Lean Startup", "Eric Ries", 2011, "How Today's Entrepreneurs Use Continuous Innovation to Create Radically Successful Businesses", "https://picsum.photos/200?random=3", false));
            bookList.add(new BookModel("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011, "A brief history of humankind from the Stone Age to the twenty-first century.", "https://picsum.photos/200?random=4", false));
            bookList.add(new BookModel("Principles: Life and Work", "Ray Dalio", 2017, "Ray Dalio shares the unconventional principles that he's developed.", "https://picsum.photos/200?random=5", false));
            bookList.add(new BookModel("Zero to One", "Peter Thiel", 2014, "Notes on Startups, or How to Build the Future", "https://picsum.photos/200?random=6", false));
            bookList.add(new BookModel("Deep Work", "Cal Newport", 2016, "Rules for Focused Success in a Distracted World", "https://picsum.photos/200?random=7", false));
            bookList.add(new BookModel("Hooked", "Nir Eyal", 2014, "How to Build Habit-Forming Products", "https://picsum.photos/200?random=8", false));
            bookList.add(new BookModel("Thinking in Systems", "Donella H. Meadows", 2008, "A Primer on systems thinking.", "https://picsum.photos/200?random=9", false));
            bookList.add(new BookModel("Good to Great", "Jim Collins", 2001, "Why Some Companies Make the Leap...And Others Don't", "https://picsum.photos/200?random=10", false));
            bookList.add(new BookModel("The Alchemist", "Paulo Coelho", 1988, "A masterwork combining magic, mysticism, wisdom and wonder.", "https://picsum.photos/200?random=11", false));
            bookList.add(new BookModel("Educated", "Tara Westover", 2018, "A Memoir about a young girl who, kept out of school, leaves her survivalist family and goes on to earn a PhD from Cambridge University.", "https://picsum.photos/200?random=12", false));
            bookList.add(new BookModel("The Four Agreements", "Don Miguel Ruiz", 1997, "A Practical Guide to Personal Freedom.", "https://picsum.photos/200?random=13", false));
            bookList.add(new BookModel("Born a Crime", "Trevor Noah", 2016, "Stories from a South African Childhood.", "https://picsum.photos/200?random=14", false));
            bookList.add(new BookModel("Grit", "Angela Duckworth", 2016, "The Power of Passion and Perseverance.", "https://picsum.photos/200?random=15", false));
        }
        return bookList;
    }
}
