package com.benjamin.parsy.lpgraphql.shared.service.impl;

import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorMessage;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @NonNull
    @Override
    public ErrorMessage getErrorMessage(ErrorCode errorCode, Object[] args) {

        String code = getLocalizedMessage(errorCode.getCodeKey());
        String description = getLocalizedMessage(errorCode.getDescriptionKey(), args);

        return new ErrorMessage(code, description);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

}
