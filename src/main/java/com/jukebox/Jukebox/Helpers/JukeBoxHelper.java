package com.jukebox.Jukebox.Helpers;

import com.jukebox.Jukebox.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
public class JukeBoxHelper {
    private final String JUKEBOXAPI = "http://my-json-server.typicode.com/touchtunes/tech-assignment/";
    private final String JUKEBOXENDPOINT = "jukes";
    private final String SETTINGJUKEBOXENDPOINT = "settings";
    private WebClient webClient;
    private List<JukeBox> jukeBoxes;
    private Settings settings;
    private List<JukeBoxComplete> jukeBoxComplete;

    public JukeBoxHelper(){
        jukeBoxes = (List<JukeBox>) getAllJukeBoxes();
        settings = getAllJukeBoxSettings();
        jukeBoxComplete = (List<JukeBoxComplete>) jukeBoxFormated(jukeBoxes,settings);
    }

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

    public List<JukeBoxComplete> jukeBoxFormated(List<JukeBox> jukeBoxes, Settings setting){
        List<JukeBoxComplete> jukeBoxComplete = new ArrayList<>();
        for (Object jukeBoxe : jukeBoxes.toArray()) {
            JukeBox castedJukeBox = (JukeBox) jukeBoxe;
            List<String> components = new ArrayList<>();
            for (Object component: castedJukeBox.getComponents().toArray()) {
                ComponentTable castedComponent = (ComponentTable) component;
                components.add(castedComponent.getName());
            }
            jukeBoxComplete.add(new JukeBoxComplete(castedJukeBox.id,castedJukeBox.model,components));
        }
        return jukeBoxComplete;
    }

    public <String> boolean listEqualsIgnoreOrder(List<String> list1, List<String> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }


}
