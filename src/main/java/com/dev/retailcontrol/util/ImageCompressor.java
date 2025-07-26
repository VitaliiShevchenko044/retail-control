package com.dev.retailcontrol.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageCompressor {

    public static byte[] compress(MultipartFile imageFile,
                                  int width,
                                  int height,
                                  float quality) throws IOException {
        BufferedImage original = ImageIO.read(imageFile.getInputStream());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Thumbnails.of(original)
                .size(width, height)
                .outputFormat("jpeg")
                .outputQuality(quality)
                .toOutputStream(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}