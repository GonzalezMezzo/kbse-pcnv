/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/**
 *
 * @author chrschae
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="commentId",
            unique=true)
    private Long id;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Long creatorId;
    
    @Column(name="message",
            nullable=false,
            unique=false)
    private String message;
    
    @Column(name="timestamp",
            nullable=false,
            unique=false)
    private String timestamp;
    
    @JoinColumn(name="ownerId")
    @OneToOne(cascade = CascadeType.PERSIST)
    private Long ownerId;
   
    public Comment() {
    }
    
    public Comment(String message, Long creatorId, Long id){
        this.creatorId = creatorId;
        this.timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        this.message = message;
        this.ownerId = id;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", creatorId=" + creatorId + ", message=" + message + ", timestamp=" + timestamp +'}';
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creator) {
        this.creatorId = creator;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
