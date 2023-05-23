package ch.srg.dataProvider.integrationlayer.legacy;

/**
 * Created by seb on 13/04/15.
 */
public class IlMediaBlockedException extends IlMediaCompositionException {
    public IlMediaBlockedException(String blockingReason) {
        super(blockingReason);
    }
}
