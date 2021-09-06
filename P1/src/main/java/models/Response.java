package models;

public class Response {
    String message;
    Boolean success;
    Object response;

    public Response() {
    }

    public Response(String message, Boolean success, Object response) {
        this.message = message;
        this.success = success;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", response=" + response +
                '}';
    }
}
