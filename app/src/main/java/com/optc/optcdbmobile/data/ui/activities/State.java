package com.optc.optcdbmobile.data.ui.activities;

class State {

    private String message;
    private String command;
    private Integer value;

    public State(String message, String command, Integer value) {
        this.message = message;
        this.command = command;
        this.value = value;
    }

    public State(String message) {
        this(message, null, null);
    }

    public State(String command, Integer value) {
        this(null, command, value);
    }


    public String getMessage() {
        return message;
    }

    public String getCommand() {
        return command;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean isMessageValid() {
        return message != null && !message.isEmpty();
    }

    public Boolean isCommandValid() {
        return command != null && !command.isEmpty();
    }
}
