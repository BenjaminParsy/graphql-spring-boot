package com.benjamin.parsy.lpgraphql.shared.exception;

import lombok.Getter;

@Getter
public class GraphQLException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public GraphQLException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return errorMessage.getCode();
    }

    public String getDescription() {
        return errorMessage.getDescription();
    }

}
