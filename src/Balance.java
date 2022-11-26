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
}
