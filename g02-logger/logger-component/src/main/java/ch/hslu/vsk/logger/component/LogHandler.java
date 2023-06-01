package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.common.LogMessage;

/**
 * Provides log message handling functionalities.
 */
public interface LogHandler {

    /**
     * Handles a log message.
     *
     * @param logMessage The log message
     * @return Whether to log message handling was successful or not
     */
    boolean handleLog(LogMessage logMessage);
}
