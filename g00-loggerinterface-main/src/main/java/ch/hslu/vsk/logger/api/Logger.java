package ch.hslu.vsk.logger.api;

public interface Logger {
    void log(MessageLevel level, String message);

    void setMessageLevelFilter(MessageLevel messageLevelFilter);
}
