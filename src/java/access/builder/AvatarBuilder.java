/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access.builder;

import entities.Avatar;

/**
 *
 * @author nolde
 */
public class AvatarBuilder {

    private long id;
    private int imageHash;
    private String contentType;
    private byte[] imagePath;

    private AvatarBuilder() {
    }

    public static AvatarBuilder create() {
        return new AvatarBuilder();
    }

    public AvatarBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AvatarBuilder imageHash(int imageHash) {
        this.imageHash = imageHash;
        return this;
    }

    public AvatarBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public AvatarBuilder image(byte[] image) {
        this.imagePath = image;
        return this;
    }

    public Avatar build() {
        Avatar res = new Avatar();
        res.setId(id);
        res.setImage(imagePath);
        res.setImageHash(imageHash);
        return res;
    }

}
