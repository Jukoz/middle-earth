package net.jukoz.me.utils.resources;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.map.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileUtils {

    private static FileUtils single_instance = null;

    public static synchronized FileUtils getInstance()
    {
        if (single_instance == null)
            single_instance = new FileUtils(ClassLoader.getSystemClassLoader());

        return single_instance;
    }

    private ClassLoader classLoader;

    public FileUtils(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public BufferedImage getResourceImage(String path) {
        try{
            return ImageUtils.fetchResourceImage(getClass().getClassLoader(), path);
        } catch (IOException e) {
            return null;
        }
    }

    public BufferedImage getRunImage(String path) {
        try{
            File f = new File(path);
            if(!f.exists())
                return null;
            return ImageIO.read(f);
        } catch (IOException e) {
            return null;
        }
    }

    public void saveImage(BufferedImage bufferedImage, String path, String fileName, FileType fileType) {
        try{
            new File(path).mkdirs();
            File f = new File(path + fileName);
            ImageIO.write(bufferedImage, fileType.extension, f);
        } catch(Exception e){
            LoggerUtil.getInstance().logError("Image Utils couldn't save image for {0}.".formatted(path + fileName));
        }
    }


    /**
     * TODO : Optimise this part, it the longest process in World-Gen
     */
    public static BufferedImage blur(BufferedImage image, int brushSize) {
        // Create new expended image :
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width + (2 * brushSize);
        int newHeight = height + (2 * brushSize);

        BufferedImage expendedImage = new BufferedImage(newWidth, newHeight, image.getType());
        // Copy image content
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                expendedImage.setRGB(x + brushSize, y + brushSize, image.getRGB(x, y));
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < brushSize; x++) {
                expendedImage.setRGB(x, y + brushSize, image.getRGB(0, y)); // Left edge
                expendedImage.setRGB(width + brushSize + x, y + brushSize, image.getRGB(width - 1, y)); // Right edge
            }
        }

        for (int x = 0; x < width + 2 * brushSize; x++) {
            for (int y = 0; y < brushSize; y++) {
                expendedImage.setRGB(x, y, expendedImage.getRGB(x, brushSize)); // Top edge
                expendedImage.setRGB(x, height + brushSize + y, expendedImage.getRGB(x, height + brushSize - 1)); // Bottom edge
            }
        }

        float[] blurKernel = new float[brushSize * brushSize];
        Arrays.fill(blurKernel, 1.0f / (brushSize * brushSize));
        Kernel kernel = new Kernel(brushSize, brushSize, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        expendedImage = op.filter(expendedImage, null);


        return expendedImage.getSubimage(brushSize, brushSize, width, height);
    }
}
