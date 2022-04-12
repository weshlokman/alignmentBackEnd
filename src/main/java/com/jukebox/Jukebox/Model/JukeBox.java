package com.jukebox.Jukebox.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JukeBox{
    @JsonProperty("id")
    public int id;
    @JsonProperty("model")
    public String model;
    @JsonProperty("components")
    public List<Components> components;
}