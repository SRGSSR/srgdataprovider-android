package ch.srg.dataProvider.integrationlayer.legacy;

/**
 * When IL MediaComposition received is not playable.
 */
public class IlMediaCompositionException extends Exception {
    public IlMediaCompositionException(String message) {
        super(message, null);
    }
}
