package scrum.a2;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class Transactions {

    private ZonedDateTime date;
    private Map<Integer, Integer> itemsSold;
    private Double amountPaid;
    private Double returnedChange;
    private String paymentMethod;

    public Transactions(ZonedDateTime date, Map<Integer, Integer> itemsSold, Double amountPaid, Double returnedChange, String paymentMethod) {
        this.date = date;
        this.itemsSold = itemsSold;
        this.amountPaid = amountPaid;
        this.returnedChange = returnedChange;
        this.paymentMethod = paymentMethod;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Map<Integer, Integer> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Map<Integer, Integer> itemsSold) {
        this.itemsSold = itemsSold;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getReturnedChange() {
        return returnedChange;
    }

    public void setReturnedChange(Double returnedChange) {
        this.returnedChange = returnedChange;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
