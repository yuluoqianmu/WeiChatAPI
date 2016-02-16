package com.laipai.base.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
    /**
     * 描述：
     *
     * @param path
     *            需要压缩的图片路径
     * @param fileName
     *            要压缩的图片名称
     * @param toFileName
     *            压缩后的图片名称
     * @param scale
     *            压缩比例 不能大于1,默认0.5
     * @param quality
     *            压缩品质介于0.1~1.0之间
     * @param width
     *            压缩后的图片的宽度
     * @param height
     *            压缩后的图片的高度 返回值：void
     */
    public static void imageCompress(String path,
            float scale, float quality, int width, int height) {
        try { // 原图路径 原图名称 目标路径 压缩比率0.5 0.75 原图宽度 原图高度
            long start = System.currentTimeMillis();
            Image image = javax.imageio.ImageIO.read(new File(path));
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            

            /*if (scale > 0.5)
                scale = 0.5f;// 默认压缩比为0.5，压缩比越大，对内存要去越高，可能导致内存溢出
*/            // 按比例计算出来的压缩比
           //float realscale = getRatio(imageWidth, imageHeight, width, height);
            float finalScale = scale;
            if(imageWidth<=1600){        	   
        	   finalScale = 1;
            }else if(imageWidth>1600&&imageWidth<=1920){
            	
            	finalScale = 0.6f;
            }else if(imageWidth>3200&&imageWidth<=4800){
            	
            	finalScale = 0.4f;
            }else{
            	
            	finalScale = 0.3f;
            }            
            imageWidth = (int) (finalScale * imageWidth);
            imageHeight = (int) (finalScale * imageHeight);

            image = image.getScaledInstance(imageWidth, imageHeight,
                    Image.SCALE_SMOOTH);
            // Make a BufferedImage from the Image.
           
            BufferedImage mBufferedImage = new BufferedImage(imageWidth,
                    imageHeight, BufferedImage.TYPE_INT_RGB);
          /*  Graphics2D g2 = mBufferedImage.createGraphics();*/
            mBufferedImage.getGraphics().drawImage(image, 0, 0, null);
            FileOutputStream out = new FileOutputStream(path);  

           /* float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
                    -0.125f, -0.125f, -0.125f, -0.125f };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);*/
            ImageIO.write(mBufferedImage, "JPEG", new File(path));  
            System.out.println(path);
            // JPEGEncodeParam param =
            // encoder.getDefaultJPEGEncodeParam(bufferedImage);
            // param.setQuality(0.9f, true);
            // encoder.setJPEGEncodeParam(param);
           /* JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder
                    .getDefaultJPEGEncodeParam(mBufferedImage);
            param.setQuality(quality, true);// 默认0.75
            encoder.setJPEGEncodeParam(param);
            encoder.encode(mBufferedImage);*/
            out.close();
            long end = System.currentTimeMillis();
            System.out.println("图片，压缩时间：" + (end - start)
                    + "ms");
        } catch (FileNotFoundException fnf) {
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public static void imageCompress(String path,
             float scale, int width, int height) {
        imageCompress(path,  scale, 0.75f, width, height);
    }

    private static float getRatio(int width, int height, int maxWidth,
            int maxHeight) {// 获得压缩比率的方法
        float Ratio = 1.0f;
        float widthRatio;
        float heightRatio;
        widthRatio = (float) maxWidth / width;
        heightRatio = (float) maxHeight / height;
        if (widthRatio < 1.0 || heightRatio < 1.0) {
            Ratio = widthRatio <= heightRatio ? widthRatio : heightRatio;
        }
        return Ratio;
    }

    public static byte[] convertImage2Type(String imageFile, String imageType)
            throws Exception {// 图片格式转换
        File inputFile = new File(imageFile);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage input = ImageIO.read(inputFile);
        ImageIO.write(input, imageType, output);
        return output.toByteArray();
    }

    public static void convertImage2TypePng(String imageFile, String imageType)
            throws Exception {// 图片格式转换
        File inputFile = new File(imageFile);
        int suffixIndex = imageFile.lastIndexOf(".");
        String suffix = imageFile.substring(suffixIndex + 1);
        if (!"png".equals(suffix)) {// 如果原图片的不是PNG格式的图片
            String fileName = imageFile.substring(0, suffixIndex + 1) + "png";
            File output = new File(fileName);
            BufferedImage input = ImageIO.read(inputFile);
            ImageIO.write(input, imageType, output);
            // 转换后删除原文件
            if (inputFile.exists())
                inputFile.delete();
        }
    }
    
    public static void main(String[] args) {
    	ImageUtil imgUtil =new ImageUtil();
    	File f= new File("D:\\testPic\\psb.jpg"); 
    	System.out.println(f.length());
    	long fileSize=f.length();
    	
    	imgUtil.imageCompress("D:\\testPic\\psb.jpg", 0.4f,0.4f, 0, 0);

	}

}
