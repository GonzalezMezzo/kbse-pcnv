package Test;

import access.DTO.CommentDTO;
import access.DTO.PostDTO;
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
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import view.ViewModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chrschae
 */
@RunWith(Arquillian.class)
public class ViewModelTest {

    @Inject
    ViewModel view;
    @Inject
    private EntityManager em;
    private PostDTO post = null;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("dist/kbse-pcnv.war"));
    }

    @Before
    public void setUp() throws Exception {
        view.setInputTextUser("TestUser");
        view.setInputTextEMail("TestEmail");
        view.setInputTextFName("TestVorname");
        view.setInputTextLName("TestNachname");
        view.changeUser();
        view.setInputTexTURL("TestURL");
        view.setInputTextDescription("TestDescription");
        view.submitLink();
        List<PostDTO> list = view.getPostList();
        for (PostDTO p : list) {
            if (p.getUrl().equals("TestURL") && p.getDescription().equals("TestDescription")) {
                post = p;
            }
        }
        if (post == null) {
            throw new Exception("Post nicht in Postlist");
        }
    }

    @After
    public void tearDown() {
        view.delete(post);
    }

    @Test
    public void TestsubmitLink() throws Exception {
        Post p2 = em.find(Post.class, post.getId());
        assertEquals(post.toPost().getAuthor().getUsername(), p2.getAuthor().getUsername(), "TestUser");
        assertEquals(post.toPost().getDescription(), p2.getDescription(), "TestDescription");
        assertEquals(post.toPost().getUrl(), p2.getUrl(), "TestURL");
    }

    @Test
    public void TestsubmitComment() throws Exception {
        view.setInputTextUser("CommentSubmitter");
        view.changeUser();
        view.selectPost(post);
        view.setInputCommentMessage("submitCommentTest");
        view.submitComment();
        List<PostDTO> list = view.getPostList();
        for (PostDTO p : list) {
            if (p.getUrl().equals("TestURL") && p.getDescription().equals("TestDescription")) {
                post = p;
            }
        }
        CommentDTO c = post.getComments().get(0);
        assertEquals("CommentSubmitter", c.getCreatorId().getUsername());
        assertEquals("submitCommentTest", c.getMessage());
    }

    @Test()
    public void LinkGibtEsNichtMehrBeimKommentierenTest() throws Exception {
        view.setInputTextUser("tester");
        view.changeUser();
        view.selectPost(post);
        view.setInputTextUser("TestUser");
        view.changeUser();
        view.delete(post);
        view.setInputTextUser("tester");
        view.changeUser();
        view.setInputCommentMessage("comment");
        if (view.submitComment() != null) {
            fail("SubmitComment muss null zurückgeben");
        }
    }

    @Test()
    public void userOhneNamePostetLink() throws Exception {
        view.setInputTextUser(null);
        view.changeUser();
        if (view.submitLink() != null) {
            fail("submitLink muss null zurückgeben");
        }
    }

    @Test()
    public void userOhneNamePostetKommentar() throws Exception {
        view.setInputTextUser("kommentierer");
        view.setInputCommentMessage("test");
        if (view.submitComment() != null) {
            fail("submitComment muss null zurückgeben");
        }
    }
    @Test()
    public void userVersuchtVorhandenenLinkZuPosten(){
        view.setInputTexTURL("TestURL");
        if(view.submitComment() != null)
            fail("submitComment muss null zurückgeben");
    }
}
