package org.academiadecodigo.bootcamp8.tests.client;

import org.academiadecodigo.bootcamp8.message.Message;
import org.academiadecodigo.bootcamp8.message.Sendable;
import org.academiadecodigo.bootcamp8.tests.Crypto;
import org.academiadecodigo.bootcamp8.tests.utils.Converter;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by André Martins <Code Cadet>
 *         Tests (25/06/2017)
 *         <Academia de Código_>
 */
public class Client {

    Socket socket;

    public static void main(String[] args) {

        Client client = new Client();
        System.out.println("Connected to server");
        client.init();

    }

    private Client() {

        try {
            socket = new Socket("localhost", 12345);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init() {

        BufferedInputStream in = null;
        CipherInputStream cin = null;

        try {

            in = new BufferedInputStream(socket.getInputStream());

            SecretKey key = (SecretKey) read(in);
            Crypto crypto = new Crypto(Cipher.DECRYPT_MODE, key);

            cin = new CipherInputStream(socket.getInputStream(), crypto.getCipher());
            System.out.println(read(cin));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (cin != null) {
                    cin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object read(InputStream in) {

        byte[] bytes = null;

        try {

            List<Byte> list = new LinkedList<>();

            byte b;
            while ((b = (byte) in.read()) != -1) {
                list.add(b);
            }

            bytes = Converter.toPrimitiveByteArray(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Converter.toObject(bytes);

    }

}
