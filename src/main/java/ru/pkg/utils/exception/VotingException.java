package ru.pkg.utils.exception;

public class VotingException extends RuntimeException {

    private static final String MSG_PATTERN = "User with id=%d not voted";

    public VotingException(Throwable cause) {
        super(cause);
    }

    public VotingException(int userId) {
        super(String.format(MSG_PATTERN, userId));
    }
}
