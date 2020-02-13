package subtitle_view;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class SubListener{
    public static String SUB_WORD;

    public static class Owner implements ClipboardOwner {

        @Override
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            Transferable newContents = clipboard.getContents(this);
            clipboard.setContents(newContents, this);
            try {
                SUB_WORD = (String) clipboard.getData(DataFlavor.stringFlavor);
                System.out.println(SUB_WORD);
                    //new AnkiHelper();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Owner owner = new Owner();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(owner);
        clipboard.setContents(contents, owner);
        Thread.sleep(3600000);
    }
}


