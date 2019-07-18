import java.awt.*;

public class ScreenshotArea {
    private int x;
    private int y;
    private int width;
    private int height;

    public ScreenshotArea() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.width = 0;
        this.height = 0;
    }

    @Override
    public String toString() {
        return getClass().getName() + " | X: " + this.x + ", Y: " + this.y + ", width: " + this.width + ", height: " + this.height;
    }

    public ScreenshotArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
