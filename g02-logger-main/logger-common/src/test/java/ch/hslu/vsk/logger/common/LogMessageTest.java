package ch.hslu.vsk.logger.common;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Fail;

import static org.assertj.core.api.Assertions.assertThat;

class LogMessageTest {

    @Test
    void testSendGetMessage() {
        Instant timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        LogMessage message = new LogMessage(timestamp, "ERROR", "LogMessageTest", "Test Message");

        try {
            ByteArrayOutputStream inMemoryOutput = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(inMemoryOutput);
            LogMessage.sendMessage(dataOutputStream, message);

            ByteArrayInputStream inMemoryInput = new ByteArrayInputStream(inMemoryOutput.toByteArray());
            DataInputStream dataInputStream = new DataInputStream(inMemoryInput);
            assertThat(LogMessage.getMessage(dataInputStream)).isEqualTo(message);
        } catch (IOException e) {
            Fail.fail("An exception occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void testSendGetMessageEmpty() {
        Instant timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        LogMessage message = new LogMessage(timestamp, "INFO", "LogMessageTest", "");

        try {
            ByteArrayOutputStream inMemoryOutput = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(inMemoryOutput);
            LogMessage.sendMessage(dataOutputStream, message);

            ByteArrayInputStream inMemoryInput = new ByteArrayInputStream(inMemoryOutput.toByteArray());
            DataInputStream dataInputStream = new DataInputStream(inMemoryInput);
            assertThat(LogMessage.getMessage(dataInputStream)).isEqualTo(message);
        } catch (IOException e) {
            Fail.fail("An exception occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void testSendGetMessageLong() {
        byte[] array = new byte[1000];
        String messageString = new String(array, StandardCharsets.UTF_8);
        Instant timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        LogMessage message = new LogMessage(timestamp, "DEBUG", "LogMessageTest", messageString);

        try {
            ByteArrayOutputStream inMemoryOutput = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(inMemoryOutput);
            LogMessage.sendMessage(dataOutputStream, message);

            ByteArrayInputStream inMemoryInput = new ByteArrayInputStream(inMemoryOutput.toByteArray());
            DataInputStream dataInputStream = new DataInputStream(inMemoryInput);
            assertThat(LogMessage.getMessage(dataInputStream)).isEqualTo(message);
        } catch (IOException e) {
            Fail.fail("An exception occurred during the test: " + e.getMessage());
        }
    }
}
