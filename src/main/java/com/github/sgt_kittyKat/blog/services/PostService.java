package com.github.sgt_kittyKat.blog.services;

import com.github.sgt_kittyKat.blog.database.config.DatabaseConfiguration;
import com.github.sgt_kittyKat.blog.database.models.Post;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.DaoFactory;

import java.sql.SQLException;
import java.util.List;

public class PostService {
    private Dao<Post, Integer> dao;
    public PostService() {
        try {
            dao = DaoFactory.createDao(DatabaseConfiguration.CONNECTION_SOURCE, Post.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Post findById(int id) throws SQLException {
        Post post = dao.queryForId(id);
        return post;
    }
    public Post findByHeadline(String headline) throws SQLException {
        Post post = dao.queryBuilder().where().eq("headline", headline).queryForFirst();
        return post;
    }
    public void createPost(Post post) throws SQLException {
        dao.create(post);
    }
    public void patchPost(Post post) throws SQLException {
        dao.update(post);
    }
    public void delete(Integer id) throws SQLException {
        dao.deleteById(id);
    }
    public List<Post> getAll() throws SQLException{
        return dao.queryForAll();
    }
}
