package com.benjamin.parsy.learningprojectgraphql.service.helper.message.impl;

import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorCode;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private static final String CODE_KEY = ".code";
    private static final String MESSAGE_KEY = ".message";

    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ErrorMessage getMessage(ErrorCode errorCode, String... args) {

        String code = getLocalizedMessage(errorCode.getCodeKey().concat(CODE_KEY));
        String message = getLocalizedMessage(errorCode.getCodeKey().concat(MESSAGE_KEY), args);

        return new ErrorMessage(code, message);
    }

    private String getLocalizedMessage(String key, String... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

}
