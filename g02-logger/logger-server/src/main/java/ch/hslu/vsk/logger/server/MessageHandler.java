package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.LogMessage;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Handles incoming log messages.
 */
public final class MessageHandler implements Runnable {

    private final Socket socket;
    private final BlockingQueue<LogMessage> logQueue;
    private final BlockingQueue<LogMessage> viewerQueue;

    /**
     * Creates a new instance of the message handler.
     *
     * @param socket      The connected socket
     * @param logQueue    The log message queue
     * @param viewerQueue The log viewer queue
     */
    public MessageHandler(final Socket socket, final BlockingQueue<LogMessage> logQueue,
                          final BlockingQueue<LogMessage> viewerQueue) {
        this.socket = socket;
        this.logQueue = logQueue;
        this.viewerQueue = viewerQueue;
    }

    /**
     * Handles incoming log messages of a client.
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            while (!socket.isClosed()) {
                LogMessage logMessage = LogMessage.getMessage(in);
                logQueue.put(logMessage);
                viewerQueue.add(logMessage);
            }
        } catch (EOFException e) {
            System.out.println("A client closed the connection");
        } catch (IOException e) {
            System.out.println("An error occurred while processing the log messages: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Failed to add message to log queue: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Failed to add message to viewer queue: " + e.getMessage());
        }
    }
}
