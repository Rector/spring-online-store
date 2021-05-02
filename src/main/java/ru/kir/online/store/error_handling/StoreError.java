package ru.kir.online.store.error_handling;

import lombok.Data;

import java.util.*;

@Data
public class StoreError {
    private int status;
    private List<String> messages;
    private Date timestamp;

    public StoreError(int status, String... messages) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(messages));
        this.timestamp = new Date();
    }

    public StoreError(int status, Collection<String> messages) {
        this.status = status;
        this.messages = new ArrayList<>(messages);
        this.timestamp = new Date();
    }
}