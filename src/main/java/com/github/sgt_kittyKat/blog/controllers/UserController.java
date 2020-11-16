package com.github.sgt_kittyKat.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sgt_kittyKat.blog.services.UserService;
import com.github.sgt_kittyKat.blog.utils.commands.userCommands.Create;
import com.github.sgt_kittyKat.blog.utils.commands.userCommands.Delete;
import com.github.sgt_kittyKat.blog.utils.commands.userCommands.Patch;
import com.github.sgt_kittyKat.blog.utils.commands.userCommands.Read;
import com.github.sgt_kittyKat.blog.database.models.MyRole;
import com.github.sgt_kittyKat.blog.database.models.User;
import io.javalin.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class UserController implements Controller{
    UserService service;
    ObjectMapper om;

    public UserController(UserService service, ObjectMapper om) {
        this.service = service;
        this.om = om;
    }
    public User findSender(Context context) throws SQLException {
        if (!context.basicAuthCredentialsExist()) {
            return new User(MyRole.EMPTY);
        }
        else {
            String login = context.basicAuthCredentials().component1();
            String password = context.basicAuthCredentials().component2();
            if (service.authenticate(login, password)) {
                User user = service.findUserByLogin(login);
                return user;
            }
            else {
                throw new UnauthorizedResponse("Invalid login or password");
            }
        }
    }
    public MyRole getUserRole(Context ctx) throws Exception {
        if (ctx.basicAuthCredentialsExist()) {
            String login = ctx.basicAuthCredentials().component1();
            User user = service.findUserByLogin(login);
            return user.getRole();
        }
        else {
            throw new UnauthorizedResponse();
        }
    }
    public void getAll(Context context) {
        try {
            User sender = findSender(context);
            if (Read.permittedRoles.contains(sender.getRole())) {
                List<User> users = service.getAll();
                context.result(om.writeValueAsString(users));
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void get(Context context) {
        try {
            User sender = findSender(context);
            int id = Integer.parseInt(context.pathParam("id"));
            User target = service.findUserById(id);
            if (Read.permittedRoles.contains(sender.getRole())) {
                context.result(om.writeValueAsString(target));
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void post(Context context) {
        try {
            User sender = findSender(context);
            User created = om.readValue(context.body(), User.class);
            if (Create.permittedRoles.contains(sender.getRole())) {
                service.postUser(created);
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void patch(Context context) {
        try {
            User sender = findSender(context);
            Integer id = Integer.parseInt(context.pathParam("id"));
            User target = service.findUserById(id);
            User updated = om.readValue(context.body(), User.class);
            if (sender.equals(target) && target.changesAllowed(updated)) {
                if (updated.getPassword() != null) {
                    updated.setPassword(BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt()));
                }
                service.patchUser(updated);
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public void changeRole(Context context) {
        try {
            User sender = findSender(context);
            Integer id = Integer.parseInt(context.pathParam("id"));
            User target = service.findUserById(id);
            String role = context.pathParam("role");
            if (Patch.permittedRoles.contains(sender.getRole()) &&
                    !Patch.forbiddenRoles.contains(target.getRole())) {
                target.setRole(MyRole.valueOf(role));
                service.patchUser(target);
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Context context) {
        try {
            User sender = findSender(context);
            int id = Integer.parseInt(context.pathParam("id"));
            User target = service.findUserById(id);
            if (sender.equals(target)) {
                service.deleteUser(id);
            }
            else if (Delete.permittedRoles.contains(sender.getRole())
                    && !Delete.forbiddenRoles.contains(target.getRole())) {
                service.deleteUser(id);
            }
            else {
                throw new ForbiddenResponse();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}