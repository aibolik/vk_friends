package kz.aibol.app.vkfriends;

/**
 * Created by aibol on 4/22/16.
 */
public class UserInfo {
    private int id;
    private String name;
    private String imageUrl;
    private boolean isOnline;

    public UserInfo(int id, String name, String imageUrl, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isOnline = isOnline;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
