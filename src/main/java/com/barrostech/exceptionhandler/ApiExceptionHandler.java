package com.barrostech.exceptionhandler;

import com.barrostech.core.validation.ValidacaoException;
import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.exception.SenhasInconstantesException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) rootCause,headers,status,request);
        }else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException) rootCause,headers,status,request);
        }

        ProblemType problemType = ProblemType.JSON_INVALIDO;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(status,problemType,detail)

                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status,request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.",ex.getRequestURL());
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status,request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = String.format("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");

        List<Field> problemFields = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if(objectError instanceof FieldError){
                        name = ((FieldError) objectError).getField();
                    }

                    return Field.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                } )
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status,problemType,detail)
                .objects(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if( rootCause instanceof MethodArgumentTypeMismatchException || rootCause instanceof NumberFormatException){
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = "Erro no parâmetro informado na requisição.";
        Problem problem = createProblemBuilder(status,problemType,detail).build();


        return handleExceptionInternal(ex, problem, new HttpHeaders(), status,request);
    }


    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s' e é esperado um valor do tipo %s",ex.getName(),ex.getValue(),ex.getRequiredType().getSimpleName());
        Problem problem =  createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(ex,problem,headers,status,request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.JSON_INVALIDO;
        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
        String detail = String.format("A propriedade '%s' não pertence ao corpo da requisição", path);
        Problem problem =  createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(ex,problem,headers,status,request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.JSON_INVALIDO;

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s' "+
                "que é do tipo '%s' incompátivel com o valor esperado que é um '%s'. Corrija e informe um valor compátivel com o tipo %s para este campo.",
                path,ex.getValue(),ex.getValue().getClass().getSimpleName(),ex.getTargetType().getSimpleName(),ex.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(ex,problem,headers,status,request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException e, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.JSON_INVALIDO;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return  handleExceptionInternal(e,problem,new HttpHeaders(),status,request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            EntidadeNaoEncontradaException e, WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status,problemType,detail).build();


        return handleExceptionInternal(e, problem, new HttpHeaders(), status,request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> HandleGenericException(Exception e, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_NO_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir entre em contato com o administrador do sistema";
        Problem problem = createProblemBuilder(status,problemType,detail).build();


        return handleExceptionInternal(e, problem, new HttpHeaders(), status,request);
    }



    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException e, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), status,request);
    }

    @ExceptionHandler(SenhasInconstantesException.class)
    public ResponseEntity<?> handleSenhasInconstanteException(
            NegocioException e, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.SENHA_INCORRETA;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), status,request);
    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status,problemType,detail).build();

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), status,request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null){

            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        }else if(body instanceof String){
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value() )
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
