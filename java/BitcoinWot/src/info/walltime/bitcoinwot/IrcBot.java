package info.walltime.bitcoinwot;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import org.jibble.pircbot.PircBot;

public class IrcBot extends PircBot {
    @Override
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
        super.onMessage(channel, sender, login, hostname, message);

        System.out.println(sender + ": " + message);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        super.onPrivateMessage(sender, login, hostname, message);
        
        System.out.println("Private message: " + sender + ": " + message);
    }

    @Override
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);

        if (BitcoinWot.LOGIN != null) {
            System.out.println("Notice: " + sourceNick + ": " + notice);

            if (notice.contains("is not registered")) {
                JOptionPane.showMessageDialog(null, 
                        "Esse nick não está registrado, por favor registre antes.");

                BitcoinWot.LOGIN.getjButton1().setEnabled(true);
                BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else if (notice.contains("Registered")) {
                BitcoinWot.LOGIN.setState(Frame.ICONIFIED);

                BitcoinWot.LOGIN.getjButton1().setEnabled(true);
                BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                
                java.awt.EventQueue.invokeLater(() -> {
                    new Password().setVisible(true);
                });
            } else if (notice.contains("has been regained")) {
                BitcoinWot.PASSWORD.dispatchEvent(new WindowEvent(BitcoinWot.PASSWORD, 
                        WindowEvent.WINDOW_CLOSING));
            } else if (notice.contains("invalid password")) {
                            JOptionPane.showMessageDialog(null, 
                        "Senha incorreta!");
            }
        }
    }

    @Override
    protected void onChannelInfo(String channel, int userCount, String topic) {
        super.onChannelInfo(channel, userCount, topic);

        System.out.println("Channel " + channel);

    }

    @Override
    protected void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
        super.onUserMode(targetNick, sourceNick, sourceLogin, sourceHostname, mode);

        if (BitcoinWot.LOGIN != null) {
            BitcoinWot.LOGIN.getjButton1().setEnabled(true);
            BitcoinWot.LOGIN.getjButton2().setEnabled(true);
            BitcoinWot.LOGIN.setCursor(Cursor.getDefaultCursor());
            BitcoinWot.LOGIN.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    
}
