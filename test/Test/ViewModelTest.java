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
import view.LinkNotFoundException;
import view.MissingCredentialsException;
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
    
    @Deployment
    public static WebArchive createDeployment(){
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("dist/kbse-pcnv.war"));
    }
    
    
    @Before
    public void setUp(){

    }
    
    @After
    public void tearDown(){
        
    }
   @Test
    public void TestsubmitLink() throws Exception{
        view.setInputTextUser("TestUser");
        view.setInputTextEMail("TestEmail");
        view.setInputTextFName("TestVorname");
        view.setInputTextLName("TestNachname");
        view.changeUser();
        view.setInputTexTURL("TestURL");
        view.setInputTextDescription("TestDescription");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO p1 = null;
        for(PostDTO p : list){
            if(p.getUrl().equals("TestURL") && p.getDescription().equals("TestDescription"))
                p1 = p;
        }
        if(p1==null){
            throw new Exception("Post nicht in Postlist");
        } else {
            Post p2 = em.find(Post.class,p1.getId());
            assertEquals(p1.toPost().getAuthor().getUsername(),p2.getAuthor().getUsername(),"TestUser");
            assertEquals(p1.toPost().getDescription(),p2.getDescription(),"TestDescription");
            assertEquals(p1.toPost().getUrl(),p2.getUrl(),"TestURL");       
        }
    }
   
    @Test
    public void TestsubmitComment() throws Exception{  
        view.setInputTextUser("TestUser2");
        view.setInputTextEMail("TestEmail2");
        view.setInputTextFName("TestVorname2");
        view.setInputTextLName("TestNachname2");
        view.changeUser();
        view.setInputTexTURL("TestURL2");
        view.setInputTextDescription("TestDescription2");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO p1 = null;
        for(PostDTO p : list){
            if(p.getUrl().equals("TestURL2") && p.getDescription().equals("TestDescription2"))
                p1 = p;
        }
        if(p1==null){
            throw new Exception("Post nicht in Postlist");
        } else {
            view.setInputTextUser("CommentSubmitter");
            view.changeUser();
            view.selectPost(p1);
            view.setInputCommentMessage("submitCommentTest");
            view.submitComment();
            list = view.getPostList();
            for(PostDTO p : list){
                if(p.getUrl().equals("TestURL2") && p.getDescription().equals("TestDescription2"))
                    p1 = p;
            } 
            CommentDTO c = p1.getComments().get(0);
            assertEquals("CommentSubmitter",c.getCreatorId().getUsername());
            assertEquals("submitCommentTest",c.getMessage());       
        }
    }
    @Test
    public void TestdeletePost() throws Exception{
        view.setInputTextUser("deleter");
        view.changeUser();
        view.setInputTexTURL("testDelete");
        view.setInputTextDescription("testDeleteDesc");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO p1 = null;
        for(PostDTO p : list){
            if(p.getUrl().equals("testDelete") && p.getDescription().equals("testDeleteDesc"))
                p1 = p;
        }
        if(p1==null){
            throw new Exception("Post nicht in Postlist");
        } else {
        view.selectPost(p1);
        view.delete(p1);
        Post p = em.find(Post.class, p1.getId());
        assertEquals(null,p);
        }
    }
    @Test(expected = LinkNotFoundException.class)
    public void testCase1(){
        view.setInputTexTURL("test1URL");
        view.setInputTextDescription("test1Description");
        view.setInputTextUser("user1");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO post = list.get(0);
        view.setInputTextUser("user2");
        view.changeUser();
        view.delete(post);
        view.selectPost(post);     
    }
    @Test(expected = LinkNotFoundException.class)
    public void testCase2() throws Exception{
        view.setInputTexTURL("test2URL");
        view.setInputTextDescription("test2Description");
        view.setInputTextUser("user3");
        view.changeUser();
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        PostDTO p1 = null;
        for(PostDTO p : list){
            if(p.getUrl().equals("test2URL") && p.getDescription().equals("test2Description") &&p.getCreatorId().getUsername().equals("user3"))
                p1 = p;
        }
        if(p1==null){
            throw new Exception("Post nicht in Postlist");
        } else {
            view.setInputTextUser("user4");
            view.changeUser();
            view.selectPost(p1);
            view.setInputTextUser("user3");
            view.changeUser();
            view.delete(p1);
            view.setInputTextUser("user4");
            view.changeUser();
            view.setInputCommentMessage("testcase2Message");
            view.submitComment();
        }
    }
    @Test()
    public void TestCase3() throws Exception{
        view.setInputTexTURL("test3URL");
        view.setInputTextDescription("test3Description");
        view.setInputTextUser(null);
        view.changeUser();
        view.submitLink();
    }
    @Test(expected = MissingCredentialsException.class)
    public void testCase4() throws Exception{
        //gleich wie 3 nur mit null
        view.setInputTexTURL("test4URL");
        view.setInputTextDescription("test4Description");
        view.setInputTextUser("");
        view.changeUser();
        view.submitLink();
    }
}