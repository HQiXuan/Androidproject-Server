package hy.android.androidproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userinfo")
public class UserInfo {
    @Id
    private String _id;
    private String name;
    private String userid;
    private String gender;
    private String imagesBase64;
    private String groupid;

    public UserInfo() {
    }

    public UserInfo(String name, String userid, String gender, String imagesBase64, String groupid) {
        this.name = name;
        this.userid = userid;
        this.gender = gender;
        this.imagesBase64 = imagesBase64;
        this.groupid = groupid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImagesBase64() {
        return imagesBase64;
    }

    public void setImagesBase64(String imagesBase64) {
        this.imagesBase64 = imagesBase64;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

}
