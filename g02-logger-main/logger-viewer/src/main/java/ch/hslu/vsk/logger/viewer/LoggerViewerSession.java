package ch.hslu.vsk.logger.viewer;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages the WebSocket sessions for the LoggerViewer server.
 * Provides methods to add and remove sessions, as well as broadcast messages to all connected sessions.
 */
public final class LoggerViewerSession {

    private final List<Session> sessions = new LinkedList<>();

    /**
     * Adds a WebSocket session to the list of sessions.
     *
     * @param session the WebSocket session to add
     */
    public void add(final Session session) {
        sessions.add(session);
    }

    /**
     * Removes a WebSocket session from the list of sessions.
     *
     * @param session the WebSocket session to remove
     */
    public void remove(final Session session) {
        sessions.remove(session);
    }

    /**
     * Broadcasts a message to all connected WebSocket sessions.
     * If an IOException occurs while sending the message, the corresponding session is removed from the broadcast list.
     *
     * @param message the message to broadcast
     */
    public void broadcast(final String message) {
        Iterator<Session> it = sessions.iterator();
        while (it.hasNext()) {
            Session session = it.next();
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                it.remove();
                if (e.getMessage().equals("java.nio.channels.ClosedChannelException")) {
                    System.out.println("Logger Viewer - disconnected client removed from the broadcast list");
                } else {
                    System.out.println("Logger Viewer - client removed from the broadcast list due to an error: " + e);
                }
            }
        }
    }

    /**
     * Returns the list of client sessions.
     *
     * @return The client sessions
     */
    public List<Session> getSessions() {
        return List.copyOf(sessions);
    }
}
