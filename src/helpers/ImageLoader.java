package helpers;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {
    public static Image loadImage(String path){

        URL imageUrl = ImageLoader.class.getResource(path);

        if(imageUrl == null){

            throw new IllegalStateException(
                    "Image not found: " + path
            );
        }

        ImageIcon imageIcon = new ImageIcon(imageUrl);

        return imageIcon.getImage();
    }
}
