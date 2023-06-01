package ch.hslu.vsk.logger.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

public class PayloadFormatStringStrategyTest {

    @Test
    void testCreatePayload() {
        PayloadFormatStrategy payloadFormatter = new PayloadFormatStringStrategy();
        LogMessage log = new LogMessage(Instant.parse("2023-04-30T18:35:24.000Z"), "INFO", "formatterTest",
                "test message");
        String payload = payloadFormatter.createPayload(log);
        assertThat(payload).isEqualTo("2023-04-30T18:35:24Z | INFO | formatterTest | test message");
    }
}
