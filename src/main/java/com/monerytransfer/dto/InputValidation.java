package com.monerytransfer.dto;

public interface InputValidation {

	public static void checkBlank(String obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
