package subtitle_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TrayHelper {

    static TrayIcon trayIcon;

    public TrayHelper() {
        show();
    }

    public static void show() {
        if (!SystemTray.isSupported()){
            System.exit(0);
        }

        trayIcon = new TrayIcon(createIcon("/subtitle_view/img.png","Icon"));
        final  SystemTray tray = SystemTray.getSystemTray();

        final PopupMenu menu = new PopupMenu();

        MenuItem about = new MenuItem("about");
        MenuItem exit = new MenuItem("exit");

        menu.add(about);
        menu.addSeparator();
        menu.add(exit);

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Author by Igor" );
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        trayIcon.setPopupMenu(menu);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    protected static Image createIcon(String path, String desc) {
        URL imageURL = TrayHelper.class.getResource(path);
        return (new ImageIcon(imageURL, desc)).getImage();
    }
}
