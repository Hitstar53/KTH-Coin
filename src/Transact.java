import java.util.Scanner;
public class Transact {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Scanner sc = new Scanner(System.in);
        int choice,flag;
        System.out.println("Welcome to the KTH Blockchain!");
        System.out.println("This is the KTH Coin Portal");
        while(true) {
            System.out.println("Do you want to buy or sell?");
            System.out.println("1. Buy");
            System.out.println("2. Sell");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the amount you want to buy: ");
                    int amount = sc.nextInt();
                    System.out.println("Enter the receiver's name: ");
                    String receiver = sc.next();
                    Transaction transaction = new Transaction();
                    transaction.buy(receiver, amount);
                    blockchain.addBlock(transaction);
                    System.out.println("Transaction successful!");
                    System.out.println("Traversing the blockchain...");
                    blockchain.traverse();
                    break;
                case 2:
                    System.out.println("Enter the amount you want to sell: ");
                    amount = sc.nextInt();
                    System.out.println("Enter the sender's name: ");
                    String sender = sc.next();
                    transaction = new Transaction();
                    transaction.sell(sender, amount);
                    blockchain.addBlock(transaction);
                    System.out.println("Transaction successful!");
                    System.out.println("Traversing the blockchain...");
                    blockchain.traverse();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
            System.out.println("Do you want to continue? (1 for yes, 0 for no)");
            flag = sc.nextInt();
            if (flag == 0) {
                break;
            }
        }
        sc.close();
    }
}
