/**
 * This class is wrapper of the Exception, showing that the
 * exception is related to incorrect 'required' directives.
 */
public final class WrongRequireException extends Exception {
    /**
     * This is public constructor of WrongRequireException.
     * @param errorMessage Message of error.
     */
    public WrongRequireException(String errorMessage) {
        super(errorMessage);
    }
}
