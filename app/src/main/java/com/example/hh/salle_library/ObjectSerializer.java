package com.example.hh.salle_library;

/**
 * Created by Alumne on 14/03/2018.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class ObjectSerializer {

    /**
     *El metodo para convertir datos de byte a string, primero se verifica
     * si el parametro obj está vacio, si no está vacio inicializar el ByteArrayOutputStream
     * y ObjectOutputStream para meter los datos a stream y llamamos al metodo encondeBytes para
     * convertir los datos que está pasando con el parametro obj, una vez cuando el dato está
     * convertido se cierra la coneccion de stream.
     *
     * @param obj
     * @return String
     * @throws IOException
     */
    public static String serialize(Serializable obj) throws IOException {
        if (obj == null) return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * El metodo para convertir datos de string a byte, primero se verifica si el parametro
     * str está vacio, si no está vacio inicializar el ByteArrayInputStream
     * y ObjectInputStream y meter dato al stream, luego utilizamos el metodo decodeBytes para
     * podemos convertir los datos que está pasando con el parametro str, una vez cuando el
     * dato está convertido se cierra la coneccion de stream.
     * @param str
     * @return Byte
     * @throws IOException
     */

    public static Object deserialize(String str) throws IOException {
        if (str == null || str.length() == 0) return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            objStream.close();
            return objStream.readObject();
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * El metodo sirve para convertir el dato de tipo byte a string
     * utilizamos para cuando tenemos que convertir un byte a string
     *
     * @param bytes
     * @return string
     */

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    /**
     * El metodo sirve para convertir el dato de tipo string a byte
     * utilizamos para cuando tenemos que convertir un string a byte
     * @param str
     * @return byte
     */
    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i+=2) {
            char c = str.charAt(i);
            bytes[i/2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i+1);
            bytes[i/2] += (c - 'a');
        }
        return bytes;
    }
}
