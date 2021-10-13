package com.example.webclientdemo.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepoRequest {
    @NotBlank
    private String name;

    private String description;

    @JsonProperty("private")
    private Boolean isPrivate;


    public RepoRequest() {

    }

    public RepoRequest(@NotBlank String name, String description) {
        this.name = name;
        this.description = description;
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
