package ch.hslu.vsk.stringpersistor.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

final class StringPersistorFileTest {
    private static final File TEST_FILE = new File("test.txt");

    @BeforeEach
    void deleteFile() {
        TEST_FILE.delete();
    }

    @Test
    void testSetDirectory() {
        StringPersistor persistor = new StringPersistorFile();
        assertThatThrownBy(() -> persistor.setFile(new File("target").toPath()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A file must be specified but a directory must be given.");
    }

    @Test
    void testSetNull() {
        StringPersistor persistor = new StringPersistorFile();
        assertThatThrownBy(() -> persistor.setFile(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Argument path can't be null.");
    }

    @Test
    void testSetInvalid() {
        StringPersistor persistor = new StringPersistorFile();
        assertThatThrownBy(() -> persistor.setFile(Path.of(":/does/not/exist")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("File could not be created.");
    }

    @Test
    void testGetEmptyFile() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        List<PersistedString> logEntries = persistor.get(Integer.MAX_VALUE);
        assertThat(logEntries.size()).isEqualTo(0);
    }

    @Test
    void testGetNegative() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1");
        persistor.save(Instant.parse("2019-01-02T11:12:56.00Z"), "test_string_2");
        List<PersistedString> logEntries = persistor.get(-1);
        assertThat(logEntries.size()).isEqualTo(0);
    }

    @Test
    void testGet0() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1");
        persistor.save(Instant.parse("2019-01-02T11:12:56.00Z"), "test_string_2");
        List<PersistedString> logEntries = persistor.get(0);
        assertThat(logEntries.size()).isEqualTo(0);
    }

    @Test
    void testGet1() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1");
        persistor.save(Instant.parse("2019-01-02T11:12:56.00Z"), "test_string_2");
        List<PersistedString> logEntries = persistor.get(1);
        assertThat(logEntries.size()).isEqualTo(1);
        assertThat(logEntries.get(0))
                .isEqualTo(new PersistedString(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1"));
    }

    @Test
    void testGetAll() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1");
        persistor.save(Instant.parse("2019-01-02T11:12:56.00Z"), "test_string_2");
        List<PersistedString> logEntries = persistor.get(Integer.MAX_VALUE);
        assertThat(logEntries.get(0))
                .isEqualTo(new PersistedString(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string_1"));
        assertThat(logEntries.get(1))
                .isEqualTo(new PersistedString(Instant.parse("2019-01-02T11:12:56.00Z"), "test_string_2"));
    }

    @Test
    void testGetFileNotSet() {
        StringPersistor persistor = new StringPersistorFile();
        assertThatThrownBy(() -> persistor.get(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Log file not set.");
    }

    @Test
    void testGetFileNotFound() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        TEST_FILE.delete();
        assertThatThrownBy(() -> persistor.get(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Log file not found (test.txt)");
    }

    @Test
    void testSave() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string");
        assertThat(persistor.get(1).get(0))
                .isEqualTo(new PersistedString(Instant.parse("2018-11-30T18:35:24.00Z"), "test_string"));
    }

    @Test
    void testSaveEmptyMessage() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), "");
        assertThat(persistor.get(1).get(0))
                .isEqualTo(new PersistedString(Instant.parse("2018-11-30T18:35:24.00Z"), ""));
    }

    @Test
    void testSaveFileNotSet() {
        StringPersistor persistor = new StringPersistorFile();
        assertThatThrownBy(() -> persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), ""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Log file not set.");
    }

    @Test
    void testSaveNoTimestamp() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        assertThatThrownBy(() -> persistor.save(null, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Argument timestamp can't be null.");
    }

    @Test
    void testSaveNoPayload() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        assertThatThrownBy(() -> persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Argument payload can't be null.");
    }

    @Test
    void testSaveFileNotFound() {
        StringPersistor persistor = new StringPersistorFile();
        persistor.setFile(TEST_FILE.toPath());
        TEST_FILE.delete();
        assertThatThrownBy(() -> persistor.save(Instant.parse("2018-11-30T18:35:24.00Z"), ""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Log file not found (test.txt)");
    }

    @Test
    void testSave1000Objects() {
        StringPersistor persistor = new StringPersistorFile();
        Instant instant = Instant.parse("2018-11-30T18:35:24.00Z");
        persistor.setFile(TEST_FILE.toPath());
        for (int i = 0; i < 1000; i++) {
            persistor.save(instant, "string " + i);
        }
        List<PersistedString> persistedStrings = persistor.get(Integer.MAX_VALUE);
        assertThat(persistedStrings.size()).isEqualTo(1000);
        ListIterator<PersistedString> iterator = persistedStrings.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            assertThat(iterator.next()).isEqualTo(new PersistedString(instant, "string " + index));
            index++;
        }
    }

    @Test
    void testGet100ObjectsIn200MS() {
        StringPersistor persistor = new StringPersistorFile();
        Instant instant = Instant.parse("2018-11-30T18:35:24.00Z");
        String string = this.createRandomString(999);
        persistor.setFile(TEST_FILE.toPath());
        for (int i = 0; i < 100; i++) {
            persistor.save(instant, string + i);
        }
        long start = System.nanoTime();
        List<PersistedString> persistedStrings = persistor.get(Integer.MAX_VALUE);
        long finish = System.nanoTime();
        assertThat(persistedStrings.size()).isEqualTo(100);
        assertThat((finish - start) / 1_000_000).isLessThan(200);
        ListIterator<PersistedString> iterator = persistedStrings.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            assertThat(iterator.next()).isEqualTo(new PersistedString(instant, string + index));
            index++;
        }
    }

    private String createRandomString(final int length) {
        byte[] array = new byte[length];
        return new String(array, StandardCharsets.UTF_8);
    }
}
