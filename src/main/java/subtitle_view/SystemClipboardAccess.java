package subtitle_view;

import java.awt.datatransfer.*;
import java.awt.*;
import java.io.IOException;

/**
 * Demo to access System Clipboard
 */
public class SystemClipboardAccess {

    public static void main(String args[]) throws Exception {
       System.out.println(readClipboard());
    }

    public static String readClipboard() throws IOException, UnsupportedFlavorException {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    public static void clearClipboard() {
        StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                stringSelection, null);
    }
}
