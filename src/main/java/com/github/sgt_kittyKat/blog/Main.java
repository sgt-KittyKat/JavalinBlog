package com.github.sgt_kittyKat.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sgt_kittyKat.blog.controllers.PostController;
import com.github.sgt_kittyKat.blog.controllers.UserController;
import com.github.sgt_kittyKat.blog.services.PostService;
import com.github.sgt_kittyKat.blog.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static Javalin app;
    public static void main(String[] args) {
        UserController userController = new UserController(new UserService(), new ObjectMapper());
        PostController postController = new PostController(new PostService(), new ObjectMapper(), new UserService());
        app = Javalin.create();
        app.routes(()-> {
            path("users", () -> {
                get(userController::getAll);
                post(userController::post);
                path(":id", () -> {
                    get(userController::get);
                    patch(userController::patch);
                    delete(userController::delete);
                    path(":role", () -> {
                        patch(userController::changeRole);
                    });
                });
            });
        });
        app.routes(()-> {
           path("posts", () -> {
               get(postController::getAll);
               post(postController::post);
               patch(postController::patch);
               path(":id", () -> {
                   get(postController::get);
                   delete(postController::delete);
               });
           }) ;
        });
        app.start(8080);
    }
}
