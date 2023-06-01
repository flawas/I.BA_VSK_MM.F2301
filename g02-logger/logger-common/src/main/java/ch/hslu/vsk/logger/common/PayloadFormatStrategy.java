package ch.hslu.vsk.logger.common;

/**
 * Provides payload formatting strategies for log files.
 */
public interface PayloadFormatStrategy {

    /**
     * Creates the formatted string payload for the log file.
     *
     * @param logMessage
     * @return The formatted string payload
     */
    String createPayload(final LogMessage logMessage);
}
