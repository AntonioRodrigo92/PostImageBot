import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader {

    public static BufferedImage urlToImage (String site) throws IOException {
        BufferedImage img = null;
        URL url = new URL(site);
        img = ImageIO.read(url);
        return img;
    }

    public static File bufferedImageToFile (BufferedImage img) throws IOException {
        File file = new File("imagem.png");
        ImageIO.write(img, "png", file);
        return file;
    }

    public static InputStream bufferedImageToInputStream (BufferedImage img) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    public static BufferedImage resizeImage (BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        Image tmp = img.getScaledInstance(w/2, h/2, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(w/2, h/2, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
