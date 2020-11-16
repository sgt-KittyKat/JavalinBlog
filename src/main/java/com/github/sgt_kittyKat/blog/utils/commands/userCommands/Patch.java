package com.github.sgt_kittyKat.blog.utils.commands.userCommands;

import com.github.sgt_kittyKat.blog.database.models.MyRole;
import io.javalin.core.security.Role;

import java.util.HashSet;
import java.util.Set;

public abstract class Patch implements UserCommand {
    public static Set<Role> permittedRoles = new HashSet<>();
    public static Set<Role> forbiddenRoles = new HashSet<>();
    static {
        permittedRoles.add(MyRole.ADMIN);
        forbiddenRoles.add(MyRole.ADMIN);
    }
}
