/**
 * The LogMessage class represents a log message including a timestamp, the level, the source, and the message itself.
 *
 * @author Lorin Bucher, Sandro Hilpert, Flavio Waser, Peter Zürcher
 */
package ch.hslu.vsk.logger.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * Implements the Log Message format.
 *
 * @param timeStamp The timestamp of the log message
 * @param level     The log level of the log message
 * @param source    The source of the log message
 * @param message   The log message
 */
public record LogMessage(Instant timeStamp, String level, String source, String message) {

    /**
     * Gets a log message from a DataInputStream.
     *
     * @param is The DataInputStream to get the log message from
     * @return A LogMessage object with the data from the DataInputStream
     * @throws IOException If an I/O error occurs while getting the log message
     */
    public static LogMessage getMessage(final DataInputStream is) throws IOException {
        byte[] levelIn = new byte[is.readInt()];
        byte[] sourceIn = new byte[is.readInt()];
        byte[] messageIn = new byte[is.readInt()];

        long timeStampIn = is.readLong();
        is.readFully(levelIn);
        is.readFully(sourceIn);
        is.readFully(messageIn);

        Instant timeStamp = Instant.ofEpochMilli(timeStampIn);
        String level = new String(levelIn, StandardCharsets.UTF_8);
        String source = new String(sourceIn, StandardCharsets.UTF_8);
        String message = new String(messageIn, StandardCharsets.UTF_8);

        return new LogMessage(timeStamp, level, source, message);
    }

    /**
     * Sends a log message to the DataOutputStream.
     *
     * @param os         The DataOutputStream to send the log message to
     * @param logMessage The LogMessage to send
     * @throws IOException If an I/O error occurs while sending the log message
     */
    public static void sendMessage(final DataOutputStream os, final LogMessage logMessage) throws IOException {
        byte[] levelOut = logMessage.level.getBytes(StandardCharsets.UTF_8);
        byte[] sourceOut = logMessage.source.getBytes(StandardCharsets.UTF_8);
        byte[] messageOut = logMessage.message.getBytes(StandardCharsets.UTF_8);

        os.writeInt(levelOut.length);
        os.writeInt(sourceOut.length);
        os.writeInt(messageOut.length);

        os.writeLong(logMessage.timeStamp.toEpochMilli());
        os.write(levelOut);
        os.write(sourceOut);
        os.write(messageOut);

        os.flush();
    }
}
