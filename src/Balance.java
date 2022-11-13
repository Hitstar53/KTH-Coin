import java.sql.*;
public class Balance {
    User user;
    public void initialize(User user) {
        this.user = user;
        //get balance from database
        int balance = getBalanceFromDatabase();
        //display balance
        displayBalance(balance);
    }
    public int getBalanceFromDatabase() {
        int balance = 0;
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            ResultSet rs = stmt.executeQuery("select * from users where email = '" + user.email + "'");
            //get balance
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return balance;
    }
    //update balance in database
    public void updateBalanceInDatabase(int balance) {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            stmt.executeUpdate("update users set balance = " + balance + " where email = '" + user.email + "'");
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void displayBalance(int balance) {
        System.out.println("Your balance is: " + balance);
    }
    /*public static void main(String[] args) {
        Balance balance = new Balance();
        User user = new User();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email: ");
        user.email = sc.nextLine();
        System.out.println("Enter your password: ");
        user.password = sc.nextLine();
        balance.initialize(user);
        System.out.println("Enter the amount you want to add to your balance: ");
        int amount = sc.nextInt();
        int newBalance = balance.getBalanceFromDatabase() + amount;
        balance.updateBalanceInDatabase(newBalance);
        balance.displayBalance(newBalance);
        sc.close();
    }*/
}
