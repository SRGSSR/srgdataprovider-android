package ch.srg.dataProvider.integrationlayer.legacy;

/**
 * When IL MediaComposition received is not playable.
 */
public class IlDataException extends Exception {
    public IlDataException(String message) {
        super(message, null);
    }
}
