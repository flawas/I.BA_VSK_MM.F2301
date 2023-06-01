package ch.hslu.vsk.logger.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

public class PayloadFormatCompetitionStrategyTest {
    @Test
    void testCreatePayload() {
        PayloadFormatStrategy payloadFormatter = new PayloadFormatCompetitionStrategy();
        LogMessage log = new LogMessage(Instant.parse("2023-04-30T18:35:24.240Z"), "INFO", "formatterTest",
                "test message");
        String payload = payloadFormatter.createPayload(log);
        assertThat(payload).isEqualTo("2023-04-30 18:35:24.2400 INFO formatterTest test message");
    }
}