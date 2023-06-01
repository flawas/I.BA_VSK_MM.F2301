package ch.hslu.vsk.logger.viewer;

import jakarta.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.PayloadFormatStrategy;
import ch.hslu.vsk.logger.common.PayloadFormatStringStrategy;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

/**
 * The LoggerViewerServer class represents a server that provides a LoggerViewer
 * functionality.
 * It uses Jetty as the underlying HTTP server and WebSocket container.
 * The server listens for WebSocket connections on the "/viewer" path.
 * The server has an idle timeout of 30 minutes for WebSocket connections.
 */
public final class LoggerViewerServer implements Runnable {

    private static final int PORT = 7778;
    private final BlockingQueue<LogMessage> queue;
    private final LoggerViewerServerEndpoint endpoint = new LoggerViewerServerEndpoint();
    private final PayloadFormatStrategy payloadFormatter = new PayloadFormatStringStrategy();

    /**
     * Creates a new instance of the logger viewer server.
     *
     * @param queue The log message queue
     */
    public LoggerViewerServer(final BlockingQueue<LogMessage> queue) {
        this.queue = queue;
    }

    /**
     * Runs a LoggerViewer server on the specified port.
     * The server uses Jetty as the underlying HTTP server and WebSocket container.
     * The server listens for WebSocket connections on the "/viewer" path.
     * The server has an idle timeout of 30 minutes for WebSocket connections.
     */
    @Override
    public void run() {
        this.startServer();
        while (true) {
            this.readLogQueue();
        }
    }

    private void readLogQueue() {
        try {
            LogMessage message = queue.take();
            String payload = payloadFormatter.createPayload(message);
            endpoint.sendMessage(payload);
        } catch (InterruptedException e) {
            System.out.println("Logger Viewer - interrupted while waiting for log messages.");
        }
    }

    private void startServer() {
        Server server = new Server(PORT);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        server.setHandler(servletContextHandler);

        Servlet websocketServlet = new JettyWebSocketServlet() {
            @Override
            protected void configure(final JettyWebSocketServletFactory factory) {
                factory.setIdleTimeout(Duration.ofMinutes(30));
                factory.addMapping("/", (req, res) -> endpoint);
            }
        };
        servletContextHandler.addServlet(new ServletHolder(websocketServlet), "/viewer");
        JettyWebSocketServletContainerInitializer.configure(servletContextHandler, null);

        try {
            server.start();
            System.out.println("Logger Viewer - listening for client connection on " + PORT);
        } catch (Exception e) {
            System.out.println("Logger Viewer - failed to start Logger Viewer server: " + e.getMessage());
        }
    }
}
