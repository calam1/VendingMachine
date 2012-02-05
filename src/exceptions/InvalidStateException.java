package exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidStateException extends Exception{
    public InvalidStateException(String message){
        super(message);
    }
}
