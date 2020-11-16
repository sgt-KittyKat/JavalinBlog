package com.github.sgt_kittyKat.blog.database.config;

import com.github.sgt_kittyKat.blog.database.models.Post;
import com.github.sgt_kittyKat.blog.database.models.User;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseConfiguration {
    public static final String JDBC_CONNECTION = "jdbc:sqlite:C:\\SQL\\DBs\\blog.db";
    public static ConnectionSource CONNECTION_SOURCE;
    static {

        try {
            CONNECTION_SOURCE = new JdbcConnectionSource(JDBC_CONNECTION);
            TableUtils.createTableIfNotExists(CONNECTION_SOURCE, User.class);
            TableUtils.createTableIfNotExists(CONNECTION_SOURCE, Post.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
