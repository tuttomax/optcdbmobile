package com.optc.optcdbmobile.data.ui.activities;

class State {

    private String message;

    public State(String message) {
        this.message = message;
    }

    public Boolean isMessageValid() {
        return message != null && !message.isEmpty();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static State buildProgress(String message, int max, int count) {
        return new State(String.format("%s\n%d/%d", message, count, max));
    }
}




