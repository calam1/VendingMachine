package exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidMoneyException extends Exception{
    public InvalidMoneyException(String message) {
        super(message);
    }
}
