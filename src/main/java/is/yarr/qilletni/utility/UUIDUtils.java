package is.yarr.qilletni.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A class for utilities relating to {@link UUID}s.
 */
public class UUIDUtils {

    /**
     * Converts an array of bytes to a {@link UUID}.
     *
     * @param bytes The byte array to convert
     * @return The created {@link UUID}
     */
    public static UUID convertBytesToUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    /**
     * Converts a {@link UUID} into an array of bytes.
     *
     * @param uuid The {@link UUID} to convert
     * @return The created byte array
     */
    public static byte[] convertUUIDToBytes(UUID uuid) {
        return ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
    }

}
