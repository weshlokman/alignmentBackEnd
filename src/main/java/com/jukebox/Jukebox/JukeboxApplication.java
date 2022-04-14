package com.jukebox.Jukebox;

import com.jukebox.Jukebox.Helpers.JukeBoxHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JukeboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(JukeboxApplication.class, args);
		JukeBoxHelper helper = new JukeBoxHelper();
	}


}
