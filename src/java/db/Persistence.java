/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import access.PostDTO;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author philippnienhuser
 */
public class Persistence {
    @Inject
    private EntityManager em;
    
    public void persisr(Object o){
        em.persist(o);
    }
    
    public Object merge(Object o){
        return em.merge(o);
    }
    
    /**
     * TODO
     */
    public void refrehRating(){}
    
    public void addRating(){}
    
    public void deletePost(){}
    
    public void addPost(){}

    public List<PostDTO> getAllPosts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
