/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enitities;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 *
 * @author philippnienhuser
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query= "SELECT s FROM Post s")
})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",
            nullable=false,
            unique=true)
    private Long id;
    @Column(name="url",
            nullable=false,
            unique=true)
    private String url;
    @Column(name="comment",
            nullable=true,
            unique=false)
    private String comment;
    @Column(name="creator",
            nullable=false,
            unique=false)
    private String creator;
    @Column(name="totalRating",
            nullable=true,
            unique=false)
    private int totalRating;
    @Column(name="ratings",
            nullable=true,
            unique=false)
    private Map<String, Integer> ratings;
    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> ratings) {
        this.ratings = ratings;
    }
    
    public void calcTotalRating(){
        int res= 0;
        for(Map.Entry<String,Integer> entry : this.ratings.entrySet()){
            res += entry.getValue();
        }
        setTotalRating(res);
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
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "enitities.Post[ id=" + id + " ]";
    }
    
}
