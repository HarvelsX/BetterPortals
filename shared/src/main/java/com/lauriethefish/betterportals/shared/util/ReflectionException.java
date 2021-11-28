package com.lauriethefish.betterportals.shared.util;

/**
 * Wraps an exception generated by reflection for convenience
 */
public class ReflectionException extends RuntimeException {
    public ReflectionException(ReflectiveOperationException cause) {
        super(cause);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(String message) {
        super(message);
    }
}