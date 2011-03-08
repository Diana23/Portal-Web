/**
 * 
 */
package com.cablevision.util;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil extends Component {

	public static void reducirImagen(String pathImgOriginal, String pathImgResult, int width, int height) throws ImageFormatException, IOException {
		//esto deja la imagen original en un objeto Image 
		Image image = Toolkit.getDefaultToolkit().getImage(pathImgOriginal); 
		ImageManagerUtil gImg = new ImageManagerUtil(image); // para que espere a que la imagen este cargada
		image = gImg.getImage();

		// esto reduce la imagen a los valores de las variables width, height 
		BufferedImage tnsImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB); 
		Graphics2D graphics2D = tnsImg.createGraphics(); 
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
		graphics2D.drawImage(image, 0, 0, width, height, null); 

		//esto guarda la imagen en un fichero jpg 
		File f = new File(pathImgResult);
		if(f.exists()) f.delete();
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f)); 
		//creamos el "parseador" para guardar la imagen en formato JPG 
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tnsImg); 

		//Asignamos la calidad con la que se va a guardar la imagen de 0-100
		int calidad = 100;
		calidad = Math.max(0, Math.min(calidad, 100)); 
		param.setQuality((float)calidad / 100.0f, false); 
		encoder.setJPEGEncodeParam(param); 
		encoder.encode(tnsImg); 
		out.close();  
	}

	public static void scale(String src, String dest, int width, int height) throws IOException {
		BufferedImage bsrc = ImageIO.read(new File(src));
		BufferedImage bdest =
			new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bdest.createGraphics();
		AffineTransform at =
			AffineTransform.getScaleInstance((double)width/bsrc.getWidth(),
					(double)height/bsrc.getHeight());
		g.drawRenderedImage(bsrc,at);
		ImageIO.write(bdest,"JPG",new File(dest));
	}

}
