package ch.hslu.vsk.logger.common;

import java.nio.file.Path;
import java.time.Instant;

import ch.hslu.vsk.stringpersistor.api.StringPersistor;

/**
 * Implements the adapter for different string payload formats for the string
 * persistor.
 */
public final class StringPersistorAdapter implements LogPersistor {
    private final StringPersistor persistor;
    private final PayloadFormatStrategy payloadFormatter;

    /**
     * Constructs a new instance of the string persistor adapter.
     *
     * @param persistor        The string persistor
     * @param payloadFormatter The payload formatting strategy
     * @param file             The log file path
     */
    public StringPersistorAdapter(final StringPersistor persistor, final PayloadFormatStrategy payloadFormatter,
            final Path file) {
        this.persistor = persistor;
        this.persistor.setFile(file);
        this.payloadFormatter = payloadFormatter;
    }

    @Override
    public void save(final Instant timeStamp, final LogMessage log) {
        String payload = payloadFormatter.createPayload(log);
        persistor.save(timeStamp, payload);
    }
}
