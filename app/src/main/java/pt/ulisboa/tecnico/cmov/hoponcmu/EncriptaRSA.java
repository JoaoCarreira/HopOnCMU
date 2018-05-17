package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.security.keystore.KeyProperties;

import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;


public class EncriptaRSA {

    public static KeyPair gerachave() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            kpg.initialize(1024);
            kp = kpg.generateKeyPair();

            return kp;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return kp;

    }


    /**
     * Criptografa o texto puro usando chave pública.
     */
    public static byte[] criptografa(String texto, PublicKey chave) {

        byte[] cipherText = null;

        ObjectInputStream inputStream = null;
        //inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
        //final PublicKey chavePublica = (PublicKey) inputStream.readObject();

        try {
            final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    /**
     * Decriptografa o texto puro usando chave privada.
     */
    public static String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }





}
