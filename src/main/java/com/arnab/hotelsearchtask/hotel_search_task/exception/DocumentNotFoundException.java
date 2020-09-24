package com.arnab.hotelsearchtask.hotel_search_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends Exception {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
