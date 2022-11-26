import java.util.Scanner;
public class Transact {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Scanner sc = new Scanner(System.in);
        User user = new User();
        Balance balance = new Balance();
        int choice,flag=0,amount,price;
        System.out.println("Welcome to the KTH Blockchain!");
        System.out.println("This is the KTH Coin Portal");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        user.email = email;
        user.password = password;
        System.out.println("Logging in...");
        balance.initialize(user);
        user.getUserDetailsFromDatabase();
        System.out.println("Login successful!");
        System.out.println("Loading blocks...");
        blockchain.loadBlock();
        System.out.println("Blocks loaded!");
        while(true) {
            if(flag==3) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("KTH Coin Portal");
                sc.nextLine();
                System.out.print("Email: ");
                email = sc.nextLine();
                System.out.print("Password: ");
                password = sc.nextLine();
                user.email = email;
                user.password = password;
                System.out.println("Logging in...");
                balance.initialize(user);
                user.getUserDetailsFromDatabase();
                System.out.println("Login successful!");
            }
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
                    System.out.println("3. Cancel Buy Order");
                    System.out.println("4. Cancel Sell Order");
                    System.out.println("5. List Orders");
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
                            balance.updateBalanceInDatabase(flag-amount);
                            if(transaction.performTransSell(sender,amount,price)) {
                                blockchain.addBlock(transaction);
                            }
                            System.out.println("Traversing the blockchain...");
                            blockchain.traverse();
                            balance.displayBalance(balance.getBalanceFromDatabase());
                            break;
                        case 3:
                            System.out.print("Enter quantity: ");
                            amount = sc.nextInt();
                            System.out.print("Enter price: ");
                            price = sc.nextInt();
                            System.out.print("Enter receiver's Email: ");
                            receiver = sc.next();
                            transaction = new Transaction();
                            transaction.cancelBuyOrder(receiver,amount,price);
                            balance.displayBalance(balance.getBalanceFromDatabase());
                            break;
                        case 4:
                            System.out.print("Enter quantity: ");
                            amount = sc.nextInt();
                            System.out.print("Enter price: ");
                            price = sc.nextInt();
                            System.out.print("Enter sender's Email: ");
                            sender = sc.next();
                            flag = balance.getBalanceFromDatabase();
                            transaction = new Transaction();
                            if(transaction.cancelSellOrder(sender,amount,price)) {
                                balance.updateBalanceInDatabase(flag+amount);
                            }
                            balance.displayBalance(balance.getBalanceFromDatabase());
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
            System.out.println("Do you want to continue?\n1. Log Out\n2. Continue\n3. Switch User");
            flag = sc.nextInt();
            if (flag==1) {
                System.out.println("Logging out...");
                break;
            }
        }
        sc.close();
    }
}
