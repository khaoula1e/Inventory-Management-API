package ma.nemo.assignment.web.common;


import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductQuantityNotInStock;
import ma.nemo.assignment.exceptions.ProductValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ProductAlreadyExists.class)
    public final ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExists ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductNotFound.class)
    public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFound ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductQuantityNotInStock.class)
    public final ResponseEntity<Object> handleProductQuantityNotInStockException(ProductQuantityNotInStock ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductValidationException.class)
    public final ResponseEntity<Object> handleProductValidationException(ProductValidationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    // Generic exception handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
