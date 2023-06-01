package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.LogPersistor;
import ch.hslu.vsk.logger.common.PayloadFormatStrategy;
import ch.hslu.vsk.logger.common.PayloadFormatStringStrategy;
import ch.hslu.vsk.logger.common.StringPersistorAdapter;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements local log message handling using a file.
 */
public final class LogHandlerFile implements LogHandler {

    private final Path file;
    private final LogPersistor persistor;

    /**
     * Constructs a new instance of the file log message handler.
     *
     * @param file The file to save the logs to
     */
    public LogHandlerFile(final Path file) {
        StringPersistor stringPersistor = new StringPersistorFile();
        PayloadFormatStrategy formatStrategy = new PayloadFormatStringStrategy();
        this.file = file;
        this.persistor = new StringPersistorAdapter(stringPersistor, formatStrategy, this.file);
    }

    /**
     * Clears the local log file.
     */
    public void clearLocalLogs() {
        try {
            Files.deleteIfExists(this.file);
            StringPersistor stringPersistor = new StringPersistorFile();
            stringPersistor.setFile(this.file);
        } catch (IOException e) {
            System.out.println("Failed to clear local log file: " + this.file);
        }
    }

    /**
     * Returns all logs saved locally in the file.
     *
     * @return Log messages saved in the file
     */
    public List<LogMessage> getLocalLogs() {
        StringPersistor stringPersistor = new StringPersistorFile();
        stringPersistor.setFile(this.file);
        List<LogMessage> logMessages = new ArrayList<>();
        List<PersistedString> persistedStrings = stringPersistor.get(Integer.MAX_VALUE);
        for (PersistedString persistedString : persistedStrings) {
            logMessages.add(parseMessagePayload(persistedString.getPayload()));
        }
        return logMessages;
    }

    /**
     * Parses the log message payload from the persisted string.
     *
     * @param payload Message payload from the persisted string
     * @return The log message with the payload from the persisted string
     */
    public LogMessage parseMessagePayload(final String payload) {
        String[] splitString = payload.split(" \\| ");
        Instant timeStamp = Instant.parse(splitString[0]);
        return new LogMessage(timeStamp, splitString[1], splitString[2], splitString[3]);
    }

    @Override
    public boolean handleLog(final LogMessage logMessage) {
        persistor.save(Instant.now(), logMessage);
        return true;
    }
}
