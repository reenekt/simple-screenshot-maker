import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class Main {
    public static final String applicationTitle = "Simple Screenshot making App by reenekt"; //old name "Simple Screener App by reenekt"
    public static final String ICON_STR = "/images/image64x64.png"; // TODO make normal icon
    public static int mainWindowWidth = 400; // TODO make normal width
    public static int mainWindowHeight = 250; // TODO make normal height
    public static JFrame frame = new JFrame();
    public static SystemTray tray;

    public static void main(String[] args) {
        frame.setTitle(applicationTitle);
        frame.setSize(mainWindowWidth, mainWindowHeight);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(false);
        frame.setLayout(null);

        JButton makeScreenshotButton = new JButton("Make screenshot"); // TODO localize
        makeScreenshotButton.setBounds(10, 10, 140, 35);
        frame.add(makeScreenshotButton);

        ActionListener makeScreenshotButtonListener = e -> new ScreenClip();
        makeScreenshotButton.addActionListener(makeScreenshotButtonListener);
        setTrayIcon();
    }

    public static void setTrayIcon() {
        if(! SystemTray.isSupported() ) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();

        MenuItem makeScreenshotItem = new MenuItem("Make screenshot");
        makeScreenshotItem.addActionListener(e -> new ScreenClip());
        trayMenu.add(makeScreenshotItem);

        MenuItem openMainWindowItem = new MenuItem("Open main window");
        openMainWindowItem.addActionListener(e -> frame.setVisible(true));
        trayMenu.add(openMainWindowItem);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        trayMenu.add(exitItem);

        URL imageURL = Main.class.getResource(ICON_STR);

        Image icon = Toolkit.getDefaultToolkit().getImage(imageURL);
        TrayIcon trayIcon = new TrayIcon(icon, applicationTitle, trayMenu);
        trayIcon.setImageAutoSize(true);

        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new ScreenClip();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage(applicationTitle, "Ready to make screenshots!",
                TrayIcon.MessageType.INFO);
    }
}
