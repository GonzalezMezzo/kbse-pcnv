package Test;


import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
public class TestClass {
    
    @Before
    public void setUp(){
       
    }
    
    @After
    public void tearDown(){
        
    }
    
    
    @Deployment
    public static WebArchive createDeployment(){
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("dist/KBSE_Nienhueser_Koschman_Schaefer_Oldemeier.war"));
    }
    
    @Test
    public void test1(){}
}
