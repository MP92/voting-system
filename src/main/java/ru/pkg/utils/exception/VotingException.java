package ru.pkg.utils.exception;

public class VotingException extends RuntimeException {

    public VotingException(Throwable cause) {
        super(cause);
    }

    public VotingException(int userId) {
        super("Vote for user with id=" + userId + " not found");
    }

    public VotingException(String message) {
        super(message);
    }
}
