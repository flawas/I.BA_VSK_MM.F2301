package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.LogPersistor;
import ch.hslu.vsk.logger.common.PayloadFormatCompetitionStrategy;
import ch.hslu.vsk.logger.common.PayloadFormatStrategy;
import ch.hslu.vsk.logger.common.StringPersistorAdapter;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;

import java.nio.file.Path;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BlockingQueue;

/**
 * Processes the log messages and persists them to a file.
 */
public final class MessagePersistor implements Runnable {

    private final BlockingQueue<LogMessage> msgQueue;
    private final LogPersistor persistor;

    /**
     * Creates a new instance of the log message persistor.
     *
     * @param file     The log file path
     * @param msgQueue The log message queue
     */
    public MessagePersistor(final Path file, final BlockingQueue<LogMessage> msgQueue) {
        StringPersistor stringPersistor = new StringPersistorFile();
        PayloadFormatStrategy payloadFormatter = new PayloadFormatCompetitionStrategy();
        this.persistor = new StringPersistorAdapter(stringPersistor, payloadFormatter, file);
        this.msgQueue = msgQueue;
    }

    /**
     * Saves the log messages from the queue to the log file.
     */
    @Override
    public void run() {
        while (true) {
            try {
                LogMessage message = msgQueue.take();
                Instant serverTimeStamp = Instant.now().truncatedTo(ChronoUnit.MILLIS);
                persistor.save(serverTimeStamp, message);
            } catch (InterruptedException e) {
                System.out.println("Failed to get message from queue: " + e.getMessage());
            }
        }
    }
}
