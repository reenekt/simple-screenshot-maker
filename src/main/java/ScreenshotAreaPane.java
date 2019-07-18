import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScreenshotAreaPane extends JPanel implements MouseListener, MouseMotionListener, ClipboardOwner {
    private ScreenshotArea screenshotArea;

    public ScreenshotAreaPane(ScreenshotArea screenshotArea) {
        this.screenshotArea = screenshotArea;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void makeRepaint() {
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Area outter = new Area(new Rectangle(0, 0, getWidth(), getHeight()));

        int paintX = screenshotArea.getX();
        int paintY = screenshotArea.getY();
        int paintWidth = screenshotArea.getWidth();
        int paintHeight = screenshotArea.getHeight();
        if (paintWidth < 0) {
            paintWidth = -paintWidth;
            paintX -= paintWidth;
        }
        if (paintHeight < 0) {
            paintHeight = -paintHeight;
            paintY -= paintHeight;
        }
        Rectangle inner = new Rectangle(paintX, paintY, paintWidth, paintHeight);
        outter.subtract(new Area(inner));

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(new Color(0, 0, 0, 192));
        g2d.fill(outter);
        g2d.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int width = e.getX() - screenshotArea.getX();
        int height = e.getY() - screenshotArea.getY();
        screenshotArea.setWidth(width);
        screenshotArea.setHeight(height);
        makeRepaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.screenshotArea.setX(e.getX());
        this.screenshotArea.setY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int width = e.getX() - this.screenshotArea.getX();
        int height = e.getY() - this.screenshotArea.getY();
        this.screenshotArea.setWidth(width);
        this.screenshotArea.setHeight(height);
        System.out.println(screenshotArea.toString());
        makeRepaint();

        try {
            Robot robot = new Robot();
            int areaX = screenshotArea.getX();
            int areaY = screenshotArea.getY();
            int areaWidth = screenshotArea.getWidth();
            int areaHeight = screenshotArea.getHeight();

            if (areaWidth < 0) {
                areaWidth = -areaWidth;
                areaX -= areaWidth;
            }
            if (areaHeight < 0) {
                areaHeight = -areaHeight;
                areaY -= areaHeight;
            }

            if (areaWidth == 0) {
                areaWidth = 1;
            }
            if (areaHeight == 0) {
                areaHeight = 1;
            }

            Rectangle screenShotArea = new Rectangle(areaX, areaY, areaWidth, areaHeight);
            BufferedImage i = robot.createScreenCapture( screenShotArea );
            TransferableImage trans = new TransferableImage( i );
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents( trans, this);

            // Message TODO: replace [0] to something better
            Main.tray.getTrayIcons()[0].displayMessage(Main.applicationTitle, "Screenshot copied to clipboard!",
                    TrayIcon.MessageType.INFO);
        } catch (AWTException ex) {
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error! Press enter to exit program", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println( "Lost Clipboard Ownership" );
    }

    private class TransferableImage implements Transferable {
        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
                throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}
