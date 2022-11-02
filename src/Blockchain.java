import java.security.*;
public class Blockchain {
    class Block {
        String hash;
        String prevHash;
        Block next;
        Block prev;
        //int data;
        Transaction transaction;
        long timeStamp;
        // Constructor
        Block() {
            next = null;
            prev = null;
        }
    }
    Block genesisBlock;
    Block omegaBlock;
    //constructor
    public Blockchain() {
        genesisBlock = new Block();
        genesisBlock.timeStamp = System.currentTimeMillis();
        genesisBlock.prevHash = "";
        genesisBlock.hash = calculateHash(genesisBlock);
        omegaBlock = genesisBlock;
    }
    //add a new block to the blockchain
    public void addBlock(Transaction transaction) {
        Block newBlock = new Block();
        //newBlock.data = data;
        //assign transaction details to new block
        newBlock.transaction = new Transaction();
        newBlock.transaction.sender = transaction.sender;
        newBlock.transaction.receiver = transaction.receiver;
        newBlock.transaction.amount = transaction.amount;
        newBlock.timeStamp = System.currentTimeMillis();
        newBlock.prevHash = omegaBlock.hash;
        newBlock.hash = calculateHash(newBlock);
        omegaBlock.next = newBlock;
        newBlock.prev = omegaBlock;
        omegaBlock = newBlock;
    }
    //traversal the blockchain
    public void traverse() {
        Block currentBlock = genesisBlock;
        while (currentBlock != null) {
            if (currentBlock.transaction != null) {
                System.out.println(currentBlock.transaction.toString());
            }
            System.out.println("Hash: " + currentBlock.hash);
            System.out.println("PrevHash: " + currentBlock.prevHash);
            System.out.println("Timestamp: " + currentBlock.timeStamp);
            System.out.println();
            currentBlock = currentBlock.next;
        }
    }
    //delete a block from the blockchain
    /*public void deleteBlock(int data) {
        Block currentBlock = genesisBlock;
        while (currentBlock != null) {
            if (currentBlock.data == data) {
                if (currentBlock == genesisBlock) {
                    genesisBlock = genesisBlock.next;
                    genesisBlock.prev = null;
                } else if (currentBlock == omegaBlock) {
                    omegaBlock = omegaBlock.prev;
                    omegaBlock.next = null;
                } else {
                    currentBlock.prev.next = currentBlock.next;
                    //exchange hashes
                    currentBlock.next.prevHash = currentBlock.prevHash;
                    currentBlock.next.prev = currentBlock.prev;
                }
            }
            currentBlock = currentBlock.next;
        }
    }*/
    //delete block using trasaction data
    public void deleteBlock(Transaction transaction) {
        Block currentBlock = genesisBlock;
        while (currentBlock != null) {
            if (currentBlock.transaction == transaction) {
                if (currentBlock == genesisBlock) {
                    genesisBlock = genesisBlock.next;
                    genesisBlock.prev = null;
                } else if (currentBlock == omegaBlock) {
                    omegaBlock = omegaBlock.prev;
                    omegaBlock.next = null;
                } else {
                    currentBlock.prev.next = currentBlock.next;
                    //exchange hashes
                    currentBlock.next.prevHash = currentBlock.prevHash;
                    currentBlock.next.prev = currentBlock.prev;
                }
            }
            currentBlock = currentBlock.next;
        }
    }
    //calculate hash method
    public String calculateHash(Block block) {
        //ternary operator
        String data = (block.transaction == null) ? "" : block.transaction.toString();
        String dataToHash = block.prevHash + Long.toString(block.timeStamp)+data;
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
