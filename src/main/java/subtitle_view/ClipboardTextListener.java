package subtitle_view;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

class ClipboardTextListener extends Observable implements Runnable {

    Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    public void run() {
        System.out.println("Listening to clipboard...");
        // the first output will be when a non-empty text is detected
        String recentContent = "";
        // continuously perform read from clipboard
        while (running) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // request what kind of data-flavor is supported
                List<DataFlavor> flavors = Arrays.asList(sysClip.getAvailableDataFlavors());
                // this implementation only supports string-flavor
                if (flavors.contains(DataFlavor.stringFlavor)) {
                    String data = (String) sysClip.getData(DataFlavor.stringFlavor);
                    if (!data.equals(recentContent)) {
                        recentContent = data;
                        // Do whatever you want to do when a clipboard change was detected, e.g.:
                        System.out.println("New clipboard text detected: " + data);

                        AnkiHelper.WORD = data;
                        String PATTERN = ".*\\b" + data + "\\b.*";
                        AnkiHelper.findInEngSub(data, PATTERN);

                        setChanged();
                        notifyObservers(data);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TrayHelper.show();
        ClipboardTextListener b = new ClipboardTextListener();
        Thread thread = new Thread(b);
        thread.start();
    }
}

