import java.awt.*;

public class ScreenClip {
    public ScreenClip() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        ScreenClipFrame frame = new ScreenClipFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(1f, 1f, 1f, 0f));
        frame.setAlwaysOnTop(true);
        // Without this, the window is draggable from any non transparent
        // point, including points  inside textboxes.
        frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
        frame.setSize(width, height);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        ScreenshotArea screenshotArea = new ScreenshotArea();
        ScreenshotAreaPane screenshotAreaPane = new ScreenshotAreaPane(screenshotArea);
        screenshotAreaPane.setOpaque(false);
        frame.add(screenshotAreaPane);
        frame.setVisible(true);
    }
}
