package ch.hslu.vsk.demoapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Properties;

import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import ch.hslu.vsk.logger.api.MessageLevel;

/**
 * Demo-Applikation für LoggerComponent-Klasse.
 */
public final class DemoApp {

    public static void main(final String[] args)
            throws IllegalArgumentException, IOException, ReflectiveOperationException {
        Properties properties = new Properties();
        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                + "app.properties";
        properties.load(new FileInputStream(propertiesPath));
        Logger logger = LoggerSetup.fromClass(properties.getProperty("logger.class"))
                .withPort(Integer.parseInt(properties.getProperty("logger.port")))
                .withHost(properties.getProperty("logger.server"))
                .withMessageLevelFilter(MessageLevel.INFO)
                .withPath(Path.of(properties.getProperty("logger.path")))
                .withName("Demo App")
                .build();
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = userIn.readLine();
            logger.log(MessageLevel.INFO, input);
        }
    }
}
