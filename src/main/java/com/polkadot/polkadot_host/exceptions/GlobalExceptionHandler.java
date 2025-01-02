package com.polkadot.polkadot_host.exceptions;

import com.polkadot.polkadot_host.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;

import static com.polkadot.polkadot_host.exceptions.messages.ExceptionMessage.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalStateException.class)
    protected ResponseEntity<String> handleException(IllegalStateException ex) {
        ErrorMessage error = new ErrorMessage(GLOBAL_INVALID_STATE_EXCEPTION,
                ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error.toJson());
    }

    @ExceptionHandler(value = ExecutionException.class)
    protected ResponseEntity<String> handleException(ExecutionException ex) {
        ErrorMessage error = new ErrorMessage(GLOBAL_EXECUTION_EXCEPTION,
                String.format(POLKADOT_RPC_EXECUTION_EXCEPTION, ex.getMessage()));
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error.toJson());
    }


    @ExceptionHandler(value = InterruptedException.class)
    protected ResponseEntity<String> handleException(InterruptedException ex) {
        ErrorMessage error = new ErrorMessage(GLOBAL_INTERRUPTED_EXCEPTION,
                String.format(POLKADOT_RPC_INTERRUPTED_EXCEPTION, ex.getMessage()));
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error.toJson());
    }
}
