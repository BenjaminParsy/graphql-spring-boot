package com.benjamin.parsy.lpgraphql.shared.service;

import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorMessage;

public interface MessageService {

    ErrorMessage getErrorMessage(ErrorCode errorCode, Object... args);

}
