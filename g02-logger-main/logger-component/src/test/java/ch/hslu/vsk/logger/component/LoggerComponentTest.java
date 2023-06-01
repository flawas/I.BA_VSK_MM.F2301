package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.MessageLevel;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerComponentTest {

    private final Logger loggerComponent;
    private static final Path PATH = Path.of("LoggerComponentTest.log");

    LoggerComponentTest() throws IOException {
        this.loggerComponent = new LoggerComponent()
                .withHost("127.0.0.1")
                .withPort(7777)
                .withName("Test Component")
                .withMessageLevelFilter(MessageLevel.INFO)
                .withPath(PATH)
                .build();
    }

    public String readFile() throws IOException {
        FileReader fr = new FileReader(PATH.toFile());   //reads the file
        BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
        StringBuilder sb = new StringBuilder();    //constructs a string builder with no characters
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);      //appends line to string buffer
            sb.append("\n");     //line feed
        }
        fr.close();    //closes the stream and release the resources
        new FileWriter(PATH.toFile(), false).close();
        return sb.toString();   //returns a string that textually represents the object
    }

    public void generateLogs() {
        loggerComponent.log(MessageLevel.FATAL, "Fatal Log");
        loggerComponent.log(MessageLevel.ERROR, "Error Log");
        loggerComponent.log(MessageLevel.WARN, "Warn Log");
        loggerComponent.log(MessageLevel.INFO, "Info Log");
        loggerComponent.log(MessageLevel.DEBUG, "Debug Log");
        loggerComponent.log(MessageLevel.TRACE, "Trace Log");
    }

    @Test
    void log() throws IOException {
        loggerComponent.log(MessageLevel.ERROR, "Test Message");
        assertThat(readFile()).contains("Test Message");
    }

    @Test
    void setMessageLevelFilterOff() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.OFF);
        generateLogs();
        String file = readFile();
        assertThat(file).doesNotContain("FATAL");
        assertThat(file).doesNotContain("ERROR");
        assertThat(file).doesNotContain("WARN");
        assertThat(file).doesNotContain("INFO");
        assertThat(file).doesNotContain("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterFatal() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.FATAL);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).doesNotContain("ERROR");
        assertThat(file).doesNotContain("WARN");
        assertThat(file).doesNotContain("INFO");
        assertThat(file).doesNotContain("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterError() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.ERROR);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).doesNotContain("WARN");
        assertThat(file).doesNotContain("INFO");
        assertThat(file).doesNotContain("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterWarn() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.WARN);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).contains("WARN");
        assertThat(file).doesNotContain("INFO");
        assertThat(file).doesNotContain("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterInfo() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.INFO);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).contains("WARN");
        assertThat(file).contains("INFO");
        assertThat(file).doesNotContain("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterDebug() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.DEBUG);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).contains("WARN");
        assertThat(file).contains("INFO");
        assertThat(file).contains("DEBUG");
        assertThat(file).doesNotContain("TRACE");
    }

    @Test
    void setMessageLevelFilterTrace() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.TRACE);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).contains("WARN");
        assertThat(file).contains("INFO");
        assertThat(file).contains("DEBUG");
        assertThat(file).contains("TRACE");
    }

    @Test
    void setMessageLevelFilterAll() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.ALL);
        generateLogs();
        String file = readFile();
        assertThat(file).contains("FATAL");
        assertThat(file).contains("ERROR");
        assertThat(file).contains("WARN");
        assertThat(file).contains("INFO");
        assertThat(file).contains("DEBUG");
        assertThat(file).contains("TRACE");
    }

    @Test
    void build() {
    }

    @Test
    void withHost() {
    }

    @Test
    void withPort() {
    }

    @Test
    void withPath() {
        assertThat("LoggerComponentTest.log").isEqualTo(PATH.toString());
    }

    @Test
    void withName() throws IOException {
        loggerComponent.log(MessageLevel.ERROR, "Test Message");
        assertThat(readFile()).contains("Test Component");
    }

    @Test
    void withMessageLevelFilter() throws IOException {
        loggerComponent.setMessageLevelFilter(MessageLevel.INFO);
        generateLogs();
        assertThat(readFile()).doesNotContain("Debug");
    }
}
