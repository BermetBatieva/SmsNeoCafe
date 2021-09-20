package com.example.SmsNeoCafe.entity;


import java.util.Set;

public enum ERole {


    ROLE_ADMIN(Set.of(Permission.READ,Permission.WRITER),1),//1
    ROLE_WAITER(Set.of(Permission.READ),2),//2
    ROLE_BARISTA(Set.of(Permission.READ),3),
    ROLE_CLIENT(Set.of(Permission.READ),4);


    private int getId() {
        return id;
    }

    private int id;
    private Set<Permission> permissions;
    ERole(Set<Permission> permissions, int id ){
        this.permissions = permissions;
        this.id = id;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public static ERole getRole(int id ){
        for(ERole role : ERole.values())
            if(role.getId() == id)
                return role;
        return null;
    }
}