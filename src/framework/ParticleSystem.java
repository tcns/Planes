package framework;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ParticleSystem {
	private Particle[] mParticles;
	private int PARTICLECOUNT = 20;
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndexBuffer;

	public ParticleSystem() {
		mParticles = new Particle[PARTICLECOUNT];

		// Random gen = new Random(System.currentTimeMillis());
		for (int i = 0; i < PARTICLECOUNT; i++) {
			mParticles[i] = new Particle();
		}

		float[] verticesBuffer = { -0.1f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f };
		short[] indicesBuffer = { 0, 1 };

		mVertexBuffer = makeFloatBuffer(verticesBuffer);
		mIndexBuffer = makeShortBuffer(indicesBuffer);

	}

	private FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	// use to make native order buffers
	private ShortBuffer makeShortBuffer(short[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer ib = bb.asShortBuffer();
		ib.put(arr);
		ib.position(0);
		return ib;
	}

	public void draw(GL10 gl) {
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColor4f(1f, 1f, 1f, 1f);
		for (int i = 0; i < PARTICLECOUNT; i++) {
			gl.glPushMatrix();
			gl.glTranslatef(mParticles[i].x, mParticles[i].y, 0);
			gl.glDrawElements(GL10.GL_TRIANGLES, 2, GL10.GL_UNSIGNED_SHORT,
					mIndexBuffer);
			gl.glPopMatrix();
		}
	}

	public void update() {
		int multi = 10;
		for (int i = 0; i < PARTICLECOUNT; i++) {
			mParticles[i].y = mParticles[i].y - 0.01f*multi;
			if (mParticles[i].y < 0.0f) {
				mParticles[i].y = 1.0f*multi;
			}
		}
	}
}
