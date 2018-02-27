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
    "activity"
})
public class Activity {

    @JsonProperty("timestampMs")
    private Timestamp timestampMs;
    @JsonProperty("activity")
    private List<Activity_> activity = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    public Activity() {}

    public Activity(Timestamp timestampMs, List<Activity_> activity) {
		super();
		this.timestampMs = timestampMs;
		this.activity = activity;
	}

	@JsonProperty("timestampMs")
    public Timestamp getTimestampMs() {
        return timestampMs;
    }

    @JsonProperty("timestampMs")
    public void setTimestampMs(Timestamp timestampMs) {
        this.timestampMs = timestampMs;
    }

    public Activity withTimestampMs(Timestamp timestampMs) {
        this.timestampMs = timestampMs;
        return this;
    }

    @JsonProperty("activity")
    public List<Activity_> getActivity() {
        return activity;
    }

    @JsonProperty("activity")
    public void setActivity(List<Activity_> activity) {
        this.activity = activity;
    }

    public Activity withActivity(List<Activity_> activity) {
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

    public Activity withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("timestampMs", timestampMs).append("activity", activity).append("additionalProperties", additionalProperties).toString();
    }

}
