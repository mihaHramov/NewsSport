
package aaa.bbb.ccc.sportnews.mvp.model.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponceOfSource {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private List<GlobalSource> sources = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GlobalSource> getSources() {
        return sources;
    }

    public void setSources(List<GlobalSource> sources) {
        this.sources = sources;
    }

}
