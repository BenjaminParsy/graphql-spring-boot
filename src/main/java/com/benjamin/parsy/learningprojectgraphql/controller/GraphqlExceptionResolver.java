package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.exception.GraphQLException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class GraphqlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private static final String UNKNOWN_ERROR_MSG = "An unexpected error has occurred, see the logs";

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {

        try {

            if (ex instanceof GraphQLException badRequestException) {
                return resolveWithBadRequest(badRequestException, env);
            }

        } catch (Exception e) {
            log.error("An error occurred while resolving exceptions for graphQL :", e);
            return resolveWithUnknownError(env);
        }

        return resolveWithUnknownError(env);
    }

    private GraphQLError resolveWithBadRequest(@NonNull GraphQLException ex, @NonNull DataFetchingEnvironment env) {
        return buildGraphQLError(ErrorType.BAD_REQUEST, ex.getMessage(), env);
    }

    private GraphQLError resolveWithUnknownError(@NonNull DataFetchingEnvironment env) {
        return buildGraphQLError(ErrorType.INTERNAL_ERROR, UNKNOWN_ERROR_MSG, env);
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
