package org.g23.json.entities;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestampMs",
    "latitudeE7",
    "longitudeE7",
    "accuracy",
    "activity"
})
public class Location {

    @JsonProperty("timestampMs")
    private Timestamp timestampMs;
    @JsonProperty("latitudeE7")
    private int latitudeE7;
    @JsonProperty("longitudeE7")
    private int longitudeE7;
    @JsonProperty("accuracy")
    private int accuracy;
    @JsonProperty("activity")
    private List<Activity> activity = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("timestampMs")
    public Timestamp getTimestampMs() {
        return timestampMs;
    }

    @JsonProperty("timestampMs")
    public void setTimestampMs(Timestamp timestampMs) {
        this.timestampMs = timestampMs;
    }

    public Location withTimestampMs(Timestamp timestampMs) {
        this.timestampMs = timestampMs;
        return this;
    }

    @JsonProperty("latitudeE7")
    public int getLatitudeE7() {
        return latitudeE7;
    }

    @JsonProperty("latitudeE7")
    public void setLatitudeE7(int latitudeE7) {
        this.latitudeE7 = latitudeE7;
    }

    public Location withLatitudeE7(int latitudeE7) {
        this.latitudeE7 = latitudeE7;
        return this;
    }

    @JsonProperty("longitudeE7")
    public int getLongitudeE7() {
        return longitudeE7;
    }

    @JsonProperty("longitudeE7")
    public void setLongitudeE7(int longitudeE7) {
        this.longitudeE7 = longitudeE7;
    }

    public Location withLongitudeE7(int longitudeE7) {
        this.longitudeE7 = longitudeE7;
        return this;
    }

    @JsonProperty("accuracy")
    public int getAccuracy() {
        return accuracy;
    }

    @JsonProperty("accuracy")
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public Location withAccuracy(int accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    @JsonProperty("activity")
    public List<Activity> getActivity() {
        return activity;
    }

    @JsonProperty("activity")
    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public Location withActivity(List<Activity> activity) {
        this.activity = activity;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Location withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("timestampMs", timestampMs).append("latitudeE7", latitudeE7).append("longitudeE7", longitudeE7).append("accuracy", accuracy).append("activity", activity).append("additionalProperties", additionalProperties).toString();
    }

}
