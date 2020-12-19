package ninja.bytecode.shuriken.raspberry;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.sun.javafx.scene.CameraHelper;
import ninja.bytecode.shuriken.execution.J;
import ninja.bytecode.shuriken.format.Form;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.math.M;
import ninja.bytecode.shuriken.math.PSW;
import ninja.bytecode.shuriken.random.RNG;
import ninja.bytecode.shuriken.raspberry.util.CameraStream;
import ninja.bytecode.shuriken.raspberry.util.ColorDisplay;
import ninja.bytecode.shuriken.raspberry.util.SmoothColorDisplay;
import org.checkerframework.checker.units.qual.C;
import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.Encoding;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ShurikenRaspberry
{
    private static boolean down = false;
    private static long downAt = M.ms();

    public static void main(String[] a)
    {
        L.i("Starting Shuriken Raspberry");
        AtomicBoolean busy = new AtomicBoolean(false);
        ExecutorService exx = Executors.newSingleThreadExecutor();
        GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput r = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "RED", PinState.LOW);
        final GpioPinDigitalOutput g = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "GREEN", PinState.LOW);
        final GpioPinDigitalOutput b = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "BLUE", PinState.LOW);
        final GpioPinDigitalOutput beep = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "BEEP", PinState.HIGH);
        GpioPinDigitalInput button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "Camera Button", PinPullResistance.PULL_DOWN);
        r.setShutdownOptions(true, PinState.LOW);
        g.setShutdownOptions(true, PinState.LOW);
        b.setShutdownOptions(true, PinState.LOW);
        beep.setShutdownOptions(true, PinState.HIGH);
        CameraStream st = new CameraStream(CameraConfiguration.cameraConfiguration()
                .encoding(Encoding.JPEG)
                .captureTimeout(0)
                .quality(85));
        st.open();
        SmoothColorDisplay c = new SmoothColorDisplay(Color.RED, 0.07f, r, g, b);
        c.start();
        Color readyColor = new Color(0x005940);
        Color readyReadyColor = new Color(0x66ff00);
        Color shotColor = new Color(0x00fff7);
        c.setColor(readyColor);

        J.a(() -> {
            beep.pulse(250, PinState.LOW);
            J.sleep(250);
            beep.pulse(250, PinState.LOW);
        });

        button.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {
                if(e.getEdge().equals(PinEdge.RISING))
                {
                    c.setColor(readyReadyColor);
                    down = false;
                }

                if(e.getState().isHigh())
                {
                    down = false;
                }

                if(e.getEdge().equals(PinEdge.FALLING))
                {
                    if(!down)
                    {
                        downAt = M.ms();
                    }

                    down = true;

                    if(!busy.get())
                    {
                        busy.set(true);
                        exx.execute(() -> {
                            c.setColor(shotColor);
                            File f = st.capture();
                            busy.set(false);
                            c.setColor(readyColor);
                            J.a(() -> {

                                beep.pulse(15, PinState.LOW);
                            });
                            L.v("Captured Photo " + f.getAbsolutePath());
                        });
                    }
                }
            }
        });

        while(true)
        {
            J.sleep(500);

            down = !button.isLow();

            if(down && M.ms() - downAt > 3000)
            {
                c.setColor(new Color(0x88fc03));
                beep.pulse(100, PinState.LOW);
            }

            if(down && M.ms() - downAt > 4000)
            {
                c.setColor(new Color(0xb5fc03));
                beep.pulse(200, PinState.LOW);
            }

            if(down && M.ms() - downAt > 5000)
            {
                c.setColor(new Color(0xfcf803));
                beep.pulse(200, PinState.LOW);
            }

            if(down && M.ms() - downAt > 6000)
            {
                c.setColor(new Color(0xfcb103));
                beep.pulse(300, PinState.LOW);
            }

            if(down && M.ms() - downAt > 7000)
            {
                c.setColor(new Color(0xfc5603));
                beep.pulse(400, PinState.LOW);
            }

            if(down && M.ms() - downAt > 8000)
            {
                c.setColor(new Color(0xfc3503));
                beep.pulse(499, PinState.LOW);
            }

            if(down && M.ms() - downAt > 9000)
            {
                c.setColor(new Color(0xfc2803));
                beep.pulse(499, PinState.LOW);
            }

            if(down && M.ms() - downAt > 10000)
            {
                c.setColor(new Color(0xfc0303));
                beep.pulse(10000, PinState.LOW);
                System.exit(0);
            }
        }
    }
}
