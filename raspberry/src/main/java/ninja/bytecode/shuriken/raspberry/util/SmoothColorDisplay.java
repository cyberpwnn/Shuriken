package ninja.bytecode.shuriken.raspberry.util;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import ninja.bytecode.shuriken.execution.ChronoLatch;
import ninja.bytecode.shuriken.math.M;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.concurrent.atomic.AtomicReference;

public class SmoothColorDisplay extends ColorDisplay
{
    private final float speed;
    private final ChronoLatch cl;
    private final AtomicReference<Color> target;
    private final AtomicReference<Color> current;
    private final float[] a = new float[3];
    private final float[] b = new float[3];

    public SmoothColorDisplay(Color initialColor, float speed, GpioPinDigitalOutput red, GpioPinDigitalOutput green, GpioPinDigitalOutput blue) {
        super(red, green, blue);
        this.speed = speed;
        cl = new ChronoLatch(4);
        target = new AtomicReference<>(initialColor);
        current = new AtomicReference<>(initialColor);
        super.setColor(initialColor);
    }

    @Override
    protected void tick() {
        if(cl.flip())
        {
            interpolateColor();
        }
        super.tick();
    }

    private void interpolateColor() {
        Color cu = current.get();
        Color tu = target.get();
        Color.RGBtoHSB(cu.getRed(),cu.getGreen(), cu.getBlue(), a);
        Color.RGBtoHSB(tu.getRed(),tu.getGreen(), tu.getBlue(), b);
        a[0] = M.flerp(a[0], b[0], speed);
        a[1] = M.flerp(a[1], b[1], speed);
        a[2] = M.flerp(a[2], b[2], speed);
        Color n = new Color(Color.HSBtoRGB(a[0], a[1], a[2]));
        super.setColor(n);
        current.set(n);
    }

    @Override
    public void setColor(Color c)
    {
        target.set(c);
    }
}
