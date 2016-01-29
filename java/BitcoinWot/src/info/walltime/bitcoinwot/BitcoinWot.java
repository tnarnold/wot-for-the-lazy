package info.walltime.bitcoinwot;

import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bitcoinj.core.ECKey;
import org.jibble.pircbot.IrcException;

public class BitcoinWot {

    public static final IrcBot BOT = new IrcBot();
    public static final AtomicBoolean REGISTERING = new AtomicBoolean();
    public static final AtomicBoolean VERIFYING_EMAIL = new AtomicBoolean();
    
    public static Login LOGIN;
    public static Password PASSWORD;
    public static ECKey KEY;
    public static String PASSWORD_STRING;
    public static RegisteringWot REGISTERING_WOT;
    public static VerifyEmail VERIFY_EMAIL;

    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });

        BOT.setAutoNickChange(true);
        BOT.setVerbose(true);
        BOT.connect("chat.freenode.net");
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    
}
