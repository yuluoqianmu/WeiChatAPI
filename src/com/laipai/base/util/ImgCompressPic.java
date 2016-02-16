package com.laipai.base.util;

import java.awt.Graphics;
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import javax.imageio.ImageIO;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
  
/******************************************************************************* 
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true)) 
 */  
 public class ImgCompressPic {   
     private File file = null; // 文件对象   
    /* private String inputDir; // 输入图路径  
     private String outputDir; // 输出图路径  
     private String inputFileName; // 输入图文件名  
     private String outputFileName; // 输出图文件名  
*/   private int outputWidth = 1024; // 默认输出图片宽  
     private int outputHeight = 756; // 默认输出图片高  
     private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
     private float pre;
     public void setPre(float pre) {
		this.pre = pre;
	}

	private String imagePath;
     public ImgCompressPic() { // 初始化变量  
       /*  inputDir = "";   
         outputDir = "";   
         inputFileName = "";   
         outputFileName = "";*/ 
         imagePath="";
         outputWidth = 1024;   
         outputHeight = 768; 
         pre=0.5f;
     }   
     public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
/*	public void setInputDir(String inputDir) {   
         this.inputDir = inputDir;   
     }   
     public void setOutputDir(String outputDir) {   
         this.outputDir = outputDir;   
     }   
     public void setInputFileName(String inputFileName) {   
         this.inputFileName = inputFileName;  
     }   
     public void setOutputFileName(String outputFileName) {   
         this.outputFileName = outputFileName;   
     } */  
     public void setOutputWidth(int outputWidth) {  
         this.outputWidth = outputWidth;   
     }   
     public void setOutputHeight(int outputHeight) {   
         this.outputHeight = outputHeight;   
     }   
     public void setWidthAndHeight(int width, int height) {   
         this.outputWidth = width;  
         this.outputHeight = height;   
     }   
       
     /*  
      * 获得图片大小  
      * 传入参数 String path ：图片路径  
      */   
     public long getPicSize(String path) {   
         file = new File(path);   
         return file.length();   
     }  
       
     // 图片处理   
     public String compressPic() {   
         try {   
             //获得源文件   
             file = new File(imagePath);   
             if (!file.exists()) {   
                 return "";   
             }   
             Image img = ImageIO.read(file);  
             //System.out.println("获得图片流。。。。");
             // 判断图片格式是否正确   
             if (img.getWidth(null) == -1) {  
                 System.out.println(" can't read,retry!" + "<BR>");   
                 return "no";   
             } else {   
                 int newWidth; int newHeight;   
                 // 判断是否是等比缩放   
                 if (this.proportion == true) {   
                     // 为等比缩放计算输出的图片宽度及高度   
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;   
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;   
                     // 根据缩放比率大的进行缩放控制   
                     double rate = rate1 > rate2 ? rate1 : rate2;   
                     newWidth = (int) (((double) img.getWidth(null)) / rate);   
                     newHeight = (int) (((double) img.getHeight(null)) / rate);   
                 } else {   
                     newWidth = outputWidth; // 输出的图片宽度   
                     newHeight = outputHeight; // 输出的图片高度   
                 }   
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                //System.out.println("目标。。。。");
                /* 
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
                 * 优先级比速度高 生成的图片质量比较好 但速度慢 
                 */   
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                //System.out.println("绘制图片。。。。");

                FileOutputStream out = new FileOutputStream(imagePath);  
                //System.out.println("获取输出流。。。。");
/*                // JPEGImageEncoder可适用于其他图片类型的转换   
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
                System.out.println("生成图片完毕。。。。");
               encoder.encode(tag);   */
/*                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图  
                g.dispose();  */
               
                
               /* tag.getGraphics().dispose();*/
                // 输出为文件  
               /* JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                JPEGEncodeParam jep=JPEGCodec.getDefaultJPEGEncodeParam(tag);
                jep.setQuality(pre, true);*/
                ImageIO.write(tag, "JPEG", new File(imagePath));  
               /* encoder.encode(tag, jep);*/
                out.close(); 

             }   
         } catch (IOException ex) {   
             ex.printStackTrace();   
         }   
         return "ok";   
    }   
    public String compressPic (String imagePath,float pre) {   
       
        this.pre=pre;
        this.imagePath=imagePath;
        return compressPic();   
    }   
    public String compressPic(String imagePath, int width, int height, boolean gp) {   
    	this.imagePath=imagePath;
        // 设置图片长宽  
        setWidthAndHeight(width, height);   
        // 是否是等比缩放 标记   
        this.proportion = gp;   
        return compressPic();   
    }   
      
    // main测试   
    // compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))  
   
    public static void main(String[] args) {
    	ImgCompressPic imgCompress=new ImgCompressPic();
    	imgCompress.compressPic("D://testPic//QQ图片20150204154236.jpg",0.5f);
	}
 }  