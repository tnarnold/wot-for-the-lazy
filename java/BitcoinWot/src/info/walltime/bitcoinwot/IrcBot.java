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

        if (sender.equals("gribble")) {
            if (message.contains("Your challenge string")) {
                String[] challengeArray = message.split(" ");
                String challenge = challengeArray[challengeArray.length - 1];
                String proof = BitcoinWot.KEY.signMessage(challenge);

                BitcoinWot.BOT.sendMessage("gribble", ";;bcverify " + proof);
            } else if (message.contains("You are now authenticated")) {
                if (BitcoinWot.REGISTERING_WOT != null) {
                    BitcoinWot.REGISTERING_WOT.dispatchEvent(new WindowEvent(BitcoinWot.REGISTERING_WOT, 
                            WindowEvent.WINDOW_CLOSING));
                }

                if (BitcoinWot.CREATING_PAIR != null) {
                    BitcoinWot.CREATING_PAIR.dispatchEvent(new WindowEvent(BitcoinWot.CREATING_PAIR, 
                            WindowEvent.WINDOW_CLOSING));
                }

                if (BitcoinWot.PASSWORD != null) {
                    BitcoinWot.PASSWORD.dispatchEvent(new WindowEvent(BitcoinWot.PASSWORD, 
                            WindowEvent.WINDOW_CLOSING));
                }

                java.awt.EventQueue.invokeLater(() -> {
                    new AuthenticatedUser().setVisible(true);
                });
            }
        }
    }

    @Override
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);

        if (BitcoinWot.LOGIN != null) {
            System.out.println("Notice: " + sourceNick + ": " + notice);

            if (notice.contains("is not registered")) {
                
                if (!BitcoinWot.REGISTERING.get()) {
                    JOptionPane.showMessageDialog(null, 
                            "Esse nick não está registrado, por favor registre antes.");

                    BitcoinWot.LOGIN.getjButton1().setEnabled(true);
                    BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } else {
                    BitcoinWot.LOGIN.getjButton2().setEnabled(true);
                    BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
                    BitcoinWot.LOGIN.setState(Frame.ICONIFIED);

                    java.awt.EventQueue.invokeLater(() -> {
                        new NewPassword().setVisible(true);
                    });
                }
            } else if (notice.contains("Registered")) {
                if (BitcoinWot.REGISTERING.get()) {
                    JOptionPane.showMessageDialog(null, 
                            "Esse nick já existe, por favor escolha outro.");

                    BitcoinWot.LOGIN.getjButton2().setEnabled(true);
                    BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));                    
                } else {
                    BitcoinWot.LOGIN.setState(Frame.ICONIFIED);

                    BitcoinWot.LOGIN.getjButton1().setEnabled(true);
                    BitcoinWot.LOGIN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    java.awt.EventQueue.invokeLater(() -> {
                        new Password().setVisible(true);
                    });
                }
            } if (notice.contains("invalid password")) {
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
