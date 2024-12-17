package com.benjamin.parsy.lpgraphql.handler;

import com.benjamin.parsy.lpgraphql.shared.exception.GraphQLException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final String UNKNOWN_ERROR_MSG = "An unexpected error has occurred, see the logs";
    private static final String CODE = "code";
    private static final String DESCRIPTION = "description";

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = GraphQLException.class)
    public GraphQLError handle(GraphQLException ex, @NonNull DataFetchingEnvironment env) {
        return buildGraphQLError(ErrorType.BAD_REQUEST, ex.getMessage(), env,
                Map.of(CODE, ex.getCode(), DESCRIPTION, ex.getDescription()));
    }

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = Exception.class)
    public GraphQLError handle(@NonNull DataFetchingEnvironment env) {
        return buildGraphQLError(ErrorType.INTERNAL_ERROR, UNKNOWN_ERROR_MSG, env, null);
    }

    private GraphQLError buildGraphQLError(@NonNull ErrorType errorType,
                                           String message,
                                           @NonNull DataFetchingEnvironment env,
                                           Map<String, Object> extensions) {

        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(extensions)
                .build();
    }

}
