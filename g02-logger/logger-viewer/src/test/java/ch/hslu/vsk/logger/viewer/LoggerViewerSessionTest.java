package ch.hslu.vsk.logger.viewer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerViewerSessionTest {

    private final LoggerViewerSession loggerViewerSession = new LoggerViewerSession();
    private final DummySession session = new DummySession();

    @Test
    void add() {
        loggerViewerSession.add(session);
        String log = loggerViewerSession.getSessions().toString();
        assertTrue(log.contains("DummySession"));
    }

    @Test
    void remove() {
        loggerViewerSession.add(session);
        loggerViewerSession.remove(session);
        String log = loggerViewerSession.getSessions().toString();
        assertEquals("[]", log);
    }
}
