package ch.hslu.vsk.logger.common;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Implements the string payload format for the competition.
 */
public final class PayloadFormatCompetitionStrategy implements PayloadFormatStrategy {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSS";

    @Override
    public String createPayload(final LogMessage logMessage) {
        DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneOffset.UTC);
        return timeStampFormatter.format(logMessage.timeStamp()) + " " + logMessage.level().toUpperCase() + " "
                + logMessage.source() + " " + logMessage.message();
    }

}
