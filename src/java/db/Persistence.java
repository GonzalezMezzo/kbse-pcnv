/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import access.PostDTO;
import enitities.Post;
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
    
    public void persist(Object o){
        em.persist(o);
    }
    
    public Object merge(Object o){
        return em.merge(o);
    }
    
    public void deletePost(PostDTO p){
        Post tmp = em.find(Post.class, p.getId());
        em.remove(tmp);
    }
    
    public void addPost(PostDTO p){
        persist(p.toPost());
    }

    public List<PostDTO> getAllPosts() {
        List<Post> tmp =  em.createNamedQuery("Post.findAll", Post.class).getResultList();
        List<PostDTO> res = new ArrayList<PostDTO>();
        for(Post p : tmp){
            res.add(PostDTO.toPostDTO(p));
        }
        return res;
    }
    
    /**
     * TODO
     */
    public void refrehRating(){}
    
    public void addRating(){}
    
    
}
