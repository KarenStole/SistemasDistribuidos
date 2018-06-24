/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Karen
 */
public class RSA {

    /**
     *
     */
    public static PrivateKey PrivateKey = null;

    /**
     *
     */
    public static PublicKey PublicKey = null;

    /**
     *
     */
    public RSA() {

    }

    /**
     * Setea la clave privada del RSA
     * @param key la clave a setear
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public static void setPrivateKeyString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPrivateKey = stringToBytes(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        RSA.PrivateKey = privateKey;
    }

    /**
     * Setea la clave publica del RSA
     * @param key la clave a setear
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public static void setPublicKeyString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = stringToBytes(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        RSA.PublicKey = publicKey;
    }

    /**
     * Devuelve la clave privada
     * @return la clave en string
     */
    public static String getPrivateKeyString() {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(RSA.PrivateKey.getEncoded());
        return bytesToString(pkcs8EncodedKeySpec.getEncoded());
    }

    /**
     * Devuelve la clave publica
     * @return string clave
     */
    public static String getPublicKeyString() {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(RSA.PublicKey.getEncoded());
        return bytesToString(x509EncodedKeySpec.getEncoded());
    }

    /**
     * Genera un par de claves publica-privada (RSA) y las setea
     * @param size 512, 1024, 2048 o 4096 (bits)
     * @return 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public static KeyPair genKeyPair(int size) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(size);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        RSA.PrivateKey = privateKey;
        RSA.PublicKey = publicKey;
        return kp;
    }

    /**
     * Cifra un texto plano con la clave publica (seteada previamente)
     * @param plain el string a cifrar
     * @return el string cifrado con la clave publica
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException 
     */
    public static String Encrypt(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchProviderException {
        byte[] encryptedBytes;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, RSA.PrivateKey);
        encryptedBytes = cipher.doFinal(plain.getBytes());
        return bytesToString(encryptedBytes);
    }

    /**
     * Decifra un texto codificado usando la clave privada (seteada previamente)
     * @param result el texto cifrado
     * @return el texto decodificado
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public static String Decrypt(String result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] decryptedBytes;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, RSA.PublicKey);
        decryptedBytes = cipher.doFinal(stringToBytes(result));
        return new String(decryptedBytes);
    }

    /**
     * Transforma un array de bytes al formato string. Es necesario realizar la
     * conversion ya que la clase Cipher de javax.cripto utiliza array de bytes
     * @param b byte a transformar
     * @return el string resultado
     */
    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    /**
     * Transforma un string a array de bytes. Es necesario realizar la
     * conversion ya que la clase Cipher de javax.cripto no utiliza Strings
     * @param s el string a convertir
     * @return el array de bytes resultado
     */
    public static byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

    /**
     * Guarda la clave privada a disco
     * @param path la ruta donde sera guardada
     * @throws IOException 
     */
    public static void saveToDiskPrivateKey(String path) throws IOException {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            out.write(RSA.getPrivateKeyString());
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Guarda la clave publica a disco
     * @param path la ruta donde sera guardada
     */
    public static void saveToDiskPublicKey(String path) {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            out.write(RSA.getPublicKeyString());
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Setea una clave publica guardada en disco
     * @param path
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public static void openFromDiskPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = RSA.readFileAsString(path);
        RSA.setPublicKeyString(content);
    }

    /**
     * Setea una clave privada guardada en disco
     * @param path la ruta donde se encuentra la clave
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public static void openFromDiskPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = RSA.readFileAsString(path);
        RSA.setPrivateKeyString(content);
    }

    /**
     * Lee un archivo de texto, en nuestro caso el formulario .txt y lo 
     * transforma a string
     * @param filePath la ruta donde se encuentra el archivo de texto
     * @return el archivo de texto convertido a string
     * @throws IOException 
     */
    public static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

}
