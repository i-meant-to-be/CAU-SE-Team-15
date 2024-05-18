package com.cause15.issuetrackerserver.model;

import java.util.UUID;

public class Tester extends User {
    public Tester(String name, String password) {
        super(name, password);
    }

    public Tester(UUID id, String name, String password) {
        super(id, name, password);
    }
}
