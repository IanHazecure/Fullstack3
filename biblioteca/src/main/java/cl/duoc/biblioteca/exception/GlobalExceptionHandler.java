package cl.duoc.biblioteca.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidBody(MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "Validation error", "La solicitud contiene campos inválidos",
                request.getRequestURI(), fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleInvalidParameter(ConstraintViolationException exception,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(error -> fieldErrors.putIfAbsent(
                error.getPropertyPath().toString(), error.getMessage()));
        return build(HttpStatus.BAD_REQUEST, "Validation error", "Los parámetros contienen valores inválidos",
                request.getRequestURI(), fieldErrors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException exception,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        return build(status, status.getReasonPhrase(), exception.getReason(), request.getRequestURI(), Map.of());
    }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException exception,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Malformed JSON", "El cuerpo de la solicitud no es un JSON válido",
            request.getRequestURI(), Map.of());
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed",
            "El método HTTP no está permitido para esta ruta", request.getRequestURI(), Map.of());
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiError> handleNotFound(NoResourceFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "Not found", "La ruta solicitada no existe", request.getRequestURI(),
            Map.of());
        }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException exception, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, "Conflict", exception.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataConflict(DataIntegrityViolationException exception,
            HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, "Conflict", "La operación viola una restricción de datos",
                request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException exception,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Bad request", exception.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", "Ocurrió un error inesperado",
                request.getRequestURI(), Map.of());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String error, String message, String path,
            Map<String, String> fieldErrors) {
        ApiError body = new ApiError(Instant.now(), status.value(), error, message, path, fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}