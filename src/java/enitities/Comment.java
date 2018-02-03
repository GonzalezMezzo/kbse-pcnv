/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enitities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 *
 * @author chrschae
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",
            unique=true)
    private Long id;
    @Column(name="creator",
            nullable=false,
            unique=false)
    private String creator;
    @Column(name="message",
            nullable=false,
            unique=false)
    private String message;
    @Column(name="timestamp",
            nullable=false,
            unique=false)
    private String timestamp;
    /*
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OWNER_POST_ID")
    private Post owner;
    */
    public Comment() {
    }
    
    public Comment(String message, String creator){
        this.creator = creator;
        this.timestamp = new SimpleDateFormat("HHmmss_ddMMyyyy").format(Calendar.getInstance().getTime());
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", creator=" + creator + ", message=" + message + ", timestamp=" + timestamp +'}';
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
