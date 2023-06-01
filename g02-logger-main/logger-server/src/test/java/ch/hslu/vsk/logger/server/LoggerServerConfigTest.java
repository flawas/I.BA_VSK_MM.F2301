package ch.hslu.vsk.logger.server;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoggerServerConfigTest {

    @Test
    void testArgsEmpty() {
        LoggerServerConfig config = new LoggerServerConfig(new String[0]);
        assertThat(config.getHost()).isNull();
        assertThat(config.getPort()).isEqualTo(0);
        assertThat(config.getLogFile()).isNull();
        assertThat(config.getMaxClients()).isEqualTo(100);
    }

    @Test
    void testInvalidNumberOfArgs() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--host"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Incorrect number of arguments");
    }

    @Test
    void testInvalidArg() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--test", "Test"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument: --test");
    }

    @Test
    void testSetPortAndFileFromEnvEmpty() {
        LoggerServerConfig config = new LoggerServerConfig(new String[0]);
        config.setPortAndFileFromEnv("", "");
        assertThat(config.getPort()).isEqualTo(0);
        assertThat(config.getLogFile()).isNull();
    }

    @Test
    void testSetPortAndFileFromEnvPort() {
        LoggerServerConfig config = new LoggerServerConfig(new String[0]);
        config.setPortAndFileFromEnv("7777", "");
        assertThat(config.getPort()).isEqualTo(7777);
    }

    @Test
    void testSetPortAndFileFromEnvLogFile() {
        LoggerServerConfig config = new LoggerServerConfig(new String[0]);
        config.setPortAndFileFromEnv("", "/path/to/file.log");
        assertThat(config.getLogFile()).isEqualTo(Path.of("/path/to/file.log"));
    }

    @Test
    void testParseHost() {
        LoggerServerConfig config = new LoggerServerConfig(new String[]{"--host", "0.0.0.0"});
        try {
            assertThat(config.getHost()).isEqualTo(InetAddress.getByName("0.0.0.0"));
        } catch (UnknownHostException e) {
            assertThat(e).isNull();
        }
    }

    @Test
    void testParseHostEmpty() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--host", ""}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument host: should not be empty");
    }

    @Test
    void testParseHostInvalid() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--host", "Invalid"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument host:");
    }

    @Test
    void testParsePort() {
        LoggerServerConfig config = new LoggerServerConfig(new String[]{"--port", "7777"});
        assertThat(config.getPort()).isEqualTo(7777);
    }

    @Test
    void testParsePortEmpty() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--port", ""}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument port:");
    }

    @Test
    void testParsePortInvalid() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--port", "Test"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument port:");
    }

    @Test
    void testParsePortInvalidRangeToLow() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--port", "-1"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument port: number out of range");
    }

    @Test
    void testParsePortInvalidRangeToHigh() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--port", "65536"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument port: number out of range");
    }

    @Test
    void testParseLogFile() {
        LoggerServerConfig config = new LoggerServerConfig(new String[]{"--log-file", "test.log"});
        assertThat(config.getLogFile()).isEqualTo(Path.of("test.log"));
    }

    @Test
    void testParseLogFileEmpty() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--log-file", ""}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid argument log-file: should not be empty");
    }

    @Test
    void testParseLogFileInvalid() {
        byte[] array = new byte[5000];
        new Random().nextBytes(array);
        String path = new String(array, StandardCharsets.UTF_8);
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--log-file", path}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument log-file:");
    }

    @Test
    void testParseMaxClients() {
        LoggerServerConfig config = new LoggerServerConfig(new String[]{"--max-clients", "50"});
        assertThat(config.getMaxClients()).isEqualTo(50);
    }

    @Test
    void testParseMaxClientsEmpty() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--max-clients", ""}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument max-clients:");
    }

    @Test
    void testParseMaxClientsInvalid() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--max-clients", "Test"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument max-clients:");
    }

    @Test
    void testParseMaxClientsInvalidRange() {
        assertThatThrownBy(() -> new LoggerServerConfig(new String[]{"--max-clients", "0"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Invalid argument max-clients: number out of range");
    }
}
