import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

public class ScreenClipFrame extends JFrame {
    private ScreenshotArea screenshotArea;
    private ScreenshotAreaPane screenshotAreaPane;

    public ScreenClipFrame() {
        screenshotArea = new ScreenshotArea();
        screenshotAreaPane = new ScreenshotAreaPane(screenshotArea);
        this.add(screenshotAreaPane);
    }
}
