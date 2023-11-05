package ma.nemo.assignment.exceptions;

public class ProductQuantityNotInStock extends Exception{
    private static final long serialVersionUID = 1L;
    public ProductQuantityNotInStock(){

    }
    public ProductQuantityNotInStock(String message) {
        super(message);
    }
}
