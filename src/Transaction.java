public class Transaction {
    public String sender;
    public String receiver;
    public int amount;
    public Transaction(String sender, String receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
    //buy method
    public void buy(String receiver, int amount) {
        this.receiver = receiver;
        this.amount = amount;
    }
    //sell method
    public void sell(String sender, int amount) {
        this.sender = sender;
        this.amount = amount;
    }
}
