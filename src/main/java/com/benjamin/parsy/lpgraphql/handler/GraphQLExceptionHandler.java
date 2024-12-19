package com.benjamin.parsy.lpgraphql.handler;

import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorMessage;
import com.benjamin.parsy.lpgraphql.shared.exception.GraphQLException;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final String CODE = "code";
    private static final String DESCRIPTION = "description";
    private static final String VIOLATIONS = "violations";
    private static final String NPE_ARG = "An NPE occured";

    private final MessageService messageService;

    public GraphQLExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = GraphQLException.class)
    public GraphQLError handleGraphQLException(GraphQLException ex, @NonNull DataFetchingEnvironment env) {

        return buildGraphQLError(
                ErrorType.BAD_REQUEST,
                ex.getMessage(),
                env,
                Map.of(CODE, ex.getCode(), DESCRIPTION, ex.getDescription())
        );
    }

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = ConstraintViolationException.class)
    public GraphQLError handleConstraintViolationException(ConstraintViolationException ex, @NonNull DataFetchingEnvironment env) {

        Map<String, String> violationsMap = buildViolations(ex.getConstraintViolations());

        ErrorMessage errorIE3 = messageService.getErrorMessage(ErrorCode.BR8);

        return buildGraphQLError(
                ErrorType.BAD_REQUEST,
                errorIE3.getFormattedMessage(),
                env,
                Map.of(CODE, errorIE3.getCode(), DESCRIPTION, errorIE3.getDescription(),
                        VIOLATIONS, violationsMap)
        );
    }

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = NullPointerException.class)
    public GraphQLError handleNullPointerException(NullPointerException ex, @NonNull DataFetchingEnvironment env) {

        ErrorMessage errorIE3 = messageService.getErrorMessage(ErrorCode.BR5, NPE_ARG);

        log.error(errorIE3.getFormattedMessage(), ex);

        return buildGraphQLError(
                ErrorType.INTERNAL_ERROR,
                errorIE3.getFormattedMessage(),
                env,
                Map.of(CODE, errorIE3.getCode(), DESCRIPTION, errorIE3.getDescription())
        );
    }

    @SuppressWarnings("unused")
    @GraphQlExceptionHandler(value = Exception.class)
    public GraphQLError handleException(Exception ex, @NonNull DataFetchingEnvironment env) {

        ErrorMessage errorIE3 = messageService.getErrorMessage(ErrorCode.BR5, ex.getMessage());

        log.error(errorIE3.getFormattedMessage(), ex);

        return buildGraphQLError(
                ErrorType.INTERNAL_ERROR,
                errorIE3.getFormattedMessage(),
                env,
                Map.of(CODE, errorIE3.getCode(), DESCRIPTION, errorIE3.getDescription())
        );
    }

    public static GraphQLError buildGraphQLError(@NonNull ErrorType errorType,
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

    public static Map<String, String> buildViolations(Set<ConstraintViolation<?>> violationSet) {

        return violationSet.stream()
                .collect(Collectors.toMap(violation ->
                        violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (a, b) -> b,
                        LinkedHashMap::new));
    }

}