package com.novicap.checkout.exception;

import java.io.IOException;

/**
 * Exception thrown when there is a problem parsing the products.json file in resources
 */
public class UnreadableProductsFileException extends RuntimeException {
    public UnreadableProductsFileException(String message, IOException exception) {
        super(message, exception);
    }
}
