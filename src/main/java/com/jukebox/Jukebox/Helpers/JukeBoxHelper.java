package com.jukebox.Jukebox.Helpers;

import com.jukebox.Jukebox.Model.JukeBox;
import com.jukebox.Jukebox.Model.JukeBoxSettings;
import com.jukebox.Jukebox.Model.Settings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class JukeBoxHelper {
    private final String JUKEBOXAPI = "http://my-json-server.typicode.com/touchtunes/tech-assignment/";
    private final String JUKEBOXENDPOINT = "jukes";
    private final String SETTINGJUKEBOXENDPOINT = "settings";
    private WebClient webClient;

    private void initWebClient(String baseUrl){
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();
    }


    public List<JukeBox> getAllJukeBoxes(){

        initWebClient(JUKEBOXAPI);

        List<JukeBox> jukeBoxes = new ArrayList<>();
        jukeBoxes = (List<JukeBox>) webClient
                .get()
                //Mettre ce qui vient apres base url ici
                .uri(JUKEBOXENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<JukeBox>>() {}).block();

        return jukeBoxes;
    }

    public Settings getAllJukeBoxSettings(){
        initWebClient(JUKEBOXAPI);

        Settings jukeBoxSettings = new Settings();
        jukeBoxSettings = (Settings) webClient
                .get()
                //Mettre ce qui vient apres base url ici
                .uri(SETTINGJUKEBOXENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(Settings.class).block();

        return jukeBoxSettings;
    }


    public JukeBoxHelper(){

    }

}
