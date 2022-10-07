import java.security.*;
public class Mining {
    //mining problem method
    public static void miningProblem() {
        int difficulty = 5;
        String target = new String(new char[difficulty]).replace('\0', '0');
        String hash = "";
        int nonce = 0;
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash(nonce);
        }
        System.out.println("Block Mined!!! : " + hash);
    }
    //calculate hash method
    public static String calculateHash(int nonce) {
        String dataToHash = "I am Satoshi Nakamoto" + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(dataToHash.getBytes("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1)
                    buffer.append('0');
                buffer.append(hex);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
