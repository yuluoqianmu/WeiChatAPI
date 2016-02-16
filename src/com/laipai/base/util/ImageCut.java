package com.laipai.base.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;  

import javax.imageio.ImageIO;

import com.lyz.img.AliImgServer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class ImageCut {  
    
    /** 
     * 图片切割 
     * @param imagePath  原图地址 
     * @param x  目标切片坐标 X轴起点 
     * @param y  目标切片坐标 Y轴起点 
     * @param w  目标切片 宽度 
     * @param h  目标切片 高度 
     * @param divw 
     * @param divh 
     */  
    public void cutImage(String imagePath, int x ,int y ,int w,int h, int divw, int divh){  
   {  
	   try {  
           Image img;  
           ImageFilter cropFilter;  
           InputStream is = AliImgServer.getImg(imagePath);          
           // 读取源图像  
           BufferedImage bi = ImageIO.read(is);  
           int srcWidth = bi.getWidth();      // 源图宽度  
           int srcHeight = bi.getHeight();    // 源图高度  
             
           //若原图大小大于切片大小，则进行切割  
           if (srcWidth >= w && srcHeight >= h) {  
               Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);  
                 
               int x1 = x*srcWidth/divw;  
               int y1 = y*srcHeight/divh;  
               int w1 = w*srcWidth/divw;  
               int h1 = h*srcHeight/divh;
               
              /* int x1=x;
               int y1=y;
               int w1=w;
               int h1=h;*/
              
               cropFilter = new CropImageFilter(x1, y1, w1, h1);  
               img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));  
               BufferedImage tag = new BufferedImage(w1, h1,BufferedImage.TYPE_INT_RGB);  
               Graphics g = tag.getGraphics();  
               g.drawImage(img, 0, 0, null); // 绘制缩小后的图  
               g.dispose();  
               // 输出为文件  
//               ImageIO.write(tag, "JPEG", new File(imagePath));  
               /*重新写入阿里云图片服务器*/
               ByteArrayOutputStream os = new ByteArrayOutputStream();
               ImageIO.write(tag, "jpg", os);
               InputStream imgIs = new ByteArrayInputStream(os.toByteArray());
               AliImgServer.putImg(imagePath, imgIs);
           }else{
        	   
        	   compressPic(imagePath,400,400,false);       	   
        	   String newImagePath=imagePath;
        	   int newx=x;
        	   int newy=y; 
        	   int neww=w; 
        	   int newh=h;
        	   int newDivw=divw;
        	   int newDivh=divh;
        	   cutImage(newImagePath, newx ,newy ,neww,newh,newDivw, newDivh);       	   
           }
           
           
           
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }  
    }
    //处理尺寸不足的图像（小于400*300）  
    // 图片处理   
    public void compressPic(String imagePath,int outputWidth, int outputHeight,boolean proportion) {   
        try {   
        	 // 读取源图像  
            BufferedImage bi = ImageIO.read(new File(imagePath));   
  
                int newWidth; int newHeight;   
                // 判断是否是等比缩放   
                if (proportion == true) {   
                    // 为等比缩放计算输出的图片宽度及高度   
                    double rate1 = ((double) bi.getWidth(null)) / (double) outputWidth + 0.1;   
                    double rate2 = ((double) bi.getHeight(null)) / (double) outputHeight + 0.1;   
                    // 根据缩放比率大的进行缩放控制   
                    double rate = rate1 > rate2 ? rate1 : rate2;   
                    newWidth = (int) (((double) bi.getWidth(null)) / rate);   
                    newHeight = (int) (((double) bi.getHeight(null)) / rate);   
                } else {   
                    newWidth = outputWidth; // 输出的图片宽度   
                    newHeight = outputHeight; // 输出的图片高度   
                }   
               BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                 
               /* 
                * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
                * 优先级比速度高 生成的图片质量比较好 但速度慢 
                */   
               tag.getGraphics().drawImage(bi.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  

               FileOutputStream out = new FileOutputStream(new File(imagePath));  
               // JPEGImageEncoder可适用于其他图片类型的转换   
               JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
               encoder.encode(tag);   
               out.close();      
        } catch (IOException ex) {   
            ex.printStackTrace();   
        }   
       
   }
    
    public void cutImage2(String imagePath, int x ,int y ,int w,int h, int divw, int divh,String outPath){  
    	   {  
    		   try {  
    	           Image img;  
    	           ImageFilter cropFilter;  
    	           // 读取源图像  
    	           BufferedImage bi = ImageIO.read(new File(imagePath));  
    	           int srcWidth = bi.getWidth();      // 源图宽度  
    	           int srcHeight = bi.getHeight();    // 源图高度  
    	             
    	           //若原图大小大于切片大小，则进行切割  
    	           if (srcWidth >= w && srcHeight >= h) {  
    	               Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);  
    	                 
    	               int x1 = x*srcWidth/divw;  
    	               int y1 = y*srcHeight/divh;  
    	               int w1 = w*srcWidth/divw;  
    	               int h1 = h*srcHeight/divh;
    	               
    	              /* int x1=x;
    	               int y1=y;
    	               int w1=w;
    	               int h1=h;*/
    	              
    	               cropFilter = new CropImageFilter(x1, y1, w1, h1);  
    	               img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));  
    	               BufferedImage tag = new BufferedImage(w1, h1,BufferedImage.TYPE_INT_RGB);  
    	               Graphics g = tag.getGraphics();  
    	               g.drawImage(img, 0, 0, null); // 绘制缩小后的图  
    	               g.dispose();  
    	               // 输出为文件  
    	               ImageIO.write(tag, "JPEG", new File(outPath));  
    	           }else{
    	        	   
    	        	   compressPic(imagePath,400,400,false);       	   
    	        	   String newImagePath=imagePath;
    	        	   int newx=x;
    	        	   int newy=y; 
    	        	   int neww=w; 
    	        	   int newh=h;
    	        	   int newDivw=divw;
    	        	   int newDivh=divh;
    	        	   cutImage2(newImagePath, newx ,newy ,neww,newh,newDivw, newDivh,outPath);       	   
    	           }
    	           
    	           
    	           
    	       } catch (IOException e) {  
    	           e.printStackTrace();  
    	       }  
    	   }  
    	    }
   
}    

