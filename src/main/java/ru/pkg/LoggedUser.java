package ru.pkg;

public class LoggedUser {

    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }
}
