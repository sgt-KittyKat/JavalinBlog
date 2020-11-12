package com.github.sgt_kittyKat.blog.utils.commands.postCommands;

import com.github.sgt_kittyKat.blog.database.models.MyRole;
import io.javalin.core.security.Role;

import java.util.Set;

public abstract class Delete implements PostCommand {
    public static Set<Role> permittedRoles;
    static {
        permittedRoles.add(MyRole.ADMIN);
    }
}
