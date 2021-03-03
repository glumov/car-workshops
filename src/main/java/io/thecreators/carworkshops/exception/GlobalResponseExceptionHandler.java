package io.thecreators.carworkshops.exception;

import io.thecreators.carworkshops.mapper.ResponseMapper;
import io.thecreators.carworkshops.model.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ResponseError err = new ResponseError(LocalDateTime.now(), "Invalid JSON" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "Malformed JSON request" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName()+ " : " +error.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseError err = new ResponseError(LocalDateTime.now(), "Validation Errors" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getParameterName() + " parameter is missing");

        ResponseError err = new ResponseError(LocalDateTime.now(), "Missing Parameters" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ResponseError err = new ResponseError(LocalDateTime.now(), "Mismatch Type" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "Constraint Violation" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "Resource Not Found" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        ResponseError err = new ResponseError(LocalDateTime.now(), "Method Not Found" ,details);

        return ResponseMapper.errorToEntity(err,HttpStatus.BAD_REQUEST);

    }


}