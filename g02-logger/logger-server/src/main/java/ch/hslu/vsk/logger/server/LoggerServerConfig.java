package ch.hslu.vsk.logger.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * Implements the configuration of the Logger Server.
 */
public final class LoggerServerConfig {

    private InetAddress host;
    private int port;
    private Path logFile;
    private int maxClients = 100;

    /**
     * Constructs a new instance of the logger server config parsed from the command line arguments.
     *
     * @param args The command line arguments
     */
    public LoggerServerConfig(final String[] args) {
        if (args.length % 2 > 0) {
            throw new IllegalArgumentException("Incorrect number of arguments");
        }

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "--host" -> this.host = parseHost(args[i + 1]);
                case "--port" -> this.port = parsePort(args[i + 1]);
                case "--log-file" -> this.logFile = parseLogFile(args[i + 1]);
                case "--max-clients" -> this.maxClients = parseMaxClients(args[i + 1]);
                default -> throw new IllegalArgumentException("Invalid argument: " + args[i]);
            }
        }
    }

    /**
     * Sets the port and the log file configuration from the environment variable values.
     *
     * @param portEnv    The port environment variable value
     * @param logFileEnv The log file path environment variable value
     */
    public void setPortAndFileFromEnv(final String portEnv, final String logFileEnv) {
        if (!portEnv.isBlank()) {
            this.port = Integer.parseInt(portEnv);
        }

        if (!logFileEnv.isBlank()) {
            this.logFile = Path.of(logFileEnv);
        }
    }

    /**
     * Returns the host configuration parameter.
     *
     * @return The host configuration parameter
     */
    public InetAddress getHost() {
        return host;
    }

    /**
     * Returns the port configuration parameter.
     *
     * @return The port configuration parameter
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the log file path configuration parameter.
     *
     * @return The log file path configuration parameter
     */
    public Path getLogFile() {
        return logFile;
    }

    /**
     * Returns the maximal number of clients configuration parameter.
     *
     * @return The maximal number of clients configuration parameter
     */
    public int getMaxClients() {
        return maxClients;
    }

    /**
     * Parses the host argument.
     *
     * @param argument The argument from the command line
     * @return The host configuration parameter
     */
    private InetAddress parseHost(final String argument) {
        try {
            if (argument.isBlank()) {
                throw new IllegalArgumentException("Invalid argument host: should not be empty");
            }
            return InetAddress.getByName(argument);
        } catch (UnknownHostException ex) {
            throw new IllegalArgumentException("Invalid argument host: " + ex.getMessage());
        }
    }

    /**
     * Parses the port argument.
     *
     * @param argument The argument from the command line
     * @return The port configuration parameter
     */
    private int parsePort(final String argument) {
        try {
            int parsedPort = Integer.parseInt(argument);
            if (parsedPort < 1 || parsedPort > 65535) {
                throw new IllegalArgumentException("Invalid argument port: number out of range");
            }
            return parsedPort;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid argument port: " + ex.getMessage());
        }
    }

    /**
     * Parses the log file path argument.
     *
     * @param argument The argument from the command line
     * @return The log file path configuration parameter
     */
    private Path parseLogFile(final String argument) {
        try {
            if (argument.isBlank()) {
                throw new IllegalArgumentException("Invalid argument log-file: should not be empty");
            }
            return Path.of(argument);
        } catch (InvalidPathException ex) {
            throw new IllegalArgumentException("Invalid argument log-file: " + ex.getMessage());
        }
    }

    /**
     * Parses the maximal number of clients argument.
     *
     * @param argument The argument from the command line
     * @return The maximal number of clients configuration parameter
     */
    private int parseMaxClients(final String argument) {
        try {
            int numberOfClients = Integer.parseInt(argument);
            if (numberOfClients < 1) {
                throw new IllegalArgumentException("Invalid argument max-clients: number out of range");
            }
            return numberOfClients;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid argument max-clients: " + ex.getMessage());
        }
    }
}
