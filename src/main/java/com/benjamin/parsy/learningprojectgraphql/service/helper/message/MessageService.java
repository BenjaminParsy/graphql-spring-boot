package com.benjamin.parsy.learningprojectgraphql.service.helper.message;

import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorCode;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorMessage;

public interface MessageService {

    ErrorMessage getMessage(ErrorCode errorCode, String[] args);

}
