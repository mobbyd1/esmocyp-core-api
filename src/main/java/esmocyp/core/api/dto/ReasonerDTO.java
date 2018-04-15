package esmocyp.core.api.dto;

import java.util.List;

public class ReasonerDTO {

    private String query;
    private String streamingURL;
    private String namedModel;
    private String baseURI;
    private String aBox;
    private String tbox;

    private List<String> uuids;

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

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    public String getBaseURI() {
        return baseURI;
    }

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public String getaBox() {
        return aBox;
    }

    public void setaBox(String aBox) {
        this.aBox = aBox;
    }
}
