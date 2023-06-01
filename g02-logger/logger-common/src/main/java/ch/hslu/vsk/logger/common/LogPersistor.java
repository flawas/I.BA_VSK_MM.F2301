package ch.hslu.vsk.logger.common;

import java.time.Instant;

/**
 * Provides log message persisting functionalities.
 */
public interface LogPersistor {

    /**
     * Saves a log message with a timestamp.
     *
     * @param timeStamp The timestamp of the log message
     * @param log       The log message
     */
    void save(Instant timeStamp, LogMessage log);
}
