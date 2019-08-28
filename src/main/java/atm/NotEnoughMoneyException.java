package atm;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
