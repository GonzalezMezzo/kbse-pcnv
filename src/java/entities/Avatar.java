/**
 * This file was generated by the Jeddict
 */
package entities;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author nolde
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Avatar.findAll", query= "SELECT s FROM Avatar s"),
    @NamedQuery(name = "Avatar.findByHash", query= "SELECT s FROM Avatar s WHERE s.imageHash = :hash")
})
public class Avatar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Basic
    private int imageHash;
    
    @Column(nullable = false)
    @Basic
    private String contentType;

    @Column(nullable = false)
    @Lob
    @Basic
    private byte[] image;

    //TODO: delete later

    /**
     *
     */
    public Avatar() {
        this.imageHash = -1;
        this.contentType = "image/jpeg";
        this.image = new byte[1];
    }  

    /**
     *
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getImageHash() {
        return this.imageHash;
    }

    /**
     *
     * @param imageHash
     */
    public void setImageHash(int imageHash) {
        this.imageHash = imageHash;
    }

    /**
     *
     * @return
     */
    public byte[] getImage() {
        return this.image;
    }

    /**
     *
     * @param path
     */
    public void setImage(byte[] path) {
        this.image = path;
    }

    /**
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    
}