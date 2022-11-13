import java.sql.*;
public class User {
    public String name;
    public String email;
    public String phone;
    public String address;
    public String password;
    public int balance;
    Wallet wallet;
    //constructor
    public User() {
        name = "";
        email = "";
        phone = "";
        address = "";
        password = "";
        wallet = new Wallet();
        wallet.generateKeyPair();
    }
    //get user details from database
    public void getUserDetailsFromDatabase() {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            ResultSet rs = stmt.executeQuery("select * from users where email = '" + email + "'");
            //get user details
            while (rs.next()) {
                name = rs.getString("name");
                email = rs.getString("email");
                phone = rs.getString("phone");
                address = rs.getString("address");
                balance = rs.getInt("balance");
                password = rs.getString("password");
            }
            //close connection
            con.close();
            System.out.println("User details: " + name + " " + email + " " + phone + " " + address + " " + balance + " " + password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //update user details in database
    public void updateUserDetailsInDatabase() {
        try {
            //connect to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC", "sqluser", "password");
            //create statement
            Statement stmt = con.createStatement();
            //execute query
            stmt.executeUpdate("update users set name = '" + name + "', email = '" + email + "', phone = '" + phone + "', address = '" + address + "', password = '" + password + "' where email = '" + email + "'");
            //close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //toString method
    public String details() {
        return "Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address + ", Password: " + password + ", PublicKey: " + wallet.getPublicKey() +  ", PrivateKey: " + wallet.getPrivateKey();
    }
}
