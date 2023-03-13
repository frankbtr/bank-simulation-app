package com.frank.exceptions;

public class UnderConstructionException extends RuntimeException {
    public UnderConstructionException(String app_is_under_construction) {
        super(app_is_under_construction);
    }
}
