package com.github.sgt_kittyKat.blog.utils.commands.userCommands;

import com.github.sgt_kittyKat.blog.database.models.MyRole;
import io.javalin.core.security.Role;

import java.util.HashSet;
import java.util.Set;

public abstract class Read implements UserCommand {
    public static Set<Role> permittedRoles = new HashSet<>();
    static {
        permittedRoles.add(MyRole.EVERYONE);
        permittedRoles.add(MyRole.ADMIN);
        permittedRoles.add(MyRole.VIP);
    }
}
