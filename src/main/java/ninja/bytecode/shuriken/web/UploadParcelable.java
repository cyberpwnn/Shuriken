package ninja.bytecode.shuriken.web;

import java.io.InputStream;

public interface UploadParcelable
{
	public Parcelable respond(InputStream in);
}
