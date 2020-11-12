package com.github.sgt_kittyKat.blog.services;

import com.github.sgt_kittyKat.blog.database.config.DatabaseConfiguration;
import com.github.sgt_kittyKat.blog.database.models.MyRole;
import com.github.sgt_kittyKat.blog.database.models.Notification;
import com.github.sgt_kittyKat.blog.database.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.DaoFactory;
import io.javalin.core.security.Role;
import io.javalin.http.UnauthorizedResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UserService {
    private Dao<User, Integer> dao;
    public UserService() {
        try {
            dao = DaoFactory.createDao(DatabaseConfiguration.CONNECTION_SOURCE, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findUserById(int id) throws SQLException {
        User user = dao.queryForId(id);
        return user;
    }

    public void deleteUser(int id) throws SQLException {
        dao.deleteById(id);
    }

    public void patchUser(User target) throws SQLException {
        target.setPassword(BCrypt.hashpw(target.getPassword(), BCrypt.gensalt()));
        dao.update(target);
    }

    public void postUser(User created) throws SQLException {
        created.setPassword(BCrypt.hashpw(created.getPassword(), BCrypt.gensalt()));
        dao.create(created);
    }

    public boolean isValidUser(String login) throws SQLException {
        User user = findUserByLogin(login);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean authorizeUser(User user, Set <Role> permittedRoles) {
        if (permittedRoles.contains(user.getRole())) {
            return true;
        }
        return false;
    }
    public List<User> getAll() throws SQLException {
        return dao.queryForAll();
    }
    public void makeVip(User user) {
        user.setRole(MyRole.VIP);
    }
    public boolean authenticate(String login, String password) throws SQLException {
        User user = findUserByLogin(login);
        if (user == null) {
            throw new UnauthorizedResponse("Login doesn't exist");
        }
        else {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public void notifyUser(User user, Notification notification) {
        user.getNotifications().add(notification);
    }
    public void askForVip(User requester, User admin) {
        if (admin.getRole().equals(MyRole.ADMIN)) {
            String message = "User " + requester.getLogin() + " asks for VIP";
            notifyUser(admin, new Notification(message, admin));
        }
    }
    public User findUserByLogin(String login) throws SQLException {
        User user = dao.queryBuilder().where().eq("login", login).queryForFirst();
        return user;
    }
}
