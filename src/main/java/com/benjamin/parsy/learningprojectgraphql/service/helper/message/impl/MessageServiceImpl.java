package com.benjamin.parsy.learningprojectgraphql.service.helper.message.impl;

import com.benjamin.parsy.learningprojectgraphql.exception.ErrorCode;
import com.benjamin.parsy.learningprojectgraphql.exception.ErrorMessage;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ErrorMessage getErrorMessage(ErrorCode errorCode, Object[] args) {

        String code = getLocalizedMessage(errorCode.getCodeKey());
        String description = getLocalizedMessage(errorCode.getDescriptionKey(), args);

        return new ErrorMessage(code, description);
    }

    private String getLocalizedMessage(String key, Object... args) {

        try {
            return messageSource.getMessage(key, args, Locale.getDefault());
        } catch (Exception e) {
            return "Message not found";
        }
    }

}
