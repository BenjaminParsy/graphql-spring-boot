package com.benjamin.parsy.lpgraphql.shared.service;

import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorMessage;
import org.springframework.lang.NonNull;

public interface MessageService {

    @NonNull
    ErrorMessage getErrorMessage(ErrorCode errorCode, Object... args);

}
