package framework;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Font {
	public final Texture texture;
	public final int glyphWidth;
	public final int glyphHeight;
	public final TextureRegion[] glyphs;
	private static List<Entry> entries = new ArrayList<Entry>();

	private class Entry {
		int x, y;

		@SuppressWarnings("unused")
		int offsetX, offsetY, width, height;
		char code;

		public Entry(String rect, String offset, String width, char code) {
			String[] temp = rect.split(" ");
			this.x = Integer.parseInt(temp[0]);
			this.y = Integer.parseInt(temp[1]);
			this.width = Integer.parseInt(temp[2]);
			this.height = Integer.parseInt(temp[3]);
			temp = offset.split(" ");
			this.offsetX = Integer.parseInt(temp[0]);
			this.offsetY = Integer.parseInt(temp[1]);
			// this.width = Integer.parseInt(width);
			this.code = code;
		}
	}

	private void readEntries(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// parser.require(XmlPullParser.START_TAG, null, "Font");
		entries.clear();
		String code = null;
		String rect = null;
		String offset = null;
		String width = null;
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("Char")) {
					width = parser.getAttributeValue(0);
					offset = parser.getAttributeValue(1);
					rect = parser.getAttributeValue(2);
					code = parser.getAttributeValue(3);
				}
				entries.add(new Entry(rect, offset, width, code.charAt(0)));
			}
			// parser.nextTag();
		}
	}

	public Font(Texture texture, XmlPullParser parser) {
		this.texture = texture;
		glyphWidth = 0;
		glyphHeight = 0;
		try {
			readEntries(parser);

		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		glyphs = new TextureRegion[entries.size()];
		for (int i = 0; i < entries.size(); i++) {
			Entry e = entries.get(i);
			glyphs[i] = new TextureRegion(texture, e.x, e.y, e.width, e.height);

		}

	}

	public Font(Texture texture, int offsetX, int offsetY, int glyphsPerRow,
			int glyphWidth, int glyphHeight) {
		this.texture = texture;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		glyphs = new TextureRegion[96];
		int x = offsetX;
		int y = offsetY;
		for (int i = 0; i < 96; i++) {
			glyphs[i] = new TextureRegion(texture, x, y, glyphWidth,
					glyphHeight);
			x += glyphWidth;
			if (x == offsetX + glyphsPerRow * glyphWidth) {
				x = offsetX;
				y += glyphHeight;
			}
		}
	}

	@SuppressLint("DefaultLocale")
	public void drawText(SpriteBatcher batcher, String text, float x, float y,
			float size) {
		int len = text.length();
		if (entries.size() == 0) {
			for (int i = 0; i < len; i++) {
				int c = text.charAt(i) - ' ';
				if (c < 0 || c > glyphs.length - 1)
					continue;

				TextureRegion glyph = glyphs[c];
				batcher.drawSprite(x, y, glyphWidth, glyphHeight, glyph);
				x += glyphWidth;
			}
		}

		else {
			text = text.toUpperCase();
			for (int i = 0; i < len; i++) {
				int c = text.charAt(i);
				Entry e;
				TextureRegion glyph;

				if (c > 47 && c < 58) {
					glyph = glyphs[c - 48];
					e = entries.get(c - 48);
				} else if (c > 64 && c < 91) {
					glyph = glyphs[c - 55];
					e = entries.get(c - 55);
				} else if (c == 32) {
					x += 10 * size;
					continue;
				} else {
					continue;
				}
				batcher.drawSprite(x, y, e.width * size, e.height * size, glyph);
				if (e.code == '1') {
					x += e.width + 3;
				} else {
					x += e.width * (size + 0.3);
				}
			}
		}
	}

}
