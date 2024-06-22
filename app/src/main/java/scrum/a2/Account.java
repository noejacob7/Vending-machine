package scrum.a2;

public class Account {

    private String password;
    private boolean savedCard;
    private String name;
    private Long pin;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account(String password, boolean savedCard, String name, Long pin, String role) {
        this.password = password;
        this.savedCard = savedCard;
        this.name = name;
        this.pin = pin;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSavedCard() {
        return savedCard;
    }

    public void setSavedCard(boolean savedCard) {
        this.savedCard = savedCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }
}
