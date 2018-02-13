package Test;


import java.io.File;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
 public class JSFTest {
  //  @Deployment
 /*   public static WebArchive createDeployment() {
       return  ShrinkWrap.create(WebArchive.class, "test.war")
             .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
             .addPackage(Package.getPackage("Test"))
             .addResource(new File("src/main/webapp", "index.xhtml"))
             .addWebResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml");

    }*/
}