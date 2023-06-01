package ch.hslu.vsk.stringpersistor.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

/**
 * Implements the string persistor using a file to save the strings.
 */
public final class StringPersistorFile implements StringPersistor {

    private Path file;

    /**
     * Reads and returns the specified number of saved strings from the file.
     *
     * @param count The number of strings to read
     * @return The list of strings saved in the file.
     */
    @Override
    public List<PersistedString> get(final int count) {
        if (this.file == null) {
            throw new IllegalStateException("Log file not set.");
        }
        List<PersistedString> list = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(this.file), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            for (int i = 0; line != null && i < count; i++) {
                String[] splitLine = line.split(" - ");
                Instant timestamp = Instant.parse(splitLine[0]);
                String message = splitLine.length > 1 ? splitLine[1] : "";
                list.add(new PersistedString(timestamp, message));
                line = reader.readLine();
            }
        } catch (NoSuchFileException e) {
            throw new IllegalStateException("Log file not found (" + file + ")");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read from file." + e.getMessage());
        }
        return list;
    }

    /**
     * Saves a string to the file including a timestamp.
     *
     * @param timestamp The timestamp
     * @param payload   The string payload
     */
    @Override
    public void save(final Instant timestamp, final String payload) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Argument timestamp can't be null.");
        }
        if (payload == null) {
            throw new IllegalArgumentException("Argument payload can't be null.");
        }
        if (this.file == null) {
            throw new IllegalStateException("Log file not set.");
        }
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                Files.newOutputStream(this.file, StandardOpenOption.APPEND),
                                StandardCharsets.UTF_8)))) {
            writer.println(timestamp + " - " + payload);
        } catch (NoSuchFileException e) {
            throw new IllegalStateException("Log file not found (" + file + ")");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write to file." + e.getMessage());
        }
    }

    /**
     * Creates the file to save the strings to.
     *
     * @param path The file path
     */
    @Override
    public void setFile(final Path path) {
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("Argument path can't be null.");
        }
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("File could not be created." + e.getMessage());
            }
        }
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("A file must be specified but a directory must be given.");
        }
        this.file = path;
    }
}
