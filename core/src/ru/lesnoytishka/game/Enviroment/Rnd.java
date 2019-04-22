package ru.lesnoytishka.game.Enviroment;

import java.util.Random;

public class Rnd {
    private static final Random random = new Random();

    public static float getFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
}
