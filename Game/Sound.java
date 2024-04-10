package Game;

import javax.sound.sampled.*; 
 
class Sound 
{
	AudioInputStream audioInputStream;
	Clip audio;
	Clip creditsAudio;
	public Sound() 
	{	
		try {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("assets/platformer_music.wav"));
		    audio = AudioSystem.getClip();
		    audio.open(audioInputStream);
		    FloatControl volume= (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
		    volume.setValue(1.0f);
		}catch (Exception e){e.printStackTrace();}
		
		try {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("assets/credits_music.wav"));
		    creditsAudio = AudioSystem.getClip();
		    creditsAudio.open(audioInputStream);
		    FloatControl volume= (FloatControl) creditsAudio.getControl(FloatControl.Type.MASTER_GAIN);
		    volume.setValue(1.0f);
		}catch (Exception e){e.printStackTrace();}
	}
	public void stopAll() {
		audio.stop();
		creditsAudio.stop();
	}
}
