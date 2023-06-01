package ch.hslu.vsk.logger.viewer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import java.io.IOException;

/**
 * WebSocket endpoint for the LoggerViewer server.
 * Manages WebSocket connection events and message handling.
 */
public final class LoggerViewerServerEndpoint implements WebSocketListener {

    private static final LoggerViewerSession LOGGER_VIEWER_SESSION = new LoggerViewerSession();

    /**
     * Called when a WebSocket connection is established.
     * Adds the session to the LoggerViewerSession and sends a connection message to the client.
     *
     * @param session the WebSocket session
     */
    @Override
    public void onWebSocketConnect(final Session session) {
        System.out.println("Logger Viewer - a client connected");
        LOGGER_VIEWER_SESSION.add(session);
        try {
            session.getRemote().sendString("Successfully connected to server");
        } catch (IOException e) {
            System.out.println("Logger Viewer - failed to send initial connected message: " + e.getMessage());
        }
    }

    /**
     * Called when a WebSocket connection is closed.
     * Removes the session from the LoggerViewerSession and broadcasts the disconnection message.
     *
     * @param statusCode the status code indicating the reason for closure
     * @param reason     the reason for closure
     */
    @Override
    public void onWebSocketClose(final int statusCode, final String reason) {
        System.out.println("Logger Viewer - a client disconnected");
    }

    /**
     * Called when a WebSocket error occurs.
     * Removes the session from the LoggerViewerSession and prints an error message.
     *
     * @param cause the error cause
     */
    @Override
    public void onWebSocketError(final Throwable cause) {
        System.out.println("Logger Viewer - a connection error occurred: " + cause);
    }

    /**
     * Called when a WebSocket text message is received.
     * Broadcasts the message to all connected sessions and prints the message.
     *
     * @param message the received text message
     */
    @Override
    public void onWebSocketText(final String message) {
        LOGGER_VIEWER_SESSION.broadcast(message);
        System.out.println("Logger Viewer - received message: " + message);
    }

    /**
     * Broadcasts a message to all connected clients.
     *
     * @param message The message to broadcast
     */
    public void sendMessage(final String message) {
        LOGGER_VIEWER_SESSION.broadcast(message);
    }
}
