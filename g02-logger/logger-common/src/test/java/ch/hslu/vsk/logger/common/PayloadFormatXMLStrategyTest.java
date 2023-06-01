package ch.hslu.vsk.logger.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

public class PayloadFormatXMLStrategyTest {
    @Test
    void testCreatePayload() {
        PayloadFormatStrategy payloadFormatter = new PayloadFormatXMLStrategy();
        LogMessage log = new LogMessage(Instant.parse("2023-04-30T18:35:24.000Z"), "INFO", "formatterTest",
                "test message");
        String payload = payloadFormatter.createPayload(log);
        String expected = """
                <entry>
                    <timeStamp>2023-04-30T18:35:24Z</timeStamp>
                    <level>INFO</level>
                    <source>formatterTest</source>
                    <message>test message</message>
                </entry>
                """;
        assertThat(payload).isEqualTo(expected);
    }
}
