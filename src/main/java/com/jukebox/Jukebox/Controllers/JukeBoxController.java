package com.jukebox.Jukebox.Controllers;

import com.jukebox.Jukebox.Helpers.JukeBoxHelper;
import com.jukebox.Jukebox.Model.JukeBox;
import com.jukebox.Jukebox.Model.JukeBoxComplete;
import com.jukebox.Jukebox.Model.JukeBoxSettings;
import com.jukebox.Jukebox.Model.Settings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/jukebox")
public class JukeBoxController {

    JukeBoxHelper jukeBoxHelper;

//    public JukeBoxController(JukeBoxHelper jukeBoxHelper) {  }

    @RequestMapping(value = {"/{settingId}","/{settingId}/{model}"})
    public List<JukeBoxComplete>  getJukeBoxes(@PathVariable String settingId, @PathVariable(required = false) String model) {

        JukeBoxHelper jukeBoxHelper = new JukeBoxHelper();
        HashMap<String, ArrayList<JukeBoxComplete>> settingToJukeBox = new HashMap<>();
        List<JukeBox> jukeBoxesToReturn = new ArrayList<>();
        List<JukeBox> jukeBoxes =jukeBoxHelper.getJukeBoxes();
        Settings jukeBoxSettings = jukeBoxHelper.getSettings();
        List<JukeBoxComplete> jukeBoxCompletes = jukeBoxHelper.getJukeBoxComplete();

        //return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
        //Thows error here
        if(settingId == null) {
            return null;
        }

        for (Object jukeBoxe: jukeBoxCompletes.toArray()) {
            JukeBoxComplete castedJukeBox = (JukeBoxComplete) jukeBoxe;

            for (Object jukeBoxSetting:jukeBoxSettings.getSettings()) {
                JukeBoxSettings castedJukeBoxSetting = (JukeBoxSettings) jukeBoxSetting;

                if(jukeBoxHelper.listEqualsIgnoreOrder(castedJukeBox.getComponents(),((JukeBoxSettings) jukeBoxSetting).requires)){

                    if(settingToJukeBox.containsKey(castedJukeBoxSetting.id)){
                        settingToJukeBox.get(((JukeBoxSettings) jukeBoxSetting).id).add(castedJukeBox);
                    }
                    else {
                        ArrayList<JukeBoxComplete> arrayJukeComplete = new ArrayList<JukeBoxComplete>();
                        arrayJukeComplete.add(castedJukeBox);
                        settingToJukeBox.put((castedJukeBoxSetting).id,arrayJukeComplete);
                    }
                }
            }

        }
        List<JukeBoxComplete> returnedValue = settingToJukeBox.get(settingId);
        if(model.isEmpty()){
            return returnedValue;
        }
        else {
            List<JukeBoxComplete> jukeBoxByModel = new ArrayList<>();
            for (Object jukeBox: returnedValue) {
                JukeBoxComplete jukeBoxCompleteToUse = (JukeBoxComplete) jukeBox;
                if(jukeBoxCompleteToUse.model.equals(model)){
                    jukeBoxByModel.add(jukeBoxCompleteToUse);
                }
            }
            return jukeBoxByModel;
        }
    }


}
