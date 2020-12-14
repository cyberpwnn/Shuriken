package ninja.bytecode.shuriken.cloud.web;

import java.io.InputStream;

public interface UploadParcelable
{
	public Parcelable respond(InputStream in);
}
