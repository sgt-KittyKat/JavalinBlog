package com.github.sgt_kittyKat.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sgt_kittyKat.blog.controllers.UserController;
import com.github.sgt_kittyKat.blog.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static Javalin app;
    public static void main(String[] args) {
        UserController userController = new UserController(new UserService(), new ObjectMapper());
        app = Javalin.create();
        app.routes(()-> {
            path("users", () -> {
                get(userController::getAll);
                post(userController::post);
                path(":id", () -> {
                    get(userController::get);
                    patch(userController::patch);
                    delete(userController::delete);
                });
            });
        });
        app.start(8080);
    }
}
