package com.rjeady.dpll;

public class TautologicalClauseException extends Exception {
    public TautologicalClauseException() {
    }

    public TautologicalClauseException(String message) {
        super(message);
    }

    public TautologicalClauseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TautologicalClauseException(Throwable cause) {
        super(cause);
    }

    public TautologicalClauseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
