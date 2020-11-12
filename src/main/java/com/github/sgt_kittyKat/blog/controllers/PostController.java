package com.github.sgt_kittyKat.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sgt_kittyKat.blog.utils.commands.postCommands.Delete;
import com.github.sgt_kittyKat.blog.utils.commands.postCommands.ReadVip;
import com.github.sgt_kittyKat.blog.services.PostService;
import com.github.sgt_kittyKat.blog.services.UserService;
import com.github.sgt_kittyKat.blog.utils.commands.postCommands.Create;
import com.github.sgt_kittyKat.blog.utils.commands.postCommands.Read;
import com.github.sgt_kittyKat.blog.utils.commands.userCommands.Patch;
import com.github.sgt_kittyKat.blog.database.models.MyRole;
import com.github.sgt_kittyKat.blog.database.models.Post;
import com.github.sgt_kittyKat.blog.database.models.User;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;

import java.sql.SQLException;

public class PostController implements Controller{
    private PostService postService;
    private ObjectMapper om;
    private UserService userService;
    PostController(PostService postService, ObjectMapper om, UserService userService) {
        this.postService = postService;
        this.om = om;
        this.userService = userService;
    }
    public User findSender(Context context) throws SQLException {
        if (!context.basicAuthCredentialsExist()) {
            return new User(MyRole.EMPTY);
        }
        else {
            String login = context.basicAuthCredentials().component1();
            String password = context.basicAuthCredentials().component2();
            if (userService.authenticate(login, password)) {
                User user = userService.findUserByLogin(login);
                return user;
            }
            else {
                throw new UnauthorizedResponse("Invalid login or password");
            }
        }
    }
    @Override
    public void get(Context context) {
        try {
            User sender = findSender(context);
            Integer id = Integer.parseInt(context.pathParam("id"));
            Post target = postService.findById(id);
            if (!target.isForVip()) {
                if (Read.permittedRoles.contains(sender.getRole())) {
                    context.result(om.writeValueAsString(target));
                } else {
                    throw new ForbiddenResponse();
                }
            }
            else {
                if (ReadVip.permittedRoles.contains(sender.getRole())) {
                    context.result(om.writeValueAsString(target));
                }
                else {
                    throw new ForbiddenResponse();
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void post(Context context) {
        try {
            User sender = findSender(context);
            Post target = om.readValue(context.body(), Post.class);
            if (Create.permittedRoles.contains(sender.getRole())) {
                postService.createPost(target);
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
            Post target = postService.findById(id);
            Post updated = om.readValue(context.body(), Post.class);
            if (Patch.permittedRoles.contains(sender.getRole())) {
                postService.savePost(updated);
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Context context) {
        try {
            User sender = findSender(context);
            Integer id = Integer.parseInt(context.pathParam("id"));
            if (Delete.permittedRoles.contains(sender.getRole())) {
                postService.delete(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
