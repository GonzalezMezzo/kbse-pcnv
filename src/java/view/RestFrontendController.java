/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author philippnienhuser
 */
@Dependent
public class RestFrontendController implements Serializable {
    private final static String THREAD = "http://localhost:8080/kbse-pcnv/";
    
    private Client client;
    private WebTarget wt;
    
    @PostConstruct
    public void init(){
        this.client = ClientBuilder.newClient();
        this.wt = client.target(THREAD);
    }
}
