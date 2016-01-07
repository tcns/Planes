package implemented;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import framework.FileIO;

public class AndroidFileIO implements FileIO {

	AssetManager assets;
	String sdCard;

	public AndroidFileIO(AssetManager mng) {
		this.assets = mng;
		sdCard = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	@Override
	public InputStream readAsset(String fileName) throws IOException {

		return assets.open(fileName);
	}

	@Override
	public InputStream readFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return new FileInputStream(sdCard + fileName);
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return new FileOutputStream(sdCard + fileName);
	}

}
