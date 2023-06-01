package ch.hslu.vsk.logger.viewer;

import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;

import java.net.SocketAddress;
import java.time.Duration;

public final class DummySession implements Session {

    private final RemoteEndpoint remoteEndpoint;
    private boolean open;

    public DummySession() {
        this.remoteEndpoint = new DummyRemoteEndpoint();
        this.open = true;
    }

    @Override
    public void close() {
        open = false;
    }

    @Override
    public void close(final CloseStatus closeStatus) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void close(final int statusCode, final String reason) {
        // Do nothing in the dummy implementation
    }

    @Override
    public void disconnect() {
        // Do nothing in the dummy implementation
    }

    @Override
    public SocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocolVersion() {
        return null;
    }

    @Override
    public RemoteEndpoint getRemote() {
        return remoteEndpoint;
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public UpgradeRequest getUpgradeRequest() {
        return null;
    }

    @Override
    public UpgradeResponse getUpgradeResponse() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public SuspendToken suspend() {
        return null;
    }

    @Override
    public WebSocketBehavior getBehavior() {
        return null;
    }

    @Override
    public Duration getIdleTimeout() {
        return null;
    }

    @Override
    public int getInputBufferSize() {
        return 0;
    }

    @Override
    public int getOutputBufferSize() {
        return 0;
    }

    @Override
    public long getMaxBinaryMessageSize() {
        return 0;
    }

    @Override
    public long getMaxTextMessageSize() {
        return 0;
    }

    @Override
    public long getMaxFrameSize() {
        return 0;
    }

    @Override
    public boolean isAutoFragment() {
        return false;
    }

    @Override
    public void setIdleTimeout(final Duration duration) {

    }

    @Override
    public void setInputBufferSize(final int size) {

    }

    @Override
    public void setOutputBufferSize(final int size) {

    }

    @Override
    public void setMaxBinaryMessageSize(final long size) {

    }

    @Override
    public void setMaxTextMessageSize(final long size) {

    }

    @Override
    public void setMaxFrameSize(final long maxFrameSize) {

    }

    @Override
    public void setAutoFragment(final boolean autoFragment) {

    }
}
