import java.security.*;
public class Blockchain {
    class Block {
        String hash;
        String prevHash;
        int data;
        long timeStamp;
    }
    Block genesisBlock;
    //constructor
    public Blockchain() {
        genesisBlock = new Block();
        genesisBlock.data = 0;
        genesisBlock.timeStamp = System.currentTimeMillis();
        genesisBlock.prevHash = "";
        genesisBlock.hash = calculateHash(genesisBlock);
    }
    //insert mthod for blockchain
    public void addBlock(int data) {
        Block newBlock = new Block();
        newBlock.data = data;
        newBlock.timeStamp = System.currentTimeMillis();
        newBlock.prevHash = genesisBlock.hash;
        newBlock.hash = calculateHash(newBlock);
        genesisBlock = newBlock;
    }
    //calculate hash method
    public String calculateHash(Block block) {
        String dataToHash = block.prevHash + Long.toString(block.timeStamp) + Integer.toString(block.data);
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
