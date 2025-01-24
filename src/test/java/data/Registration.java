package data;

public class Registration {
    private String email;
    private String password;
    private String agentcode;

    public Registration(String email, String password, String agentcode) {
        this.email = email;
        this.password = password;
        this.agentcode = agentcode;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAgentcode() {
        return agentcode;
    }
}
