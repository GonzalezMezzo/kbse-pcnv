/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import access.DTO.AvatarDTO;
import access.DTO.CommentDTO;
import access.DTO.PostDTO;
import access.DTO.RatingDTO;
import access.DTO.SystemUserDTO;
import entities.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chrschae
 */
public class MarshallTests {
    
    public MarshallTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void PostDTOtoJSONandBack() {
       SystemUserDTO user = new SystemUserDTO("Username", "Nachname", "Vorname", "Email");
       PostDTO post = new PostDTO("URL", "Beschreibung", user, 0, new ArrayList<>());  
       JsonObject o = post.toJsonObject();
       PostDTO jsonPost = PostDTO.toPOJO(o);
       assertEquals(post.getCreatorId().getUsername(),jsonPost.getCreatorId().getUsername());
       assertEquals(post.getDescription(),jsonPost.getDescription());
       assertEquals(post.getUrl(),jsonPost.getUrl());
       assertEquals(post.getComments(),jsonPost.getComments());
       assertEquals(post.getRatings(),jsonPost.getRatings());
       assertEquals(post.getTotalRating(),jsonPost.getTotalRating());    
    }
    @Test
    public void CommentDTOtoJSONandBack(){
        SystemUserDTO user = new SystemUserDTO("Username", "Nachname", "Vorname", "Email");
        PostDTO post = new PostDTO("URL", "Beschreibung", user, 0, new ArrayList<>());  
        CommentDTO comment = new CommentDTO("message", user,post);
        JsonObject o = comment.toJsonObject();
        CommentDTO jsonComment = CommentDTO.toPOJO(o);
        assertEquals(comment.getMessage(),jsonComment.getMessage());
        assertEquals(comment.getCreatorId().getUsername(),jsonComment.getCreatorId().getUsername());
        //assertEquals(comment,jsonComment);
    }
    @Test
    public void SystemUserDTOtoJSONandBack(){
        //Avatar Fehlt
        SystemUserDTO user = new SystemUserDTO("Username", "Nachname", "Vorname", "Email");
        JsonObject o = user.toJsonObject();
        SystemUserDTO jsonUser = SystemUserDTO.toPOJO(o);
        assertEquals(user.getEmail(),jsonUser.getEmail());
        assertEquals(user.getFname(),jsonUser.getFname());
        assertEquals(user.getLname(),jsonUser.getLname());
        assertEquals(user.getUsername(),jsonUser.getUsername());
    }
    @Test
    public void AvatarDTOtoJsonandBack() throws IOException{
<<<<<<< HEAD
        
        byte[] test = "avatartest".getBytes();
        AvatarDTO avatar = new AvatarDTO(Arrays.hashCode(test),test);
=======
        byte[] param = "avatartest".getBytes();
        int hash = param.hashCode();
        AvatarDTO avatar = new AvatarDTO(hash,"image/jpeg",param);
>>>>>>> caa125315d473a88841432ed52579bc51f69d0be
        JsonObject o = avatar.toJsonObejct();
        AvatarDTO jsonAvatar = AvatarDTO.toPOJO(o);
        assertEquals(avatar.getImageHash(),jsonAvatar.getImageHash());
    //    assertEquals(avatar.getImage().hashCode(),jsonAvatar.getImage().hashCode());      
    }
    @Test
    public void RatingDTOtoJSONandBack(){
        SystemUserDTO user = new SystemUserDTO("Username", "Nachname", "Vorname", "Email");
        PostDTO post = new PostDTO("URL", "Beschreibung", user, 0, new ArrayList<>());
        RatingDTO rating = new RatingDTO(5,user,post);
        JsonObject o = rating.toJsonObject();
        RatingDTO jsonRating = RatingDTO.toPOJO(o);
        assertEquals(rating.getPost().getDescription(),jsonRating.getPost().getDescription());
        assertEquals(rating.getRatedValue(),jsonRating.getRatedValue());
        assertEquals(rating.getUser().getUsername(),jsonRating.getUser().getUsername());     
    }
}
