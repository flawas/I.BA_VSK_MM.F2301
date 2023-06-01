package ch.hslu.vsk.logger.common;

/**
 * Implements the XML payload format.
 */
public final class PayloadFormatXMLStrategy implements PayloadFormatStrategy {

    @Override
    public String createPayload(final LogMessage logMessage) {
        return String.format("""
                <entry>
                    <timeStamp>%s</timeStamp>
                    <level>%s</level>
                    <source>%s</source>
                    <message>%s</message>
                </entry>
                        """, logMessage.timeStamp(), logMessage.level(), logMessage.source(), logMessage.message());
    }

}
