package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.viewer.LoggerViewerServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements the Logger Server.
 */
public final class LoggerServer {

    private final LoggerServerConfig config;
    private final BlockingQueue<LogMessage> logQueue = new LinkedBlockingQueue<>(100_000);
    private final BlockingQueue<LogMessage> viewerQueue = new LinkedBlockingQueue<>(100_000);
    private final MessagePersistor messagePersistor;
    private final LoggerViewerServer loggerViewerServer;

    /**
     * Constructs a new instance of the Logger Server.
     *
     * @param config The server configuration
     */
    public LoggerServer(final LoggerServerConfig config) {
        this.config = config;
        this.messagePersistor = new MessagePersistor(config.getLogFile(), logQueue);
        this.loggerViewerServer = new LoggerViewerServer(viewerQueue);
    }

    /**
     * Starts the logger server application.
     */
    public void start() {
        final ExecutorService clientExecutor = Executors.newFixedThreadPool(config.getMaxClients());
        final ExecutorService logExecutor = Executors.newSingleThreadExecutor();
        final ExecutorService viewerExecutor = Executors.newSingleThreadExecutor();

        logExecutor.execute(messagePersistor);
        viewerExecutor.execute(loggerViewerServer);

        try (ServerSocket listen = new ServerSocket(config.getPort(), 0, config.getHost())) {
            while (true) {
                System.out.println(
                        "Listening for client connection on " + config.getHost() + ":" + config.getPort() + "...");

                Socket client = listen.accept();
                System.out.println("Client connected: " + client.getInetAddress().getHostName());

                MessageHandler handler = new MessageHandler(client, logQueue, viewerQueue);
                clientExecutor.execute(handler);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while listening for new client connections: " + e.getMessage());
        }
    }

    /**
     * Runs the Logger Server application.
     *
     * @param args Arguments
     */
    public static void main(final String[] args) {
        String portEnv = System.getenv().getOrDefault("LISTEN_PORT", "");
        String logFileEnv = System.getenv().getOrDefault("LOG_FILE", "");

        LoggerServerConfig config = new LoggerServerConfig(args);
        config.setPortAndFileFromEnv(portEnv, logFileEnv);

        LoggerServer server = new LoggerServer(config);
        server.start();
    }
}
