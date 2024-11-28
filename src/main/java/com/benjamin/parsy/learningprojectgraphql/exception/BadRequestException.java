package com.benjamin.parsy.learningprojectgraphql.exception;

import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String[] args;

    public BadRequestException(ErrorCode errorCode, String... args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }

}
