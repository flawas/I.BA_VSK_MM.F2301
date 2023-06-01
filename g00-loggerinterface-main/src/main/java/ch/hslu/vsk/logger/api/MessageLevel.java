package ch.hslu.vsk.logger.api;

public enum MessageLevel {
    OFF(1),
    FATAL(100),
    ERROR(200),
    WARN(300),
    INFO(400),
    DEBUG(500),
    TRACE(600),
    ALL(Integer.MAX_VALUE),
    ;

    private final int intLevel;

    MessageLevel(int intLevel) {
        this.intLevel = intLevel;
    }

    public int getIntLevel() {
        return intLevel;
    }
}
