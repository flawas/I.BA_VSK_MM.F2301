package ch.hslu.vsk.logger.viewer;

import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.exceptions.WebSocketException;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public final class DummyRemoteEndpoint implements RemoteEndpoint {

    @Override
    public void sendBytes(final ByteBuffer data) throws IOException {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendBytes(final ByteBuffer data, final WriteCallback callback) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPartialBytes(final ByteBuffer fragment, final boolean isLast) throws IOException {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPartialBytes(final ByteBuffer fragment, final boolean isLast, final WriteCallback callback) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendString(final String s) throws IOException, WebSocketException {
        System.out.println(s);
    }

    @Override
    public void sendString(final String text, final WriteCallback callback) {

    }

    @Override
    public void sendPartialString(final String fragment, final boolean isLast) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPartialString(final String fragment, final boolean isLast, final WriteCallback callback)
            throws IOException {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPing(final ByteBuffer applicationData) throws IOException {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPing(final ByteBuffer applicationData, final WriteCallback callback) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPong(final ByteBuffer applicationData) throws IOException {
        // Do nothing in the dummy implementation
    }

    @Override
    public void sendPong(final ByteBuffer applicationData, final WriteCallback callback) {
        // Do nothing in the dummy implementation
    }

    @Override
    public BatchMode getBatchMode() {
        return null;
    }

    @Override
    public void setBatchMode(final BatchMode mode) {
        // Do nothing in the dummy implementation
    }

    @Override
    public int getMaxOutgoingFrames() {
        return 0;
    }

    @Override
    public void setMaxOutgoingFrames(final int maxOutgoingFrames) {
        // Do nothing in the dummy implementation
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return null;
    }


    @Override
    public void flush() throws IOException {
        // Do nothing in the dummy implementation
    }
}