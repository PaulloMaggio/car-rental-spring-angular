package com.paulo_motors.demo.entities.enums;

public enum UserRole {
    MANAGER("manager"),
    CLIENT("client");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}