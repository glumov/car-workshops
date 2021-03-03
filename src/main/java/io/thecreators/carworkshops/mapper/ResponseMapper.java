package io.thecreators.carworkshops.mapper;

import io.thecreators.carworkshops.model.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {
    public static ResponseEntity<Object> errorToEntity(ResponseError err, HttpStatus status) {
        return new ResponseEntity<>(err, status);
    }

}