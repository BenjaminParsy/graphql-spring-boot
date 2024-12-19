package com.benjamin.parsy.lpgraphql.shared.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {

    BR1("error.br.1"),
    BR3("error.br.3"),
    BR4("error.br.4"),
    BR5("error.br.5"),
    BR8("error.br.8"),

    IE1("error.ie.1"),
    IE2("error.ie.2"),
    ;

    private final String baseKey;

    public String getCodeKey() {
        return baseKey + ".code";
    }

    public String getDescriptionKey() {
        return baseKey + ".desc";
    }

}
