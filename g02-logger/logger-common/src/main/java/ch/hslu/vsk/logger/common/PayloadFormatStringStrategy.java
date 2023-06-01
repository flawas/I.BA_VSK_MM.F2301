package ch.hslu.vsk.logger.common;

/**
 * Implements the string payload format.
 */
public final class PayloadFormatStringStrategy implements PayloadFormatStrategy {

    @Override
    public String createPayload(final LogMessage logMessage) {
        return logMessage.timeStamp() + " | " + logMessage.level() + " | " + logMessage.source() + " | "
                + logMessage.message();
    }
}
