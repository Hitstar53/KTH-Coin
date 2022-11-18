import java.util.Scanner;
public class Transact {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Scanner sc = new Scanner(System.in);
        User user = new User();
        Balance balance = new Balance();
        int choice,flag,amount,price;
        System.out.println("Welcome to the KTH Blockchain!");
        System.out.println("This is the KTH Coin Portal");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        user.email = email;
        user.password = password;
        balance.initialize(user);
        user.getUserDetailsFromDatabase();
        while(true) {
            System.out.println("What would you like to do?\n1. Profile\n2. Trade KTH");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Your profile details are:");
                    System.out.println("Name: " + user.name);
                    System.out.println("Email: " + user.email);
                    System.out.println("Phone: " + user.phone);
                    System.out.println("Address: " + user.address);
                    System.out.println("Balance: " + balance.getBalanceFromDatabase());
                    break;
                case 2:
                    System.out.println("Do you want to buy or sell?");
                    System.out.println("1. Buy");
                    System.out.println("2. Sell");
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.print("Enter quantity: ");
                            amount = sc.nextInt();
                            System.out.print("Enter price: ");
                            price = sc.nextInt();
                            System.out.print("Enter receiver's Email: ");
                            String receiver = sc.next();
                            Transaction transaction = new Transaction();
                            transaction.buy(receiver,amount,price);
                            transaction.enterBuyDetailsIntoDatabase();
                            if(transaction.performTransBuy(receiver,amount,price)) {
                                blockchain.addBlock(transaction);
                            }
                            System.out.println("Traversing the blockchain...");
                            blockchain.traverse();
                            balance.displayBalance(balance.getBalanceFromDatabase());
                            break;
                        case 2:
                            System.out.print("Enter quantity: ");
                            amount = sc.nextInt();
                            System.out.print("Enter price: ");
                            price = sc.nextInt();
                            System.out.print("Enter sender's Email: ");
                            String sender = sc.next();
                            //check if seller has enough balance
                            flag = balance.getBalanceFromDatabase();
                            if (flag<amount) {
                                System.out.println("You don't have enough balance! Try buying some KTH coins XD");
                                break;
                            }
                            transaction = new Transaction();
                            transaction.sell(sender,amount,price);
                            transaction.enterSellDetailsIntoDatabase();
                            if(transaction.performTransSell(sender,amount,price)) {
                                blockchain.addBlock(transaction);
                            }
                            System.out.println("Traversing the blockchain...");
                            blockchain.traverse();
                            balance.displayBalance(balance.getBalanceFromDatabase());
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
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
