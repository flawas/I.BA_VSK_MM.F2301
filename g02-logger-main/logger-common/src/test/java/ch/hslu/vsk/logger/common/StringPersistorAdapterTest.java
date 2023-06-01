package ch.hslu.vsk.logger.common;

import org.junit.jupiter.api.Test;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class StringPersistorAdapterTest {

    class StringPersistorMock implements StringPersistor {
        private Path path;
        private Instant timestamp;
        private String payload;

        @Override
        public void setFile(final Path path) {
            this.path = path;
        }

        @Override
        public void save(final Instant timestamp, final String payload) {
            this.timestamp = timestamp;
            this.payload = payload;
        }

        @Override
        public List<PersistedString> get(final int count) {
            throw new UnsupportedOperationException("Unimplemented method 'get'");
        }

        public Path getPath() {
            return path;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        public String getPayload() {
            return payload;
        }
    }

    static class PayloadFormatStrategyMock implements PayloadFormatStrategy {

        @Override
        public String createPayload(final LogMessage logMessage) {
            return logMessage.timeStamp() + ", " + logMessage.level() + ", " + logMessage.source() + ", "
                    + logMessage.message();
        }
    }

    @Test
    void testFileSet() {
        Path path = new File("testfile").toPath();
        StringPersistorMock persistor = new StringPersistorMock();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatStrategyMock();
        new StringPersistorAdapter(persistor, payloadFormatter, path);
        assertThat(persistor.getPath()).isEqualTo(path);
    }

    @Test
    void testLogMessageMockStrategy() {
        Path path = new File("testfile").toPath();
        StringPersistorMock persistor = new StringPersistorMock();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatStrategyMock();
        Instant timeStamp = Instant.parse("2023-04-30T18:35:24.000Z");
        LogMessage log = new LogMessage(timeStamp, "INFO", "adapterTest", "test message");
        LogPersistor adapter = new StringPersistorAdapter(persistor, payloadFormatter, path);
        adapter.save(timeStamp, log);
        assertThat(persistor.getPayload()).isEqualTo("2023-04-30T18:35:24Z, INFO, adapterTest, test message");
        assertThat(persistor.getTimestamp()).isEqualTo(timeStamp);
    }

    @Test
    void testLogMessageStringStrategy() {
        Path path = new File("testfile").toPath();
        StringPersistorMock persistor = new StringPersistorMock();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatStringStrategy();
        Instant timeStamp = Instant.parse("2023-04-30T18:35:24.000Z");
        LogMessage log = new LogMessage(timeStamp, "INFO", "adapterTest", "test message");
        LogPersistor adapter = new StringPersistorAdapter(persistor, payloadFormatter, path);
        adapter.save(timeStamp, log);
        assertThat(persistor.getPayload()).isEqualTo("2023-04-30T18:35:24Z | INFO | adapterTest | test message");
        assertThat(Date.from(persistor.getTimestamp())).isEqualTo(timeStamp);
    }

    @Test
    void testLogMessageXMLStrategy() {
        Path path = new File("testfile").toPath();
        StringPersistorMock persistor = new StringPersistorMock();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatXMLStrategy();
        Instant timeStamp = Instant.parse("2023-04-30T18:35:24.000Z");
        LogMessage log = new LogMessage(timeStamp, "INFO", "adapterTest", "test message");
        LogPersistor adapter = new StringPersistorAdapter(persistor, payloadFormatter, path);
        adapter.save(timeStamp, log);
        String expected = """
                <entry>
                    <timeStamp>2023-04-30T18:35:24Z</timeStamp>
                    <level>INFO</level>
                    <source>adapterTest</source>
                    <message>test message</message>
                </entry>
                """;
        assertThat(persistor.getPayload()).isEqualTo(expected);
        assertThat(Date.from(persistor.getTimestamp())).isEqualTo(timeStamp);
    }

    @Test
    void testLogMessageCompetitionStrategy() {
        Path path = new File("testfile").toPath();
        StringPersistorMock persistor = new StringPersistorMock();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatCompetitionStrategy();
        Instant timeStamp = Instant.parse("2023-04-30T18:35:24.240Z");
        LogMessage log = new LogMessage(timeStamp, "INFO", "adapterTest", "test message");
        LogPersistor adapter = new StringPersistorAdapter(persistor, payloadFormatter, path);
        adapter.save(timeStamp, log);
        String expected = "2023-04-30 18:35:24.2400 INFO adapterTest test message";
        assertThat(persistor.getPayload()).isEqualTo(expected);
        assertThat(Date.from(persistor.getTimestamp())).isEqualTo(timeStamp);
    }
}
