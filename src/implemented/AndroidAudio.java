package implemented;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import framework.Audio;
import framework.Music;
import framework.Sound;

public class AndroidAudio implements Audio {

	AssetManager assets;
	SoundPool soundpool;

	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundpool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music newMusic(String filename) {
		try {
			AssetFileDescriptor fd = assets.openFd(filename);
			AndroidMusic music = new AndroidMusic(fd);
			return music;
		} catch (IOException e) {
			throw new RuntimeException("No Music");
		}
		
	}

	@Override
	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor desc = assets.openFd(filename);
			int SoundID = soundpool.load(desc, 0);
			return new AndroidSound(soundpool, SoundID);
		} catch (IOException e) {
			throw new RuntimeException("Not able to load sound " + filename);
		}

	}

}
