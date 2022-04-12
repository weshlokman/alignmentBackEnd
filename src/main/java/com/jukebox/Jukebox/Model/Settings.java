package com.jukebox.Jukebox.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
    @JsonProperty("settings")
    public List<JukeBoxSettings> settings;
}
