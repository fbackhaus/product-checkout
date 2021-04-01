package com.novicap.checkout.exception;

import java.io.IOException;

public class UnreadableProductsFileException extends RuntimeException {
    public UnreadableProductsFileException(String message, IOException exception) {
        super(message, exception);
    }
}
