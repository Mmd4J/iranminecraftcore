package me.gameisntover.iranminecraftcore.response;


public class APIResponse<T> {
    private boolean result;

    private String message;
    protected final T value;

    public APIResponse(T val,boolean res) {
        this.value = val;
        result = res;
        
    }

    public APIResponse() {
        value = null;
    }

    public APIResponse(String message) {
        this.value = null;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public T getValue() {
        return value;
    }

    public boolean getResult() {
        return result;
    }
}
