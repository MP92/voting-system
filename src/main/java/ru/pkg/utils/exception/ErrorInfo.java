package ru.pkg.utils.exception;

/**
 * Special object that contains info about thrown exception
 */
public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this.url = url.toString();
        this.cause = ex.getClass().getSimpleName();
        this.detail = ex.getLocalizedMessage();
    }

    public String getUrl() {
        return url;
    }
    public String getCause() {
        return cause;
    }
    public String getDetail() {
        return detail;
    }
}
