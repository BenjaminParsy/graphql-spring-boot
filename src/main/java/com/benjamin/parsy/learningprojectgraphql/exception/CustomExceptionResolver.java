package com.benjamin.parsy.learningprojectgraphql.exception;

import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorMessage;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private static final String INTERNAL_ERROR_MSG = "An unexpected error has occurred, see the logs";

    private final MessageService messageService;

    public CustomExceptionResolver(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {

        try {

            if (ex instanceof BadRequestException badRequestException) {
                return resolveWithBadRequest(badRequestException, env);
            }

        } catch (Exception e) {
            log.error("An error occurred while resolving exceptions for graphQL :", e);
            return resolveWithInternalError(env);
        }

        return resolveWithInternalError(env);
    }

    private GraphQLError resolveWithBadRequest(@NonNull BadRequestException ex, @NonNull DataFetchingEnvironment env) {
        ErrorMessage errorMessage = messageService.getMessage(ex.getErrorCode(), ex.getArgs());
        return buildGraphQLError(ErrorType.BAD_REQUEST, errorMessage.getFormattedError(), env);
    }

    private GraphQLError resolveWithInternalError(@NonNull DataFetchingEnvironment env) {
        return buildGraphQLError(ErrorType.INTERNAL_ERROR, INTERNAL_ERROR_MSG, env);
    }

    private GraphQLError buildGraphQLError(@NonNull ErrorType errorType, String message, @NonNull DataFetchingEnvironment env) {

        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

}
