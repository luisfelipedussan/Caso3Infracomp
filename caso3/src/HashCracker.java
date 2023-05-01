import java.security.MessageDigest;

public class HashCracker {
    
   
        private final MessageDigest digest;
        private final String hash;
        private final String salt;

        HashCracker(MessageDigest digest, String hash, String salt) {
            this.digest = digest;
            this.hash = hash;
            this.salt = salt;
        }

        String crackHash(int start, int stride) {
            for (int i = start; i < Integer.MAX_VALUE; i += stride) {
                String candidate = Integer.toString(i);
                byte[] hashBytes = digest.digest((candidate + salt).getBytes());

                if (bytesToHex(hashBytes).equals(hash)) {
                    return candidate;
                }
            }
            return null;
        }

        private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

        private static String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        
    }
}
