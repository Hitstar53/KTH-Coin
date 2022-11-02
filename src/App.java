public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, I am KTH Coin!");
        Blockchain blockchain = new Blockchain();
        System.out.println("Genesis block created!");
        System.out.println("Adding 3 blocks to the blockchain...");
        //blockchain.addBlock(1);
        //blockchain.addBlock(2);
        //blockchain.addBlock(3);
        System.out.println("Traversing the blockchain...");
        blockchain.traverse();
        System.out.println("Deleting block 2...");
        //blockchain.deleteBlock(2);
        System.out.println("Traversing the blockchain..."); 
        blockchain.traverse();
    }
}
