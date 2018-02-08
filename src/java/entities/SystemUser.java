/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author nolde
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SystemUser.findAll", query = "SELECT s FROM SystemUser s")
})
//systemuser, weil user ist ein sql keyword
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "systemUserId",
            unique = true)
    private Long id;
    @Column(name = "username",
            nullable = false,
            unique = true)
    private String username;
    @Column(name = "first_name",
            nullable = true,
            unique = false)
    private String fname;
    @Column(name = "last_name",
            nullable = true,
            unique = false)
    private String lname;
    @Column(name = "e_mail",
            nullable = true,
            unique = false)
    private String email;
    @Lob
    @Column(name = "avatar",
            nullable = true,
            unique = false)
    private byte[] avatar;
    
    @JoinColumn(name="creatorId")
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments;
    
    @JoinColumn(name="creatorId")
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts;

    public SystemUser() {
    }

    public SystemUser(Long id, String username, String fname, String lname, String email, byte[] avatar) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "entities.SystemUser[ id=" + id + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(obj instanceof Post)) {
            return false;
        }
        SystemUser other = (SystemUser) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

}
