package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.User;

import java.util.Random;

public class UserGenerator {
    static Random random = new Random();

    public static User generateUser() {
        return new User(generateEmail(), generatePassword(), generateName());
    }

    public static User generateUserBadPass() {
        return new User(generateEmail(), generateBadPassword(), generateName());
    }

    private static String generateName() {
        return ("avzname" + random.nextInt(1000));
    }

    private static String generateEmail() {
        return ("avzemail" + random.nextInt(1000) + "@yandex.ru");
    }

    private static String generatePassword() {
        return ("avzpass" + random.nextInt(1000));
    }

    private static String generateBadPassword() {
        return ("a" + random.nextInt(1000));
    }

}
