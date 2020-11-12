package com.github.sgt_kittyKat.blog.database.models;

import io.javalin.core.security.Role;

public enum MyRole implements Role {
    EVERYONE, ADMIN, VIP, EMPTY;
}
