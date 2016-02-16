package com.lyz.api.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
public class ImgTool {

    private BufferedImage subImg;
    
    
    public static void cutImg(String key, int startX,int startY,int width,int height){
    	
    	
    }
    
    /**
     * 截图
     * @param srcPath
     * @param startX
     * @param startY
     * @param width
     * @param height
     */
    public void cut(String srcPath,int startX,int startY,int width,int height){
        try {
            BufferedImage bufImg = ImageIO.read(new File(srcPath));
            subImg = bufImg.getSubimage(startX, startY, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存截图。
     * @param bufImg
     * @param imgType
     * @param tarPath
     */
    public void save(String imgType,String imgName,String tarPath,int width,int height){
        try {/**压缩图片为指定尺寸*/
            if(subImg.getWidth()!=width || subImg.getHeight()!=height){
                BufferedImage tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
                tempImg.getGraphics().drawImage(subImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);
                ImageIO.write(tempImg, imgType, new File(tarPath+"/"+imgName));
            }else{
                ImageIO.write(subImg,imgType,new File(tarPath+"/"+imgName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]){
    	
    	ImgTool tool = new ImgTool();
    	tool.cut("e://test/2.jpg", 0, 0, 1000, 1000);
    	tool.save("jpg", "3.jpg", "e://test/", 1000, 1000);
    }
}
