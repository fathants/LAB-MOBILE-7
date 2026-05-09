package com.example.Tuprak2LAB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    public static ArrayList<ModelPost> homeFeedList    = new ArrayList<>();
    public static ArrayList<ModelPost> profileFeedList = new ArrayList<>();
    public static ArrayList<ModelPost> highlightList   = new ArrayList<>();

    public static Map<String, ArrayList<ModelPost>> userHighlightMap = new HashMap<>();

    // ── Highlight akun sendiri (7 item) ──────────────────────────────────────
    public static void initHighlightIfEmpty() {
        if (!highlightList.isEmpty()) return;
        String[] names  = {"EXO", "Me", "Friend", "NCT", "Him", "Sunset", "OOTD"};
        int[]    images = {
                R.drawable.exo,    R.drawable.profil, R.drawable.a5,
                R.drawable.nctdream, R.drawable.him,  R.drawable.sunset,
                R.drawable.a6
        };
        for (int i = 0; i < names.length; i++)
            highlightList.add(new ModelPost(names[i], 0, images[i], ""));
    }

    // ── Highlight per user (masing-masing 7 item, foto unik) ─────────────────
    public static void initUserHighlightsIfEmpty() {
        if (!userHighlightMap.isEmpty()) return;

        // Jaemin Highlight
        ArrayList<ModelPost> jaeminHl = new ArrayList<>();
        jaeminHl.add(new ModelPost("NCT", 0, R.drawable.jaemin10, ""));
        jaeminHl.add(new ModelPost("NCT DREAM", 0, R.drawable.jaemin8, ""));
        jaeminHl.add(new ModelPost("NCTZEN", 0, R.drawable.jaemin9, ""));
        jaeminHl.add(new ModelPost("ME", 0, R.drawable.jaemin1, ""));
        jaeminHl.add(new ModelPost("PHOTOSHOOT", 0, R.drawable.jaemin6, ""));
        jaeminHl.add(new ModelPost("MIRROR SELFIE", 0, R.drawable.jaemin7, ""));
        jaeminHl.add(new ModelPost("POTRAIT", 0, R.drawable.jaemin5, ""));
        userHighlightMap.put("na.jaemin0813", jaeminHl);

        // Chanyeol Highlight
        ArrayList<ModelPost> cyHl = new ArrayList<>();
        cyHl.add(new ModelPost("EXO OT9", 0, R.drawable.cy8,  ""));
        cyHl.add(new ModelPost("EXO OT12", 0, R.drawable.cy9,  ""));
        cyHl.add(new ModelPost("PHOTOSHOOT", 0, R.drawable.cy7,  ""));
        cyHl.add(new ModelPost("EXO-L", 0, R.drawable.cy10,  ""));
        cyHl.add(new ModelPost("MUSIC", 0, R.drawable.cy4, ""));
        cyHl.add(new ModelPost("Random", 0, R.drawable.cy5, ""));
        cyHl.add(new ModelPost("X-Mas", 0, R.drawable.cy6,   ""));
        userHighlightMap.put("real__pcy", cyHl);

        // Yeri aesthetic
        ArrayList<ModelPost> yeriHl = new ArrayList<>();
        yeriHl.add(new ModelPost("soft", 0, R.drawable.yeri1, ""));
        yeriHl.add(new ModelPost("daily", 0, R.drawable.yeri2, ""));
        yeriHl.add(new ModelPost("mood", 0, R.drawable.yeri3, ""));
        yeriHl.add(new ModelPost("vibes", 0, R.drawable.yeri4, ""));
        yeriHl.add(new ModelPost("random", 0, R.drawable.yeri5, ""));
        yeriHl.add(new ModelPost("self", 0, R.drawable.yeri6, ""));
        yeriHl.add(new ModelPost("night", 0, R.drawable.yeri7, ""));
        userHighlightMap.put("peachy.yeri", yeriHl);

        // Carmen
        ArrayList<ModelPost> carmenHl = new ArrayList<>();
        carmenHl.add(new ModelPost("story", 0, R.drawable.carmen1, ""));
        carmenHl.add(new ModelPost("life", 0, R.drawable.carmen2, ""));
        carmenHl.add(new ModelPost("daily", 0, R.drawable.carmen5, ""));
        carmenHl.add(new ModelPost("food", 0, R.drawable.carmen4, ""));
        carmenHl.add(new ModelPost("self", 0, R.drawable.carmen3, ""));
        carmenHl.add(new ModelPost("random", 0, R.drawable.carmen6, ""));
        carmenHl.add(new ModelPost("night", 0, R.drawable.carmen7, ""));
        userHighlightMap.put("carmen.official", carmenHl);

        // Jisoo
        ArrayList<ModelPost> jisooHl = new ArrayList<>();
        jisooHl.add(new ModelPost("pink", 0, R.drawable.jisoo1, ""));
        jisooHl.add(new ModelPost("stage", 0, R.drawable.jisoo2, ""));
        jisooHl.add(new ModelPost("idol", 0, R.drawable.jisoo3, ""));
        jisooHl.add(new ModelPost("daily", 0, R.drawable.jisoo4, ""));
        jisooHl.add(new ModelPost("cute", 0, R.drawable.jisoo5, ""));
        jisooHl.add(new ModelPost("selfie", 0, R.drawable.jisoo6, ""));
        jisooHl.add(new ModelPost("mood", 0, R.drawable.jisoo7, ""));
        userHighlightMap.put("sooyaaa__", jisooHl);

        // Go Youn Jung
        ArrayList<ModelPost> gyjHl = new ArrayList<>();
        gyjHl.add(new ModelPost("film", 0, R.drawable.goyounjung1, ""));
        gyjHl.add(new ModelPost("shoot", 0, R.drawable.goyounjung2, ""));
        gyjHl.add(new ModelPost("drama", 0, R.drawable.goyounjung3, ""));
        gyjHl.add(new ModelPost("daily", 0, R.drawable.goyounjung4, ""));
        gyjHl.add(new ModelPost("self", 0, R.drawable.goyounjung5, ""));
        gyjHl.add(new ModelPost("style", 0, R.drawable.goyounjung6, ""));
        gyjHl.add(new ModelPost("night", 0, R.drawable.goyounjung7, ""));
        userHighlightMap.put("goyounjung", gyjHl);

        // Byeon Woo Seok
        ArrayList<ModelPost> bwsHl = new ArrayList<>();
        bwsHl.add(new ModelPost("actor", 0, R.drawable.byeonsook1, ""));
        bwsHl.add(new ModelPost("shoot", 0, R.drawable.byeonsook2, ""));
        bwsHl.add(new ModelPost("style", 0, R.drawable.byeonsook3, ""));
        bwsHl.add(new ModelPost("daily", 0, R.drawable.byeonsook4, ""));
        bwsHl.add(new ModelPost("self", 0, R.drawable.byeonsook5, ""));
        bwsHl.add(new ModelPost("random", 0, R.drawable.byeonsook6, ""));
        bwsHl.add(new ModelPost("night", 0, R.drawable.byeonsook7, ""));
        userHighlightMap.put("byeonwooseok", bwsHl);

        // Song Kang
        ArrayList<ModelPost> skHl = new ArrayList<>();
        skHl.add(new ModelPost("drama", 0, R.drawable.songkang1, ""));
        skHl.add(new ModelPost("shoot", 0, R.drawable.songkang2, ""));
        skHl.add(new ModelPost("style", 0, R.drawable.songkang3, ""));
        skHl.add(new ModelPost("daily", 0, R.drawable.songkang4, ""));
        skHl.add(new ModelPost("self", 0, R.drawable.songkang5, ""));
        skHl.add(new ModelPost("mood", 0, R.drawable.songkang6, ""));
        skHl.add(new ModelPost("night", 0, R.drawable.songkang7, ""));
        userHighlightMap.put("songkang_b", skHl);

        // Hwang Minhyun
        ArrayList<ModelPost> mhHl = new ArrayList<>();
        mhHl.add(new ModelPost("idol", 0, R.drawable.minhyun1, ""));
        mhHl.add(new ModelPost("stage", 0, R.drawable.minhyun2, ""));
        mhHl.add(new ModelPost("daily", 0, R.drawable.minhyun3, ""));
        mhHl.add(new ModelPost("style", 0, R.drawable.minhyun4, ""));
        mhHl.add(new ModelPost("self", 0, R.drawable.minhyun5, ""));
        mhHl.add(new ModelPost("mood", 0, R.drawable.minhyun6, ""));
        mhHl.add(new ModelPost("night", 0, R.drawable.minhyun7, ""));
        userHighlightMap.put("optimushwang", mhHl);

        // Cho Yi Hyun
        ArrayList<ModelPost> cyhHl = new ArrayList<>();
        cyhHl.add(new ModelPost("drama", 0, R.drawable.yihyun1, ""));
        cyhHl.add(new ModelPost("shoot", 0, R.drawable.yihyun2, ""));
        cyhHl.add(new ModelPost("daily", 0, R.drawable.yihyun3, ""));
        cyhHl.add(new ModelPost("style", 0, R.drawable.yihyun4, ""));
        cyhHl.add(new ModelPost("self", 0, R.drawable.yihyun5, ""));
        cyhHl.add(new ModelPost("mood", 0, R.drawable.yihyun6, ""));
        cyhHl.add(new ModelPost("doggie ", 0, R.drawable.yihyun7, ""));
        userHighlightMap.put("choyihyun", cyhHl);
    }

    public static Map<String, ArrayList<ModelPost>> userFeedMap = new HashMap<>();

    // ── Feed per user (masing-masing 5 postingan unik) ────────────────────────
    public static void initUserFeedsIfEmpty() {
        if (!userFeedMap.isEmpty()) return;

        // nct_jaemin
        ArrayList<ModelPost> jaeminFeed = new ArrayList<>();
        jaeminFeed.add(new ModelPost("nct_jaemin", "Na Jaemin", R.drawable.foto_profil1, R.drawable.feedjaemin1, "Bersandar pada ikon merah✨"));
        jaeminFeed.add(new ModelPost("nct_jaemin", "Na Jaemin", R.drawable.foto_profil1, R.drawable.feedjaemin2, "Di bawah lampu London q"));
        jaeminFeed.add(new ModelPost("nct_jaemin", "Na Jaemin", R.drawable.foto_profil1, R.drawable.feedjaemin3, "Tampil rapi dengan blazer warna sage"));
        jaeminFeed.add(new ModelPost("nct_jaemin", "Na Jaemin", R.drawable.foto_profil1, R.drawable.feedjaemin4, "Fokus belajar"));
        jaeminFeed.add(new ModelPost("nct_jaemin", "Na Jaemin", R.drawable.foto_profil1, R.drawable.feedjaemin5, "Menangkap momen dengan kaos kucing"));
        userFeedMap.put("nct_jaemin", jaeminFeed);

        // real__pcy
        ArrayList<ModelPost> cyFeed = new ArrayList<>();
        cyFeed.add(new ModelPost("real__pcy", "Chanyeol", R.drawable.foto_profil2, R.drawable.feedcy1, "Gym selfie."));
        cyFeed.add(new ModelPost("real__pcy", "Chanyeol", R.drawable.foto_profil2, R.drawable.feedcy2, "Siap terbang"));
        cyFeed.add(new ModelPost("real__pcy", "Chanyeol", R.drawable.foto_profil2, R.drawable.feedcy3, "Hang loose"));
        cyFeed.add(new ModelPost("real__pcy", "Chanyeol", R.drawable.foto_profil2, R.drawable.feedcy4, "Beach vibes"));
        cyFeed.add(new ModelPost("real__pcy", "Chanyeol", R.drawable.foto_profil2, R.drawable.feedcy5, "On top of the world"));
        userFeedMap.put("real__pcy", cyFeed);

        // Yeri
        ArrayList<ModelPost> yeriFeed = new ArrayList<>();
        yeriFeed.add(new ModelPost("peachy.yeri", "Yeri", R.drawable.foto_profil3, R.drawable.feedyeri1, "Outfit check ✅. Suka banget sama dress baruku!"));
        yeriFeed.add(new ModelPost("peachy.yeri", "Yeri", R.drawable.foto_profil3, R.drawable.feedyeri2, "Santai sore dulu."));
        yeriFeed.add(new ModelPost("peachy.yeri", "Yeri", R.drawable.foto_profil3, R.drawable.feedyeri3, "Selfie tipis-tipis."));
        yeriFeed.add(new ModelPost("peachy.yeri", "Yeri", R.drawable.foto_profil3, R.drawable.feedyeri4, "Selamat pagi! Semangat buat hari ini."));
        yeriFeed.add(new ModelPost("peachy.yeri", "Yeri", R.drawable.foto_profil3, R.drawable.feedyeri5, "Lagi mikirin apa ya?"));
        userFeedMap.put("peachy.yeri", yeriFeed);

        // Carmen
        ArrayList<ModelPost> carmenFeed = new ArrayList<>();
        carmenFeed.add(new ModelPost("carmen.official", "Carmen", R.drawable.foto_profil4, R.drawable.feedcarmen1, "Outfit of the day."));
        carmenFeed.add(new ModelPost("carmen.official", "Carmen", R.drawable.foto_profil4, R.drawable.feedcarmen2, "Selamat pagi!"));
        carmenFeed.add(new ModelPost("carmen.official", "Carmen", R.drawable.foto_profil4, R.drawable.feedcarmen3, "Selfie."));
        carmenFeed.add(new ModelPost("carmen.official", "Carmen", R.drawable.foto_profil4, R.drawable.feedcarmen4, "jametss dulss"));
        carmenFeed.add(new ModelPost("carmen.official", "Carmen", R.drawable.foto_profil4, R.drawable.feedcarmen5, "Lagi mikirin apa ya?"));
        userFeedMap.put("carmen.official", carmenFeed);

        // Jisoo
        ArrayList<ModelPost> jisooFeed = new ArrayList<>();
        jisooFeed.add(new ModelPost("sooyaaa__", "Jisoo", R.drawable.foto_profil5, R.drawable.feedjisoo1, "BLACKPINK 💕"));
        jisooFeed.add(new ModelPost("sooyaaa__", "Jisoo", R.drawable.foto_profil5, R.drawable.feedjisoo2, "Tebak film yuks?"));
        jisooFeed.add(new ModelPost("sooyaaa__", "Jisoo", R.drawable.foto_profil5, R.drawable.feedjisoo3, "Fotografer eak."));
        jisooFeed.add(new ModelPost("sooyaaa__", "Jisoo", R.drawable.foto_profil5, R.drawable.feedjisoo4, "Bagus ga?"));
        jisooFeed.add(new ModelPost("sooyaaa__", "Jisoo", R.drawable.foto_profil5, R.drawable.feedjisoo5, "Pose gemes dulu."));
        userFeedMap.put("sooyaaa__", jisooFeed);

        // Go Youn Jung
        ArrayList<ModelPost> gyjFeed = new ArrayList<>();
        gyjFeed.add(new ModelPost("goyounjung", "Go Youn Jung", R.drawable.foto_profil6, R.drawable.feedgyj1, "tas menarik dari cemilan favku"));
        gyjFeed.add(new ModelPost("goyounjung", "Go Youn Jung", R.drawable.foto_profil6, R.drawable.feedgyj2, "wleee"));
        gyjFeed.add(new ModelPost("goyounjung", "Go Youn Jung", R.drawable.foto_profil6, R.drawable.feedgyj3, "pov: kamu single tapi suka nonton orang bucin"));
        gyjFeed.add(new ModelPost("goyounjung", "Go Youn Jung", R.drawable.foto_profil6, R.drawable.feedgyj4, "wkwkwkwk"));
        gyjFeed.add(new ModelPost("goyounjung", "Go Youn Jung", R.drawable.foto_profil6, R.drawable.feedgyj5, "p, balap"));
        userFeedMap.put("goyounjung", gyjFeed);

        // Byeon Woo Seok
        ArrayList<ModelPost> bwsFeed = new ArrayList<>();
        bwsFeed.add(new ModelPost("byeonwooseok", "Byeon Woo Seok", R.drawable.foto_profil7, R.drawable.feedbws1, "bareng ayang"));
        bwsFeed.add(new ModelPost("byeonwooseok", "Byeon Woo Seok", R.drawable.foto_profil7, R.drawable.feedbws2, "hati-hati awas jatuh"));
        bwsFeed.add(new ModelPost("byeonwooseok", "Byeon Woo Seok", R.drawable.foto_profil7, R.drawable.feedbws3, "masih cocok pakai baju sekolah kan?"));
        bwsFeed.add(new ModelPost("byeonwooseok", "Byeon Woo Seok", R.drawable.foto_profil7, R.drawable.feedbws4, "terbang kemana kita?"));
        bwsFeed.add(new ModelPost("byeonwooseok", "Byeon Woo Seok", R.drawable.foto_profil7, R.drawable.feedbws5, "outfit kece gue"));
        userFeedMap.put("byeonwooseok", bwsFeed);


        // Song Kang
        ArrayList<ModelPost> skFeed = new ArrayList<>();
        skFeed.add(new ModelPost("songkang_b", "Song Kang", R.drawable.foto_profil8, R.drawable.feedsk1, "nyamannn"));
        skFeed.add(new ModelPost("songkang_b", "Song Kang", R.drawable.foto_profil8, R.drawable.feedsk2, "kalian bisa ga? haha"));
        skFeed.add(new ModelPost("songkang_b", "Song Kang", R.drawable.foto_profil8, R.drawable.feedsk3, "jangan lupa minum susu biar sehat"));
        skFeed.add(new ModelPost("songkang_b", "Song Kang", R.drawable.foto_profil8, R.drawable.feedsk4, "ngemil apa ya ini?"));
        skFeed.add(new ModelPost("songkang_b", "Song Kang", R.drawable.foto_profil8, R.drawable.feedsk5, "lapar brokkk"));
        userFeedMap.put("songkang_b", skFeed);

        // Hwang Minhyun
        ArrayList<ModelPost> mhFeed = new ArrayList<>();
        mhFeed.add(new ModelPost("optimushwang", "Hwang Minhyun", R.drawable.foto_profil9, R.drawable.feedmh1, "mau buat lagu jadi baca script dulu"));
        mhFeed.add(new ModelPost("optimushwang", "Hwang Minhyun", R.drawable.foto_profil9, R.drawable.feedmh2, "hoho saya bapak-bapak"));
        mhFeed.add(new ModelPost("optimushwang", "Hwang Minhyun", R.drawable.foto_profil9, R.drawable.feedmh3, "tebak kekuatan saya?"));
        mhFeed.add(new ModelPost("optimushwang", "Hwang Minhyun", R.drawable.foto_profil9, R.drawable.feedmh4, "sky dulu"));
        mhFeed.add(new ModelPost("optimushwang", "Hwang Minhyun", R.drawable.foto_profil9, R.drawable.feedmh5, "mau di masakin apa sayang?"));
        userFeedMap.put("optimushwang", mhFeed);


        // Cho Yi Hyun
        ArrayList<ModelPost> cyhFeed = new ArrayList<>();
        cyhFeed.add(new ModelPost("choyihyun", "Cho Yi Hyun", R.drawable.foto_profil10, R.drawable.feedcyh1, "pantai dulu🌸"));
        cyhFeed.add(new ModelPost("choyihyun", "Cho Yi Hyun", R.drawable.foto_profil10, R.drawable.feedcyh2, "nom nom minum segerr"));
        cyhFeed.add(new ModelPost("choyihyun", "Cho Yi Hyun", R.drawable.foto_profil10, R.drawable.feedcyh3, "cantik kan?"));
        cyhFeed.add(new ModelPost("choyihyun", "Cho Yi Hyun", R.drawable.foto_profil10, R.drawable.feedcyh4, "bayikkk favoritekuu"));
        cyhFeed.add(new ModelPost("choyihyun", "Cho Yi Hyun", R.drawable.foto_profil10, R.drawable.feedcyh5, "slayyyy"));

        userFeedMap.put("choyihyun", cyhFeed);
    }

    // ── Home Feed ─────────────────────────────────────────────────────────────
    public static void initHomeFeedIfEmpty() {
        if (!homeFeedList.isEmpty()) return;

        Object[][] data = {
                {
                        "nct_jaemin", "Na Jaemin",
                        R.drawable.foto_profil9, R.drawable.jaemin1,
                        "Dream era vibes ✨",
                        "12,7 Rb", "890",
                        "Diikuti oleh peachy.yeri dan 4 lainnya"
                },
                {
                        "real__pcy", "Chanyeol",
                        R.drawable.foto_profil10, R.drawable.cy1,
                        "Studio day 🎧",
                        "45,3 Rb", "210",
                        "Diikuti oleh sooyaaa__ dan 8 lainnya"
                },
                {
                        "peachy.yeri", "Yeri",
                        R.drawable.foto_profil1, R.drawable.feedyeri1,
                        "soft aesthetic 🌸",
                        "1,2 Rb", "340",
                        "Diikuti oleh 3 lainnya"
                },
                {
                        "carmen.official", "Carmen",
                        R.drawable.foto_profil4, R.drawable.feedcarmen1,
                        "daily moment ✨",
                        "5,6 Rb", "302",
                        "Diikuti oleh 2 lainnya"
                },
                {
                        "sooyaaa__", "Jisoo",
                        R.drawable.foto_profil2, R.drawable.feedjisoo1,
                        "BLACKPINK in your area 💕",
                        "23,1 Rb", "512",
                        "Diikuti oleh 7 lainnya"
                },
                {
                        "goyounjung", "Go Youn Jung",
                        R.drawable.foto_profil3, R.drawable.feedgyj1,
                        "on set 🎬",
                        "9,4 Rb", "430",
                        "Diikuti oleh 3 lainnya"
                },
                {
                        "byeonwooseok", "Byeon Woo Seok",
                        R.drawable.foto_profil5, R.drawable.feedbws1,
                        "actor life ✨",
                        "181 Rb", "64",
                        "Diikuti oleh 5 lainnya"
                },
                {
                        "songkang_b", "Song Kang",
                        R.drawable.foto_profil6, R.drawable.feedsk1,
                        "golden hour 🔥",
                        "92,9 Rb", "2.690",
                        "Diikuti oleh 10 lainnya"
                },
                {
                        "optimushwang", "Hwang Minhyun",
                        R.drawable.foto_profil1, R.drawable.feedmh1,
                        "stage mood 💙",
                        "78,9 Rb", "156",
                        "Diikuti oleh 6 lainnya"
                },
                {
                        "choyihyun", "Cho Yi Hyun",
                        R.drawable.foto_profil8, R.drawable.feedcyh1,
                        "OOTD today 🌸",
                        "156 Rb", "88",
                        "Diikuti oleh 12 lainnya"
                }

        };

        for (Object[] d : data) {
            homeFeedList.add(new ModelPost(
                    (String) d[0], (String) d[1],
                    (int)    d[2], (int)    d[3],
                    (String) d[4], (String) d[5],
                    (String) d[6], (String) d[7]
            ));
        }
    }

    // ── Profile Feed akun sendiri ─────────────────────────────────────────────
    public static void initProfileFeedIfEmpty() {
        for (ModelPost p : profileFeedList) {
            if (p.getImageResId() == R.drawable.feed1) return;
        }

        int[] images = {
                R.drawable.feed1, R.drawable.feed2, R.drawable.feed3,
                R.drawable.feed4, R.drawable.feed10
        };
        String[] captions = {
                "peri hutann", "Base-G", "miss EXO ot9",
                "NCT OT23", "gabut gambar"
        };
        for (int i = 0; i < images.length; i++) {
            profileFeedList.add(new ModelPost(
                    "vaniaakaroma", "Trivania Buli Karoma",
                    R.drawable.profil,
                    images[i], captions[i]
            ));
        }
    }
}