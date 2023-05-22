package ch.srg.dataProvider.integrationlayer.legacy;

public interface Cancellable {
    Cancellable NOT_CANCELLABLE = () -> {
    };

    void cancel();
}
