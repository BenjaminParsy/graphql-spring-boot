package com.benjamin.parsy.learningprojectgraphql.service.helper.message;

import com.benjamin.parsy.learningprojectgraphql.exception.ErrorCode;
import com.benjamin.parsy.learningprojectgraphql.exception.ErrorMessage;

public interface MessageService {

    ErrorMessage getErrorMessage(ErrorCode errorCode, Object... args);

}
