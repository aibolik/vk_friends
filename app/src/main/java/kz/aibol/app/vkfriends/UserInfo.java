package kz.aibol.app.vkfriends;

/**
 * Created by aibol on 4/22/16.
 */
public class UserInfo {
    private int id;
    private String name;
    //    private Bitmap image;
    private String imageUrl;

    //    public UserInfo(int id, String name, Bitmap image) {
//        this.id = id;
//        this.name = name;
//        this.image = image;
//    }
    public UserInfo(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //    public Bitmap getImage() {
//        return image;
//    }
//
//    public void setImage(Bitmap image) {
//        this.image = image;
//    }
}
