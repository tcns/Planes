package implemented;



import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {

	MediaPlayer player;
	boolean isPrepared = false;

	public AndroidMusic(AssetFileDescriptor fd) {
		player = new MediaPlayer();
		try {
			player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
					fd.getLength());
			player.prepare();
			isPrepared = true;
			player.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void play() {
		if (player.isPlaying())
			return;
		try {
			synchronized (this) {
				if (!isPrepared)
					player.prepare();
				player.start();
			}
		} catch (Exception ex) {

		}

	}

	@Override
	public void stop() {
		player.stop();
		synchronized (this) {
			isPrepared = false;
		}

	}

	@Override
	public void pause() {
		player.pause();

	}

	@Override
	public void setLooping(boolean looping) {
		player.setLooping(looping);

	}

	@Override
	public void setVolume(float volume) {
		player.setVolume(volume, volume);

	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		return player.isLooping();
	}

	@Override
	public void dispose() {
		if (player.isLooping()) {
			player.stop();
		}
		player.release();

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized (this) {
			isPrepared = false;
		}

	}

}
