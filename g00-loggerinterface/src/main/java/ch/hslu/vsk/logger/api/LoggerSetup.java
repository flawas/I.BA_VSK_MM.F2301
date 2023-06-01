package ch.hslu.vsk.logger.api;

import java.io.IOException;
import java.nio.file.Path;

public interface LoggerSetup {
    Logger build() throws IllegalArgumentException, IOException;

    LoggerSetup withHost(String host);

    LoggerSetup withPort(int port);

    LoggerSetup withPath(Path path);

    LoggerSetup withName(String name);

    LoggerSetup withMessageLevelFilter(MessageLevel messageLevelFilter);

    static LoggerSetup fromClass(String className) throws ReflectiveOperationException {
        var loggerClass = Class.forName(className);
        return (LoggerSetup) loggerClass.getDeclaredConstructor().newInstance();
    }
}


