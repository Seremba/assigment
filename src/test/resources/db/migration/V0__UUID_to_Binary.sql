CREATE ALIAS UUID_TO_BIN AS $$
import java.util.UUID;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
@CODE

byte[] uuidToBin(String id) throws Exception {
  UUID uuid = UUID.fromString(id);
  byte[] uuidBytes = new byte[16];
  ByteBuffer.wrap(uuidBytes)
    .order(ByteOrder.BIG_ENDIAN)
    .putLong(uuid.getMostSignificantBits())
    .putLong(uuid.getLeastSignificantBits());
  return uuidBytes;
}
$$;
