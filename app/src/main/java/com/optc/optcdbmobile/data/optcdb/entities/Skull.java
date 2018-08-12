package com.optc.optcdbmobile.data.optcdb.entities;

import java.util.HashMap;
import java.util.Map;

public class Skull {
    public final static int MIN = 9001;
    public final static int MAX = 9020;

    //General
    private static final int STR = 9010;
    private static final int QCK = 9011;
    private static final int DEX = 9012;
    private static final int INT = 9013;
    private static final int PSY = 9014;


    //Straw Hat
    private static final int LUFFY = 9001;
    private static final int ZORO = 9002;
    private static final int NAMI = 9003;
    private static final int USOPP = 9004;
    private static final int SANJI = 9005;
    private static final int CHOPPER = 9006;
    private static final int ROBIN = 9007;
    private static final int FRANKY = 9008;
    private static final int BROOK = 9009;
    private static final int JIMBEY = 9999; //Ops, spoiler!

    //Judge's son
    private static final int JUDGE = 9015;
    private static final int REIJU = 9016;
    private static final int ICHIJI = 9017;
    private static final int NIJI = 9018;
    private static final int YONJI = 9019;

    private static final int DOFFY = 9020;

    private static final Map<String, Integer> idMap = new HashMap<String, Integer>() {{
        put("skullSTR", STR);
        put("skullQCK", QCK);
        put("skullDEX", DEX);
        put("skullINT", INT);
        put("skullPSY", PSY);

        put("skullLuffy", LUFFY);
        put("skullZoro", ZORO);
        put("skullNami", NAMI);
        put("skullUsopp", USOPP);
        put("skullSanji", SANJI);
        put("skullChopper", CHOPPER);
        put("skullRobin", ROBIN);
        put("skullFranky", FRANKY);
        put("skullBrook", BROOK);

        put("skullJudge", JUDGE);
        put("skullReiju", REIJU);
        put("skullNiji", NIJI);
        put("skullIchiji", ICHIJI);
        put("skullYonji", YONJI);

        put("skullDoffy", DOFFY);
    }};

    private static final Map<Integer, String> idName = new HashMap<Integer, String>() {{
        put(STR, "skullSTR");
        put(QCK, "skullQCK");
        put(DEX, "skullDEX");
        put(INT, "skullINT");
        put(PSY, "skullPSY");

        put(LUFFY, "skullLuffy");
        put(ZORO, "skullZoro");
        put(NAMI, "skullNami");
        put(USOPP, "skullUsopp");
        put(SANJI, "skullSanji");
        put(CHOPPER, "skullChopper");
        put(ROBIN, "skullRobin");
        put(FRANKY, "skullFranky");
        put(BROOK, "skullBrook");

        put(JUDGE, "skullJudge");
        put(REIJU, "skullReiju");
        put(NIJI, "skullNiji");
        put(ICHIJI, "skullIchiji");
        put(YONJI, "skullYonji");

        put(DOFFY, "skullDoffy");
    }};

    private static final Map<Integer, String> imageUrlMap = new HashMap<Integer, String>() {{
        put(STR, "https://onepiece-treasurecruise.com/wp-content/uploads/red_skull_f.png");
        put(QCK, "https://onepiece-treasurecruise.com/wp-content/uploads/blue_skull_f.png");
        put(DEX, "https://onepiece-treasurecruise.com/wp-content/uploads/green_skull_f.png");
        put(INT, "https://onepiece-treasurecruise.com/wp-content/uploads/black_skull_f.png");
        put(PSY, "https://onepiece-treasurecruise.com/wp-content/uploads/yellow_skull_f.png");

        put(LUFFY, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_luffy.png");
        put(ZORO, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_zoro.png");
        put(NAMI, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_nami.png");
        put(USOPP, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_usopp_f.png");
        put(SANJI, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_sanji_f.png");
        put(CHOPPER, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_chopper_f.png");
        put(ROBIN, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_robin_f.png");
        put(FRANKY, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_franky_f.png");
        put(BROOK, "https://onepiece-treasurecruise.com/wp-content/uploads/skull_brook_f.png");

        put(JUDGE, "https://onepiece-treasurecruise.com/wp-content/uploads/Jerma_skull_f1.png");
        put(REIJU, "https://onepiece-treasurecruise.com/wp-content/uploads/Jerma_skull_f2.png");
        put(ICHIJI, "https://onepiece-treasurecruise.com/wp-content/uploads/Jerma_skull_f3.png");
        put(NIJI, "https://onepiece-treasurecruise.com/wp-content/uploads/Jerma_skull_f4.png");
        put(YONJI, "https://onepiece-treasurecruise.com/wp-content/uploads/Jerma_skull_f5.png");

        put(DOFFY, "https://onepiece-treasurecruise.com/wp-content/uploads/Doflamingo_skull_f.png");
    }};


    public static int getId(String name) {
        return idMap.get(name);
    }

    public static String getName(int id) {
        return idName.get(id);
    }

}
