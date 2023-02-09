package app.employeespair.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private final int BAD_REQUEST_STATUS_CODE = 400;
    private final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<JsonResponse> handleIllegalArgument(Exception e) {
        return new ResponseEntity<>(new JsonResponse(BAD_REQUEST_STATUS_CODE, BAD_REQUEST_ERROR_MESSAGE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}