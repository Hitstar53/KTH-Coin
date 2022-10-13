public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, I am KTH Coin!");
        Blockchain blockchain = new Blockchain();
        blockchain.addBlock(1);
        blockchain.addBlock(2);
        blockchain.addBlock(3);

        System.out.println("Hash for block 1 : " + blockchain.genesisBlock.hash);
        System.out.println("Hash for block 2 : " + blockchain.genesisBlock.prevHash);
        System.out.println("Hash for block 3 : " + blockchain.genesisBlock.prevHash);
    }
}
