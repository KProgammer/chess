package service;

import java.util.Objects;

public class ClearResult {
    private final String message;

    public ClearResult(String Message){

        message = Message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearResult that = (ClearResult) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
