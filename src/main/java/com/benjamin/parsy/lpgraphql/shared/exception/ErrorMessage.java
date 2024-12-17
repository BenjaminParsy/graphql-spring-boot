package com.benjamin.parsy.lpgraphql.shared.exception;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private static final String SPACE = " ";

    private final String code;
    private final String description;
    private final String formattedMessage;

    public ErrorMessage(String code, String description) {
        this.code = code;
        this.description = description;
        this.formattedMessage = code.concat(SPACE).concat(description);
    }

}
