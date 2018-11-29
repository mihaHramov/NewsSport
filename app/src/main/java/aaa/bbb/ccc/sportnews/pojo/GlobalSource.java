
package aaa.bbb.ccc.sportnews.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalSource {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose

    private String description;
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("country")
    @Expose
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
