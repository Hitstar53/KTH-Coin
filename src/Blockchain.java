import java.security.*;
import java.io.*;
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
        writeBlock(newBlock);
        omegaBlock.next = newBlock;
        newBlock.prev = omegaBlock;
        omegaBlock = newBlock;
    }
    //create a write block function
    public void writeBlock(Block block) {
        try {
            FileWriter fw = new FileWriter("D:\\KTH Coin\\src\\Blocks\\blocks.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(block.hash);
            pw.println(block.prevHash);
            pw.println(block.timeStamp);
            pw.println(block.transaction.sender);
            pw.println(block.transaction.receiver);
            pw.println(block.transaction.amount);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //create a read block function
    public void readBlock() {
        try {
            FileReader fr = new FileReader("D:\\KTH Coin\\src\\Blocks\\blocks.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            //read 5 lines at a time
            while((line = br.readLine()) != null) {
                line = br.readLine();
                System.out.println(line);
                line = br.readLine();
                System.out.println(line);
                line = br.readLine();
                System.out.println(line);
                line = br.readLine();
                System.out.println(line);
                line = br.readLine();
                System.out.println(line);
                System.out.println();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //create a load block function which loads the blocks from the file into the blockchain
    public void loadBlock() {
        try {
            FileReader fr = new FileReader("D:\\KTH Coin\\src\\Blocks\\blocks.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            //read 5 lines at a time
            while((line = br.readLine()) != null) {
                Block newBlock = new Block();
                newBlock.transaction = new Transaction();
                newBlock.hash = line;
                line = br.readLine();
                newBlock.prevHash = line;
                line = br.readLine();
                newBlock.timeStamp = Long.parseLong(line);
                line = br.readLine();
                newBlock.transaction.sender = line;
                line = br.readLine();
                newBlock.transaction.receiver = line;
                line = br.readLine();
                newBlock.transaction.amount = Integer.parseInt(line);
                omegaBlock.next = newBlock;
                newBlock.prev = omegaBlock;
                omegaBlock = newBlock;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
