package com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST_001("error.badrequest.001"),
    BAD_REQUEST_002("error.badrequest.002");

    private final String codeKey;

}
