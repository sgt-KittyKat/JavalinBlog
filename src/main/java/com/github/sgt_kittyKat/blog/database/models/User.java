package com.github.sgt_kittyKat.blog.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.javalin.core.security.Role;

import java.util.List;
import java.util.Objects;


@DatabaseTable
public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private MyRole role;
    @DatabaseField
    private String login;
    @DatabaseField
    private String password;

    public User() {
    }

    public User(MyRole role) {
        this.role = role;
    }

    public User(String login, String password) {
        this.id = 0;
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyRole getRole() {
        return role;
    }

    public void setRole(MyRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                role == user.role &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, password);
    }
    public boolean onlyRoleChanged(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }
    public boolean changesAllowed(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(role, user.role);
    }
}
