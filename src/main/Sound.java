package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
		soundURL[2] = getClass().getResource("/sound/powerup.wav");
		soundURL[3] = getClass().getResource("/sound/unlock.wav");
		soundURL[4] = getClass().getResource("/sound/fanfare.wav");
//		soundURL[] = getClass().getResource("/sound/<suono>.wav");
	}
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.addLineListener(e -> { if (e.getType() == LineEvent.Type.STOP) e.getLine().close(); });
		}catch(Exception e) {
			clip = null;
			System.err.println("Audio non disponibile (" + i + "): " + e);
		}
	}
	public void play() {
		if (clip != null) clip.start();
	}
	public void loop() {
		if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		if (clip != null) clip.stop();
	}
}
