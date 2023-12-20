package foo;

import com.opensymphony.xwork2.ActionSupport;

public class Echo extends ActionSupport {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String execute() {
        if (message != null) {
            try {
                Integer.parseInt(message);
                return SUCCESS;
            } catch (Exception e) {
                addActionError(e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
        } else {
            return INPUT;
        }
    }
}