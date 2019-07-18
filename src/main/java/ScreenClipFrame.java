import javax.swing.*;

public class ScreenClipFrame extends JFrame {
    private ScreenshotArea screenshotArea;
    private ScreenshotAreaPane screenshotAreaPane;

    public ScreenClipFrame() {
        screenshotArea = new ScreenshotArea();
        screenshotAreaPane = new ScreenshotAreaPane(screenshotArea);
        this.add(screenshotAreaPane);
    }
}
