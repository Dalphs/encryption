import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

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
            System.out.println("fff");
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
