package me.sidazhang.recipe.exceptions;

public class ImageFormatException extends RuntimeException {
    public ImageFormatException() {
    }

    public ImageFormatException(String message) {
        super(message);
    }

    public ImageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
