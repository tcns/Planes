package implemented;

import android.media.SoundPool;
import framework.Sound;

public class AndroidSound implements Sound {
	int soundid;
	SoundPool soundpool;

	public AndroidSound(SoundPool pool, int SoundID) {
		this.soundid = SoundID;
		this.soundpool = pool;
	}

	@Override
	public void play(float volume) {
		soundpool.play(soundid, volume, volume, 0, 0, 1);

	}

	@Override
	public void dispose() {
		soundpool.unload(soundid);

	}

}
