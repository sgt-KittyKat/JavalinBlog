package com.github.sgt_kittyKat.blog.controllers;


import io.javalin.http.Context;

public interface Controller {

    void get(Context context);
    void post(Context context);
    void patch(Context context);
    void delete(Context context);
}
