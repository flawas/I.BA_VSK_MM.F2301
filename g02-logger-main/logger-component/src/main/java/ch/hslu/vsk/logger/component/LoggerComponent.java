/**
 * This class implements the Logger and LoggerSetup interfaces, providing functionality for logging messages
 * to a server via a socket connection. It also allows for configuration of the message level filter and setup
 * of the server connection parameters.
 *
 * @author Lorin Bucher, Sandro Hilpert, Flavio Waser, Peter Zürcher
 */
package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import ch.hslu.vsk.logger.api.MessageLevel;
import ch.hslu.vsk.logger.common.LogMessage;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

/**
 * Implements the Log Component.
 */
public final class LoggerComponent implements Logger, LoggerSetup {

    private MessageLevel filterLevel = MessageLevel.ALL;
    private String host;
    private int port;
    private String clientName;
    private Path path;

    private LogHandlerFile logHandlerFile;
    private LogHandlerServer logHandlerServer;

    /**
     * Logs a message to the server via a socket connection.
     *
     * @param messageLevel The level of the message being logged.
     * @param message      The message to be logged.
     */
    @Override
    public void log(final MessageLevel messageLevel, final String message) {

        if (messageLevel.getIntLevel() <= filterLevel.getIntLevel()) {
            LogMessage logMessage = new LogMessage(Instant.now(), messageLevel.name(), clientName, message);

            boolean sendMesssageFailed = false;
            if (logHandlerServer.isConnected()) {
                if (!logHandlerServer.handleLog(logMessage)) {
                    sendMesssageFailed = true;
                }
            } else {
                logHandlerServer.connectToServer();
                if (logHandlerServer.isConnected()) {
                    if (!sendLocalLogs() || !logHandlerServer.handleLog(logMessage)) {
                        sendMesssageFailed = true;
                    }
                } else {
                    sendMesssageFailed = true;
                }
            }

            if (sendMesssageFailed) {
                logHandlerFile.handleLog(logMessage);
            }
        }
    }

    /**
     * Sets the filter level for the logger.
     *
     * @param inputMessageLevel The message level to filter messages by.
     */
    @Override
    public void setMessageLevelFilter(final MessageLevel inputMessageLevel) {
        this.filterLevel = inputMessageLevel;
    }

    /**
     * Builds and returns a Logger instance using the specified configuration parameters.
     *
     * @return A Logger instance configured with the specified parameters.
     * @throws IllegalArgumentException if an invalid port number is provided.
     */
    @Override
    public Logger build() {
        System.out.println("Logger Configuration - Server: " + host + ":" + port + ", Filter Level: " + filterLevel);
        this.logHandlerFile = new LogHandlerFile(path);
        this.logHandlerServer = new LogHandlerServer(host, port);
        return this;
    }

    /**
     * Sets the server host parameter for the Logger setup.
     *
     * @param inputServer The host name or IP address of the server to connect to.
     * @return A reference to this LoggerSetup instance to allow for method chaining.
     */
    @Override
    public LoggerSetup withHost(final String inputServer) {
        this.host = inputServer;
        return this;
    }

    /**
     * Sets the server port parameter for the Logger setup.
     *
     * @param inputPort The port number to use when connecting to the server.
     * @return A reference to this LoggerSetup instance to allow for method chaining.
     */
    @Override
    public LoggerSetup withPort(final int inputPort) {
        this.port = inputPort;
        return this;
    }

    /**
     * Sets the file path parameter for the Logger setup.
     *
     * @param inputPath The file path to use for logging messages to a file.
     * @return A reference to this LoggerSetup instance to allow for method chaining.
     */
    @Override
    public LoggerSetup withPath(final Path inputPath) {
        this.path = inputPath;
        return this;
    }

    /**
     * Sets the client name parameter for the Logger setup.
     *
     * @param inputClientName The name of the client that is logging messages.
     * @return A reference to this LoggerSetup instance to allow for method chaining.
     */
    @Override
    public LoggerSetup withName(final String inputClientName) {
        this.clientName = inputClientName;
        return this;
    }

    /**
     * Sets the message level filter for the logger component.
     *
     * @param inputFilterLevel the message level to filter messages by.
     * @return an instance of LoggerSetup instance to allow for method chaining.
     */
    @Override
    public LoggerSetup withMessageLevelFilter(final MessageLevel inputFilterLevel) {
        this.filterLevel = inputFilterLevel;
        return this;
    }

    /**
     * Sends the locally saved messages to the server.
     *
     * @return Whether the log messages were sent successfully or not
     */
    private boolean sendLocalLogs() {
        List<LogMessage> logMessages = logHandlerFile.getLocalLogs();
        logHandlerFile.clearLocalLogs();
        boolean sendMessageFailed = false;
        for (LogMessage logMessage : logMessages) {
            if (sendMessageFailed || !logHandlerServer.handleLog(logMessage)) {
                sendMessageFailed = true;
                logHandlerFile.handleLog(logMessage);
            }
        }
        return true;
    }
}
