package com.mdx.rest.tree.excetion.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Matheus Xavier
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }
}
