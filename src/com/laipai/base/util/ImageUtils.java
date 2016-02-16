package com.laipai.base.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public  class ImageUtils {
	private Logger log=Logger.getLogger(getClass());
	private Image	srcImage	= null;
	private File	destFile	= null;
	private String	fileSuffix	= null;
	private int imageWidth = 0;
	private int imageHeight = 0;
    

	public ImageUtils(File fileName,String target,String alias) throws IOException {
		File _file = fileName;
		_file.setReadOnly();
		this.fileSuffix = _file.getName().substring(
				(_file.getName().indexOf(".") + 1),
				(_file.getName().length()));
		this.destFile = new File(target+alias+"."+this.fileSuffix);
		srcImage = javax.imageio.ImageIO.read(_file);
		//得到图片的原始大小， 以便按比例压缩。
		imageWidth = srcImage.getWidth(null);
		imageHeight = srcImage.getHeight(null);
		log.debug("width: " + imageWidth);
		log.debug("height: " + imageHeight);		
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * @param w int 新宽度
	 * @param h int 新高度
	 * @throws IOException
	 */
	public void resize(int w, int h) throws IOException {
		//得到合适的压缩大小，按比例。
		if ( imageWidth >= imageHeight)
		{
			h = (int)Math.round((imageHeight * w * 1.0 / imageWidth));
		}
		else 
		{
			w = (int)Math.round((imageWidth * h * 1.0 / imageHeight));
		}

		//构建图片对象
		BufferedImage _image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		//绘制缩小后的图
		_image.getGraphics().drawImage(srcImage, 0, 0, w, h, null);
		//输出到文件流
		FileOutputStream out = new FileOutputStream(destFile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(_image);
		out.flush();
		out.close();
		
	}
	public static void main(String[] args) throws IOException {
		ImageUtils mage=new ImageUtils(new File("D:\\opt\\img\\lpGalleryImg\\11832b17-e9bd-4a2d-a857-31b04297d425.jpg"),"E:\\","压缩后的图片");
		mage.resize(408, 305);
	}
}
