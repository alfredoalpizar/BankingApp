package alpizar.alfredo.capgeminicasestudy.model;
import java.time.Instant;

public class Account {

    private Long balance;
    private Long productID;
    private Instant timeStamp;

    public Account(){

    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
}
