/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author philippnienhuser
 */

@Named(value = "viewmodel")
@SessionScoped
public class Viewmodel implements Serializable{
    private static final String INDEX = "/index.xhtml?faces-redirect=true";
    
    private String inputText;
    
    
    @PostConstruct
    public void init(){
        this.inputText = "-User-";
    }
    
    public String changeUser(){
        return INDEX;
        
    }

        /*--------------------------------------------------------------------------
    getter
    --------------------------------------------------------------------------*/
    
    
    public String getImputText() {
        return inputText;
    }

    public void setImputText(String imputText) {
        this.inputText = imputText;
    }
    
}
