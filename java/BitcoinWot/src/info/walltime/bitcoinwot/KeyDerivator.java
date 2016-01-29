package info.walltime.bitcoinwot;

import com.lambdaworks.crypto.SCrypt;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import org.bitcoinj.core.ECKey;

public class KeyDerivator {

    private final String username;
    private final String password;
    
    public KeyDerivator(String username, String password) {
        this.username = username;
        this.password = password;    
    }
    
    public ECKey generateKey() throws GeneralSecurityException, UnsupportedEncodingException {
        final String composedKey = username + ":" + password + ":tatualado";
        byte[] hash = SCrypt.scrypt(composedKey.getBytes("UTF-8"),
        new String("Uzy7pjlwDkwIWuq").getBytes("UTF-8"), 
        1048576, 8, 4, 32);

        ECKey generatedKey = ECKey.fromPrivate(hash, false);
        return generatedKey;
    }
}
