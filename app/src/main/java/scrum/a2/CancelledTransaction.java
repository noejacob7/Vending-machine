package scrum.a2;

import java.time.ZonedDateTime;
import java.util.Map;

public class CancelledTransaction {
    private String user;
    private ZonedDateTime date;
    private String reason;

    public CancelledTransaction(String user, ZonedDateTime date, String reason) {
        this.user = user;
        this.date = date;
        this.reason = reason;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
