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

    
}
