package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.LogMessage;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class MessagePersistorTest {

    private static final Path TEST_LOG_FILE = Path.of("MessagePersistorTest.log");

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(TEST_LOG_FILE);
        } catch (IOException e) {
            Fail.fail("Failed to delete test log file: " + TEST_LOG_FILE.getFileName());
        }
    }

    @Test
    void testRun() {
        ExecutorService testExecutor = Executors.newSingleThreadExecutor();
        BlockingQueue<LogMessage> messageQueue = new LinkedBlockingQueue<>();
        MessagePersistor messagePersistor = new MessagePersistor(TEST_LOG_FILE, messageQueue);

        testExecutor.execute(messagePersistor);
        messageQueue.add(new LogMessage(Instant.now(), "INFO", "MessagePeristorTest", "Test Message 1"));
        messageQueue.add(new LogMessage(Instant.now(), "INFO", "MessagePeristorTest", "Test Message 2"));
        try {
            if (!testExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                testExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            testExecutor.shutdownNow();
        }

        assertThat(readLines()).hasSize(2);
        assertThat(readLines().get(0)).contains("Test Message 1");
        assertThat(readLines().get(1)).contains("Test Message 2");
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(TEST_LOG_FILE);
        } catch (IOException e) {
            Fail.fail("Failed to read file: " + e.getMessage());
        }
        return List.of();
    }
}
