package com.tasktracker.service.task.operation;

/**
 *
 * Created by joan on 5/7/15.
 */
public enum TrackOperation {

    ADD_TRACK("add"),
    PUT_TRACK("put");

    private final String operation;

    TrackOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
