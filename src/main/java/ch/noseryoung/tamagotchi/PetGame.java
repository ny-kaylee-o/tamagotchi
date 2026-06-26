package ch.noseryoung.tamagotchi;

import java.util.Random;

public class PetGame {

    public static final int LOSE = 0;
    public static final int SMALL_WIN = 1;
    public static final int BIG_WIN = 2;

    private static final String[] SYMBOLS = {"♡", "▽", "☆", "✿", "☻"};

    private final Random random = new Random();


    private String symbol1;
    private String symbol2;
    private String symbol3;


    public int spin() {
        symbol1 = randomSymbol();
        symbol2 = randomSymbol();
        symbol3 = randomSymbol();

        if (symbol1.equals(symbol2) && symbol2.equals(symbol3)) {
            return BIG_WIN;
        }
        if (symbol1.equals(symbol2) || symbol2.equals(symbol3) || symbol1.equals(symbol3)) {
            return SMALL_WIN;
        }
        return LOSE;
    }

    private String randomSymbol() {
        int index = random.nextInt(SYMBOLS.length);
        return SYMBOLS[index];
    }

    public String getSymbol1() { return symbol1; }
    public String getSymbol2() { return symbol2; }
    public String getSymbol3() { return symbol3; }
}