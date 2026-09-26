package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import scenes.Util;

public class Audio {
	public static void playFile(String filePath) {
		new Audio(filePath).play();
	}

	private String filePath;
	private Clip clip;
	private AudioInputStream audioStream;

	public static final String NUKE_SOUND = "audio/explosion-meme-made-with-Voicemod.wav";

	public void setVolume(float vol) {
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(vol);
	}

	public Audio(String filePath) {
		this.setFilePath(filePath);
		File soundFile = null;
		soundFile = Util.getFile(filePath);

		if (!soundFile.exists()) {
			System.err.println("Audio file not found: " + filePath);
			return;
		}

		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (UnsupportedAudioFileException e) {
			System.err.println("Unsupported audio file: " + filePath);
		} catch (IOException e) {
			System.err.println("Error reading audio file: " + filePath);
		} catch (LineUnavailableException e) {
			System.err.println("Audio line unavailable for: " + filePath);
		}
	}

	public void play() {
		if (clip != null) {
			if (clip.isRunning()) {
				clip.stop(); // Stop the clip if it's already running
			}

			clip.setFramePosition(0); // Rewind to the beginning
			clip.start();
		} else {
			System.err.println("Clip not initialized. Cannot play sound.");
		}
	}

	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	public void close() {
		if (clip != null) {
			clip.close();
		}
		try {
			if (audioStream != null) {
				audioStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
