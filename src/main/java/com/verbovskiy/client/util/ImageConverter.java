package com.verbovskiy.client.util;

import javafx.scene.image.Image;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;

public class ImageConverter {
    private static final Logger logger = LogManager.getLogger(ImageConverter.class);

    public static byte[] convertToBytes(File file) {
        byte[] bytes = new byte[(int) file.length()];
        try(FileInputStream inF = new FileInputStream(file);) {
            int byte1;
            for (int i = 0; (byte1 = inF.read()) > -1; i++) {
                bytes[i] = (byte) byte1;
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "error while converting to bytes");
        }
        return bytes;
    }

    public static Image convertToImage(byte[] bytes) {
        InputStream imageIN = new ByteArrayInputStream(bytes);
        return new Image(imageIN);
    }
}
