package Test;


import access.CommentDTO;
import access.PostDTO;
import db.Persistence;
import entities.Post;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import view.Viewmodel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author philippnienhuser
 */
@RunWith(Arquillian.class)
public class ViewModelTest {
    @Inject
    Viewmodel view;
    @Inject
    private EntityManager em;
    
    @Before
    public void setUp(){
        view.setInputTextUser("TestUser");
        view.setInputTextEMail("TestEmail");
        view.setInputTextFName("TestVorname");
        view.setInputTextLName("TestNachname");
    }
    
    @After
    public void tearDown(){
        
    }
    
    
    @Deployment
    public static WebArchive createDeployment(){
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("dist/kbse-pcnv.war"));
    }
    
   @Test
    public void submitLink(){
        // User 1 postet einen Link
        view.setInputTexTURL("TestURL");
        view.setInputTextDescription("TestDescription");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO p1 = list.get(0);
        Post p2 = em.find(Post.class,p1.getId());
        assertEquals(p1.toPost().getCreator(),p2.getCreator(),"TestUser");
        assertEquals(p1.toPost().getDescription(),p2.getDescription(),"TestDescription"); 
        assertEquals(p1.toPost().getUrl(),p2.getUrl(),"TestURL");       
    }
   
    @Test
    public void submitComment(){   
        // User 2 schickt Kommentar ab
        view.setInputCommentMessage("CommentMessageTest");
        view.setInputTextUser("TestUser2");
        view.changeUser();
        view.selectPost(view.getPostList().get(0));
        view.submitComment();
        CommentDTO c = view.getPostList().get(0).getComments().get(0);
        assertEquals(c.getCreator(),"TestUser2");
        assertEquals(c.getMessage(),"CommentMessageTest");       
    }
    @Test
    public void deletePost(){
        //User 1 löscht seinen Post
        view.selectPost(view.getPostList().get(0));
        long postid = view.getCurrentPost().getId();
        view.delete(view.getCurrentPost());
        Post p = em.find(Post.class, postid);
        assertEquals(p,null);
        
    }
}
