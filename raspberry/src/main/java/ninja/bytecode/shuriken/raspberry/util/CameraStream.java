package ninja.bytecode.shuriken.raspberry.util;

import ninja.bytecode.shuriken.logging.L;
import uk.co.caprica.picam.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraStream {
    private static boolean installed = false;
    private final CameraConfiguration config;
    private Camera camera;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CameraStream(CameraConfiguration config)
    {
        if(!installed)
        {
            try {
                PicamNativeLibrary.installTempLibrary();
                installed = true;
                L.v("Installed Native PiCam Libraries through temp JNI");
            } catch (NativeLibraryException e) {
                e.printStackTrace();
            }
        }

        this.config = config;
    }

    public File capture()
    {
        File f = nextFile();

        if(!capture(f))
        {
            return null;
        }

        return f;
    }

    private File nextFile() {
        File dir = new File("dcim");

        if(!dir.exists())
        {
            dir.mkdirs();
        }

        int c = 1;
        File f;
        while(true)
        {
            f = new File("dcim/" + simpleDateFormat.format(new Date()) + " (" + (c++) + ").jpg");

            if(!f.exists())
            {
                break;
            }
        }

        return f;
    }

    public boolean capture(File f)
    {
        try {
            camera.takePicture(new FilePictureCaptureHandler(f));
            return true;
        } catch (CaptureFailedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void close()
    {
        try
        {
            camera.close();
        }

        catch(Throwable e)
        {

        }
    }

    public boolean open()
    {
        try {
            camera = new Camera(config);
            return true;
        } catch (CameraException e) {
            e.printStackTrace();
        }

        return false;
    }
}
