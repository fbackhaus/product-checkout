package com.novicap.checkout.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novicap.checkout.exception.UnreadableProductsFileException;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductRepositoryFromJsonFile implements ProductRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private static final String UNREADABLE_FILE_MESSAGE = "Error when trying to read products file";

    /**
     * Reads the products file in the resources folder, and returns it
     * @return a map with the ProductCode as key and the full product information as a value
     * @throws UnreadableProductsFileException if there is a problem when parsing the json file
     */
    @Override
    public Map<ProductCode, Product> getProducts() {
        try (InputStream fileStream = ClassLoader.getSystemResourceAsStream("products.json")) {
            List<Product> productList = Arrays.asList(mapper.readValue(fileStream, Product[].class));

            return productList.stream()
                    .collect(Collectors.toMap(Product::getCode, Function.identity()));
        } catch (IOException e) {
            throw new UnreadableProductsFileException(UNREADABLE_FILE_MESSAGE, e);
        }
    }
}
