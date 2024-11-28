package com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private static final String SPACE = " ";

    private final String code;
    private final String message;
    private final String formattedError;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
        this.formattedError = code.concat(SPACE).concat(message);
    }

}
