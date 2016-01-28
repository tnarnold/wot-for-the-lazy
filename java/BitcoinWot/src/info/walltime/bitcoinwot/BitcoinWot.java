package info.walltime.bitcoinwot;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import org.jibble.pircbot.IrcException;

public class BitcoinWot {

    public static final IrcBot BOT = new IrcBot();
    public static Login LOGIN;
    public static Password PASSWORD;

    public static void main(String[] args) throws IOException, IrcException {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });

        BOT.setAutoNickChange(true);
        BOT.setVerbose(true);
        BOT.connect("chat.freenode.net");
       
        
        // BOT.joinChannel("#bitcoin-otc");
        
        
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    
}
