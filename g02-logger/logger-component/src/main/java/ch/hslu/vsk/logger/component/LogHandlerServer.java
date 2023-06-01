package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.common.LogMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Implements remote log message handling using a logging server.
 */
public final class LogHandlerServer implements LogHandler {

    private final String host;
    private final int port;

    private Socket socket;
    private DataOutputStream out;
    private boolean connected = false;

    /**
     * Constructs a new instance of the server log message handler.
     *
     * @param host The server host address
     * @param port The server port
     */
    public LogHandlerServer(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Returns whether the connection to the server is established or not.
     *
     * @return Whether the connection is established or not
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Establishes a connection to the server.
     */
    public void connectToServer() {
        System.out.println("Try connecting to " + host + ":" + port);
        try {
            this.socket = new Socket(this.host, this.port);
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.connected = true;
            System.out.println("Successfully connected to server");
        } catch (IOException e) {
            System.out.println("Failed to connect to server");
            this.socket = null;
            this.out = null;
            this.connected = false;
        }
    }

    @Override
    public boolean handleLog(final LogMessage logMessage) {
        try {
            if (this.connected) {
                LogMessage.sendMessage(this.out, logMessage);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            this.connected = false;
            System.out.println("Failed to send log message to server");
            return false;
        }
    }
}
