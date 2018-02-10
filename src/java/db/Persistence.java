/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import access.AvatarDTO;
import access.CommentDTO;
import access.PostDTO;
import access.RatingDTO;
import access.SystemUserDTO;
import entities.Avatar;
import entities.Comment;
import entities.Post;
import entities.Rating;
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

    public void addPost(PostDTO p, SystemUserDTO currentUser) {
        Post post = p.toPost();

        //String url = post.getUrl();                 
        //Post addedPost = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", url).getSingleResult();
        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", currentUser.getUsername()).getSingleResult();

        su.getPosts().add(post);
        post.setAuthor(su);

        em.persist(post);
        em.merge(su);
        //em.merge(post);
    }
    
    public void addRating(PostDTO post, RatingDTO r, SystemUserDTO u){
        
        Rating rating = r.toRating();

        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", u.getUsername()).getSingleResult();
        Post p = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", post.getUrl()).getSingleResult();
        
        su.addRating(rating);
        p.addRating(rating);
        p.calcTotalRating();
        em.persist(rating);
        em.merge(p);
        em.merge(su);
    
    }
    
    public void deleteRatings(String userName) {
        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", userName).getSingleResult();
        List<PostDTO> postList = getAllPosts();
        for (PostDTO p: postList){
                for(RatingDTO  r: p.getRatings()){
                    if(su.getUsername().equals(r.getUser().getUsername())){
                        Post tmp = em.find(Post.class, p.getId());
                        tmp.removeRating(em.find(Rating.class, r.getId()));
                        tmp.calcTotalRating();
                        em.merge(tmp);
                    }
                }
            }
        em.merge(su);
    }

    public void addComment(CommentDTO c, PostDTO post, SystemUserDTO currentUser) {
        //Post p = em.find(Post.class, c.getOwnerId());
        //p.getComments().add(c.toComment());
        //em.merge(p);
        Comment com = c.toComment();
        
        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", currentUser.getUsername()).getSingleResult();
        Post p = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", post.getUrl()).getSingleResult();
        
        su.addComment(com);
        p.addComment(com);
        com.setAuthor(su);
        //com.setPost(p);
        em.persist(com);
        em.merge(p);
        em.merge(su);
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

    public void updateSystemUser(SystemUserDTO u) {
        List<SystemUser> tmp = em.createNamedQuery("SystemUser.findAll", SystemUser.class).getResultList();
        for (SystemUser systemUser : tmp) {
            if (systemUser.getUsername().equals(u.getUsername())) {
                systemUser.setUsername(u.getUsername());
                systemUser.setFname(u.getFname());
                systemUser.setLname(u.getLname());
                systemUser.setEmail(u.getEmail());
                systemUser.setAvatar(u.getAvatar().toAvatar());
                em.merge(systemUser);
                break;
            }
        }
        //SystemUser su = em.find(SystemUser.class, userId);
        //su = u.toSystemUser();
        //em.merge(u);
    }

    public void addAvatar(AvatarDTO avatarDTO) {
        Avatar tmp = avatarDTO.toAvatar();
        em.persist(tmp);
    }
    
    public Post getPost(String url) {
        return em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", url).getSingleResult();
    }
    
    public SystemUser getUser(String username) {
        return em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", username).getSingleResult();
    }
    
    public SystemUser getUser(Long userId) {
        return em.find(SystemUser.class, userId);
    }

    public List<Comment> getCommentList(String urlPost, String username) {
        return em.createNamedQuery("Comment.findByUrlAndUsername", Comment.class).setParameter("url", urlPost).setParameter("username", username).getResultList();
    }

    public PostDTO getPost(Long id) {
        return PostDTO.toPostDTO(em.find(Post.class, id));
    }    

}
