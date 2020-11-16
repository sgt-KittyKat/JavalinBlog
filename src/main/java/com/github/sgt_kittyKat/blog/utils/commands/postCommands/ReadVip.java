package com.github.sgt_kittyKat.blog.utils.commands.postCommands;

import com.github.sgt_kittyKat.blog.database.models.MyRole;
import io.javalin.core.security.Role;

import java.util.HashSet;
import java.util.Set;

public abstract class ReadVip implements PostCommand {
    public static Set<Role> permittedRoles = new HashSet<>();
    static {
        permittedRoles.add(MyRole.ADMIN);
        permittedRoles.add(MyRole.VIP);
    }
}
