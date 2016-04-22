package kz.aibol.app.vkfriends;

/**
 * Created by aibol on 4/22/16.
 */
public class UserInfo {
    private int id;
    private String name;
    private String imgUrl;

    public UserInfo(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imageUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
