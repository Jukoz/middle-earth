package net.jukoz.me.utils.resources;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.chunkgen.map.ImageUtils;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import org.joml.Vector2i;
import org.joml.sampling.Convolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FileUtils {

    private static FileUtils single_instance = null;
    private static HashMap<Integer, float[]> gaussianBlurKernels = new HashMap<>();
    private static final float GAUSSIAN_SIGMA = 3.81f;

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

    private static final Vector2i[] directions = {new Vector2i(-1, 1), new Vector2i(0, 1), new Vector2i(1, 1),
                                            new Vector2i(-1, 0), new Vector2i(1, 0),
                                            new Vector2i(-1, -1), new Vector2i(0, -1), new Vector2i(1, -1)};
    public BufferedImage getRunImageWithBorders(int x, int y, int padding) {
        String basePath = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION);
        String centerPath = basePath + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x, y);
        BufferedImage centerImage = getRunImage(centerPath);
        if(centerImage == null) return null;

        int width = centerImage.getWidth();
        int height = centerImage.getHeight();

        BufferedImage imageWithBorders = new BufferedImage(width + 2*padding, height + 2*padding, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = imageWithBorders.createGraphics();

        graphics.setColor(MEBiomesData.defaultBiome.color);
        graphics.fillRect(0, 0, width + 2*padding, height + 2*padding);
        graphics.drawImage(centerImage, padding, padding, null);

        for(Vector2i direction : directions) {
            String edgePath = basePath + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x + direction.x, y + direction.y);
            BufferedImage edgeImage = getRunImage(edgePath);
            if(edgeImage != null) {
                graphics.drawImage(edgeImage, padding + (width * direction.x), padding + (height * direction.y), null);
            }
        }
        graphics.dispose();

        return imageWithBorders;
    }

    public void saveImage(BufferedImage bufferedImage, String path, String fileName, FileType fileType) {
        try{
            new File(path).mkdirs();
            File f = new File(path + fileName);
            ImageIO.write(bufferedImage, fileType.extension, f);
        } catch(Exception e){
            LoggerUtil.logError("Image Utils couldn't save image for {0}.".formatted(path + fileName));
        }
    }


    /**
     * TODO : Optimise this part, it the longest process in World-Gen
     * about 60s before -> about 40s now
     */
    public static BufferedImage blur(BufferedImage image, int brushSize, boolean crop) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width + (2 * brushSize);
        int newHeight = height + (2 * brushSize);

        BufferedImage imageWithBorders = image;
        Graphics2D g2d;
        if(!crop) { // CLAMP_TO_BORDER
            imageWithBorders = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = imageWithBorders.createGraphics();

            g2d.setColor(MEBiomesData.defaultBiome.color);
            g2d.fillRect(0, 0, newWidth, newHeight);
            g2d.drawImage(image, brushSize, brushSize, null);
        } else { // CLAM_TO_EDGE
            imageWithBorders = new BufferedImage(newWidth, newHeight, image.getType());
            g2d = imageWithBorders.createGraphics();

            // Copy image content (center)
            g2d.drawImage(image, brushSize, brushSize, null);

            // extend left & right
            g2d.drawImage(image, 0, brushSize, brushSize, height + brushSize, 0, 0, 1, height, null);
            g2d.drawImage(image, newWidth - brushSize, brushSize, newWidth, height + brushSize, width - 1, 0, width, height, null);

            // extend up & down
            g2d.drawImage(image, brushSize, 0, brushSize + width, brushSize, 0, 0, width, 1, null);
            g2d.drawImage(image, brushSize, newHeight - brushSize, brushSize + width, newHeight, 0, height - 1, width, height, null);

            // extend corners
            g2d.drawImage(image, 0, 0, brushSize, brushSize, 0, 0, 1, 1, null);
            g2d.drawImage(image, newWidth - brushSize, 0, newWidth, brushSize, width - 1, 0, width, 1, null);
            g2d.drawImage(image, 0, newHeight - brushSize, brushSize, newHeight, 0, height - 1, 1, height, null);
            g2d.drawImage(image, newWidth - brushSize, newHeight - brushSize, newWidth, newHeight, width - 1, height - 1, width, height, null);
        }

        float[] blurKernel = new float[brushSize*brushSize];

        if(gaussianBlurKernels.containsKey(brushSize)) {
            blurKernel = gaussianBlurKernels.get(brushSize);
        }
        else {
            Convolution.gaussianKernel(brushSize, brushSize, GAUSSIAN_SIGMA, blurKernel);
            gaussianBlurKernels.put(brushSize, blurKernel);
        }
        Kernel kernel = new Kernel(brushSize, brushSize, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage blurredImage = new BufferedImage(width, height, image.getType());
        op.filter(image, blurredImage);

        if(crop) {
            return blurredImage.getSubimage(brushSize, brushSize, width - brushSize*2, height - brushSize*2);
        } else {
            return blurredImage;
        }
    }
}
