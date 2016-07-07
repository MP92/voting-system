package ru.pkg;

public final class Profiles {
    private Profiles() {
    }

    public static final String
        JDBC = "jdbc",
        JPA = "jpa",
        POSTGRESQL = "postgresql",
        H2 = "h2";

    public static final String ACTIVE_DB = POSTGRESQL;
    public static final String ACTIVE_DAO_IMPL = JPA;
}
