package esmocyp.core.api.dto;

public class ReasonerDTO {

    private String query;
    private String streamingURL;
    private String namedModel;
    private String aBox;
    private String tbox;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNamedModel() {
        return namedModel;
    }

    public void setNamedModel(String namedModel) {
        this.namedModel = namedModel;
    }

    public String getABox() {
        return aBox;
    }

    public void setABox(String aBox) {
        this.aBox = aBox;
    }

    public String getTbox() {
        return tbox;
    }

    public void setTbox(String tbox) {
        this.tbox = tbox;
    }

    public String getStreamingURL() {
        return streamingURL;
    }

    public void setStreamingURL(String streamingURL) {
        this.streamingURL = streamingURL;
    }
}
