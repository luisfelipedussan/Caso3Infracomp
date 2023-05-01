import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCracker {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(args);
        String algorithm = args[0];
        String hash = args[1];
        String salt = args[2];
        int numThreads = Integer.parseInt(args[3]);

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        HashCracker cracker = new HashCracker(digest, hash, salt);

        if (numThreads == 1) {
            String password = cracker.crackHash(0, 1);
            System.out.println("Password encontrado: " + password);
        } else if (numThreads == 2) {
            Thread t1 = new Thread(() -> {
                String password = cracker.crackHash(0, 2);
                System.out.println("Password encontrado por hilo 1: " + password);
            });

            Thread t2 = new Thread(() -> {
                String password = cracker.crackHash(1, 2);
                System.out.println("Password encontrado por hilo 2: " + password);
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class HashCracker {
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
}
