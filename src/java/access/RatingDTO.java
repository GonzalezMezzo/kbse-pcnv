/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import entities.Rating;
import java.io.Serializable;

/**
 *
 * @author nolde
 */
public class RatingDTO implements Serializable {

    private Long id;
    private int ratedValue;
    private SystemUserDTO user;
    private PostDTO post;

    public RatingDTO(Long id, int ratedValue, SystemUserDTO user, PostDTO post) {
        this.id = id;
        this.ratedValue = ratedValue;
        this.user = user;
        this.post = post;
    }

    public RatingDTO(int ratedValue, SystemUserDTO user, PostDTO post) {
        this.ratedValue = ratedValue;
        this.user = user;
        this.post = post;
    }
       
    public static RatingDTO toRatingDTO(Rating r) {
         return new RatingDTO(r.getId(),r.getRatedValue(),SystemUserDTO.toSystemUserDTO(r.getUser()),PostDTO.toPostDTO(r.getPost()));
    }
    
    public Rating toRating() {
        return RatingBuilder.create().ratedValue(this.ratedValue).user(user.toSystemUser()).post(post.toPost()).build();
    }
}
