/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.Map;
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
    
    
    private String inputTextUser;
    private boolean changeUserBool = false;
    private boolean changeUebernehmenBool = false;
    private int Nummer = 1;
    private String Begründung = "Begründung";
    private String Link = "www.Google.de";
    
    
    @PostConstruct
    public void init(){
        this.inputTextUser = "-User-";
    }
    
    public String changeUser(){
        changeUserBool = true;
        return INDEX;
        
    }
    
    public String rating(){
        return INDEX;
    }
    
    public String submit(){
        changeUebernehmenBool = true;
        return INDEX;
    }
    
    public String delete(){
        return INDEX;
    }

        /*--------------------------------------------------------------------------
    getter
    --------------------------------------------------------------------------*/

    public String getInputTextUser() {
        return inputTextUser;
    }

    public void setInputTextUser(String inputTextUser) {
        this.inputTextUser = inputTextUser;
    }

    public int getNummer() {
        return Nummer;
    }

    public void setNummer(int Nummer) {
        this.Nummer = Nummer;
    }

    public String getBegründung() {
        return Begründung;
    }

    public void setBegründung(String Begründung) {
        this.Begründung = Begründung;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }
    

    public boolean isChangeUserBool() {
        return changeUserBool;
    }

    public void setChangeUserBool(boolean changeUserBool) {
        this.changeUserBool = changeUserBool;
    }

    public boolean isChangeUebernehmenBool() {
        return changeUebernehmenBool;
    }

    public void setChangeUebernehmenBool(boolean changeUebernehmenBool) {
        this.changeUebernehmenBool = changeUebernehmenBool;
    }

 
    
    
    
}
