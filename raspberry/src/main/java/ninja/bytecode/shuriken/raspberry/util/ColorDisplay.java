package ninja.bytecode.shuriken.raspberry.util;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import ninja.bytecode.shuriken.execution.J;
import ninja.bytecode.shuriken.logging.L;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ColorDisplay
{
    private Thread ticker;
    private final GpioPinDigitalOutput red;
    private final GpioPinDigitalOutput green;
    private final GpioPinDigitalOutput blue;
    private final AtomicBoolean running;
    private final AtomicInteger tick;
    protected final AtomicReference<Color> color;

    public ColorDisplay(GpioPinDigitalOutput red, GpioPinDigitalOutput green, GpioPinDigitalOutput blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.tick = new AtomicInteger(0);
        this.color = new AtomicReference<>(Color.WHITE);
        this.running = new AtomicBoolean(false);
    }

    public void setColor(Color c)
    {
        color.set(c);
    }

    public void start()
    {
        running.set(true);
        this.ticker = new Thread(() -> {
            while(running.get() && !Thread.interrupted())
            {
                try
                {
                    tick();
                }

                catch(Throwable e)
                {
                    L.f("Color Display crashed!");
                    L.ex(e);
                    stop();
                }
            }
        });
        ticker.start();
    }

    public void stop()
    {
        ticker.interrupt();
        running.lazySet(false);
    }

    protected void tick() {
        setRGB(color.get(), tick.get(), red, green, blue);
    }

    public static void setRGBState(boolean r, boolean g, boolean b, GpioPinDigitalOutput... pins) {
        pins[0].setState(r);
        pins[1].setState(g);
        pins[2].setState(b);
    }

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public static void setRGBHex(String hex, int tick, GpioPinDigitalOutput... pins)  {
        setRGB(hex2Rgb(hex), tick, pins);
    }

    public static void setRGB(Color color, int tick, GpioPinDigitalOutput... pins) {
        int rc = color.getRed();
        int gc = color.getGreen();
        int bc = color.getBlue();
        int sector = 0;

        while(rc > 0 || gc > 0 || bc > 0 || sector < 256)
        {
            sector++;

            if(sector % 3 == 0 && rc > 0 && gc > 0)
            {
                setRGBState(false, false, true, pins);
                rc -= 1;
                gc -= 1;
                continue;
            }

            else if(sector % 3 == 0 && rc > 0 && bc > 0)
            {
                setRGBState(false, true, false, pins);
                rc -= 1;
                bc -= 1;
                continue;
            }

            else if(sector % 3 == 1 && gc > 0 && bc > 0)
            {
                setRGBState(true, false, false, pins);
                gc -= 1;
                bc -= 1;
                continue;
            }

            else if(sector % 3 == 1 && gc > 0 && rc > 0)
            {
                setRGBState(false, false, true, pins);
                gc -= 1;
                rc -= 1;
                continue;
            }

            else if(sector % 3 == 2 && bc > 0 && rc > 0)
            {
                setRGBState(false, true, false, pins);
                bc -= 1;
                rc -= 1;
                continue;
            }

            else if(sector % 3 == 2 && bc > 0 && gc > 0)
            {
                setRGBState(true, false, false, pins);
                bc -= 1;
                gc -= 1;
                continue;
            }

            else if(sector % 3 == 0 && rc > 0)
            {
                setRGBState(false, true, true, pins);
                rc -= 1;
                continue;
            }

            else if(sector % 3 == 1 && gc > 0)
            {
                setRGBState(true, false, true, pins);
                gc -= 1;
                continue;
            }

            else if(sector % 3 == 2 && bc > 0)
            {
                setRGBState(true, true, false, pins);
                bc -= 1;
                continue;
            }

            else
            {
                setRGBState(true, true, true, pins);
                continue;
            }
        }
    }
}
