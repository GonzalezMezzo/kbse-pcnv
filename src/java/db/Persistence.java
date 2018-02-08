/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import access.CommentDTO;
import access.PostDTO;
import access.SystemUserDTO;
import entities.Post;
import entities.SystemUser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author philippnienhuser
 */
@Stateless
public class Persistence {

    @Inject
    private EntityManager em;

    public void deletePost(long id) {
        Post tmp = em.find(Post.class, id);
        em.remove(tmp);
    }

    public void addPost(PostDTO p) {
        Post tmp = p.toPost();
        em.persist(tmp);
    }

    public void addSystemUser(SystemUserDTO u) {
        SystemUser tmp = u.toSystemUser();
        em.persist(tmp);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> tmp = em.createNamedQuery("Post.findAll", Post.class).getResultList();
        List<PostDTO> res = new ArrayList<>();
        for (Post p : tmp) {
            res.add(PostDTO.toPostDTO(p));
        }
        return res;
    }

    public List<SystemUserDTO> getAllUsers() {
        List<SystemUser> tmp = em.createNamedQuery("SystemUser.findAll", SystemUser.class).getResultList();
        List<SystemUserDTO> res = new ArrayList<>();
        for (SystemUser u : tmp) {
            res.add(SystemUserDTO.toSystemUserDTO(u));
        }
        return res;
    }

    public void updateRatings(List<PostDTO> postList) {
        for (PostDTO pdto : postList) {
            Post p = pdto.toPost();
            p.calcTotalRating();
            em.merge(p);
        }
    }

    public void updateSystemUSer(SystemUserDTO u) {
        SystemUser su = em.find(SystemUser.class, u.getId());
        su = u.toSystemUser();
        em.merge(u);
    }

    public void addComment(CommentDTO c) {
        Post p = em.find(Post.class, c.getOwnerId());
        p.getComments().add(c.toComment());
        em.merge(p);
    }

    /**
     * TODO
     */
}
