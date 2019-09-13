import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Controller {
    //AES Encryption constants
    private static final String encryptionKey = "ABCDEFGHIJKLMNOP";
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";

    String absolutePath = "C:\\Users\\simon\\IdeaProjects\\3. semester\\encryption\\src\\com\\hawkster\\test.txt";

    public TextArea log;

    public void encryptPressed(ActionEvent actionEvent) {
        String text = log.getText();
        String encrypted = customEncryption(text);
        log.setText(encrypted);
    }

    public void decryptPressed(ActionEvent actionEvent) {
        String encrypted = log.getText();
        String decrypted = customDecryption(encrypted);
        log.setText(decrypted);
    }

    public void updatePressed(ActionEvent actionEvent) {
        String text = getTextFromDocument();
        log.setText(text);
    }

    public String customEncryption(String string){
        char[] chars = string.toCharArray();
        String encrypted = "";
        for (int i = 0; i < chars.length; i++) {
            encrypted +=(char) (((int) chars[i]) + 2);
        }
        writeTextToDocument(encrypted);
        return encrypted;
    }

    public String customDecryption(String string){
        char[] chars = string.toCharArray();
        String decrypted = "";
        for (int i = 0; i < chars.length; i++) {
            decrypted +=(char) (((int) chars[i]) - 2);
        }
        writeTextToDocument(decrypted);
        return decrypted;
    }

    public String getTextFromDocument(){
        String document = "";
        System.out.println(absolutePath);
        try (FileReader fileReader = new FileReader(absolutePath)) {
            int ch = fileReader.read();
            while (ch != -1) {
                document += (char) ch;
                ch = fileReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public void writeTextToDocument(String text){
        try(FileWriter fileWriter = new FileWriter(absolutePath, false)){
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aesEncryptPressed(ActionEvent actionEvent) {
        String plain = log.getText();
        String encrypted = aesEncrypt(plain);
        log.setText(encrypted);
        writeTextToDocument(encrypted);
    }

    public void aesDecryptPressed(ActionEvent actionEvent) {
        String encrypted = log.getText();
        String decrypted = aesDecrypt(encrypted);
        log.setText(decrypted);
        writeTextToDocument(decrypted);
    }

    public String aesEncrypt(String text){
        String encrypted = "";
        try{
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            Base64.Encoder encoder = Base64.getEncoder();
            encrypted = encoder.encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    public String aesDecrypt(String encryptedText){
        String decrypted = "";
        try{
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes(StandardCharsets.UTF_8));
            decrypted = new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
        return decrypted;
    }
}
