package edu.stu.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
/*import javax.media.jai.BorderExtender;
import javax.media.jai.ImageLayout;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.operator.TransposeDescriptor;

import com.sun.media.imageio.plugins.tiff.BaselineTIFFTagSet;
import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.imageio.plugins.tiff.TIFFTag;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;*/
import javax.media.jai.BorderExtender;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;

public class ImageUtils
{
 

  public static void scaleAndCropImageFile(String file,int scaledW, int scaledH, int x, int y, int w, int h,String saveTo) throws IOException
  {
	  File f=new File(file);
	  BufferedImage img=ImageIO.read(f);
	  if(scaledW>0 && scaledH>0)
		  img=scale(img, scaledW, scaledH);
	  img=img.getSubimage(x, y, w, h); //crop(img, x, y, w, h);
	  String subfix = file.substring(file.lastIndexOf('.') + 1, file.length());
	  OutputStream out=new FileOutputStream(saveTo);
	  try{
		  ImageIO.write(img, subfix,out);
	  }
	  finally
	  {
		  out.flush();
		  out.close();
	  }
  }
  
  public static BufferedImage scaleAndCropAndAddTransparencyToLeftRightSideFromFile(String file, int scaledW, int scaledH,int x, int y, int w, int h, int distance) throws IOException
  {
	  File f=new File(file);
	  BufferedImage src=ImageIO.read(f);	  
	  if(scaledW>0 && scaledH>0)
		  src=scale(src, scaledW, scaledH);
	  BufferedImage img=src.getSubimage(x, y, w, h);
	  src=new BufferedImage((int)w,(int)h, BufferedImage.TYPE_INT_ARGB);
	  src.getGraphics().drawImage(img, 0, 0, null);
  
	  Color color;
	  int alpha;
	  for(int left=0;left<distance;left++)
	  {
		  alpha=(int)(left*255F/distance);
		  for(int top=0; top<img.getHeight(); top++)
		  {
			  color=new Color( img.getRGB(left, top));			  			 
			  color=new Color(color.getRed(), color.getGreen(),color.getBlue(), alpha);			  
			  src.setRGB(left, top, color.getRGB());
		  }

	  }
	  for(int left=img.getWidth()-distance;left<img.getWidth();left++)
	  {
		  alpha=(int)((img.getWidth()-left-1)*255F/distance);
		  for(int top=0; top<img.getHeight(); top++)
		  {
			  color=new Color( img.getRGB(left, top));			  
			  color=new Color(color.getRed(), color.getGreen(),color.getBlue(), alpha);
			  src.setRGB(left, top,color.getRGB());
		  }		
	  }
	  
	  return src;
	  	  
  }
  
  public static void main(String[] args) throws IOException
  {
//	  System.err.println(Integer.toHexString(new Color(188,188,122,210).getRGB()));
//	  System.err.println(Integer.toHexString(new Color(188,188,122).getRGB()));
	  /*BufferedImage img=scaleAndCropAndAddTransparencyToLeftRightSideFromFile("e:/test2.jpg",900,800, 0, 0, 800, 300, 200);
	  File temp=new File("e:/test2.png");
		FileOutputStream fout=new FileOutputStream(temp);
		ImageIO.write(img, "png", fout);
		fout.flush();
		fout.close();*/
	  
//	  scaleAndCropImageFile("F:/test3.jpg", 0, 0, 0, 0, 142,142);
	  rotate("f:/cover_image.png", 90,"f:/aa.png");
		
  }
  
  
  public static BufferedImage scale(BufferedImage srcImg, int w, int h)
  {
    if (w == 0)
    {
      w = (int)(1.0F * srcImg.getWidth() * h / srcImg.getHeight());
    }
    else if (h == 0)
    {
      h = (int)(1.0F * srcImg.getHeight() * w / srcImg.getWidth());
    }

    Image img = srcImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    BufferedImage ret = new BufferedImage(w, h, srcImg.getType() != 0 ? srcImg.getType() : 2);
    ret.getGraphics().drawImage(img, 0, 0, null);
    return ret;
  }

  public static BufferedImage crop(BufferedImage srcImg, int x, int y, int w, int h)
  {
	  Image img= srcImg.getSubimage(x, y, w, h);
	  BufferedImage newImage=new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	  newImage.getGraphics().drawImage(img, 0, 0, null);
	  return newImage;
  }

  public static BufferedImage rotate(RenderedImage srcImg, int degree)
  {
    float angle = (float)(degree * 0.0174532925199433D);

    ParameterBlock pb = new ParameterBlock();
    pb.addSource(srcImg);
    pb.add(srcImg.getWidth() / 2.0F);
    pb.add(srcImg.getHeight() / 2.0F);
    pb.add(angle);
    pb.add(Interpolation.getInstance(3));
    return JAI.create("Rotate", pb, new RenderingHints(JAI.KEY_BORDER_EXTENDER, BorderExtender.createInstance(0))).getAsBufferedImage();
  }
  
  public static void rotate(String file, int degree, String saveTo) throws IOException 
  {
	  File f=new File(file);
	  BufferedImage img=rotate(ImageIO.read(f), degree);
	  String subfix = file.substring(file.lastIndexOf('.') + 1);
	  ImageIO.write(img, subfix, new File(saveTo));
  }
  
  
  public static int screen2print(int size, int printDPI)
  {
    return (int)(printDPI * size / 96.0F);
  }

  public static void main12(String[] args) throws Exception {
  /*  int left = 1; int center = 3; int right = 5; int top = 7; int middle = 9; int bottom = 11;
    System.out.println(left * top);
    System.out.println(left * middle);
    System.out.println(left * bottom);
    System.out.println(center * top);
    System.out.println(center * middle);
    System.out.println(center * bottom);
    System.out.println(right * top);
    System.out.println(right * middle);
    System.out.println(right * bottom);*/
	  
	  generateThumb("e:/", new File("f:/test.jpg"), 100, 100, "/test_100x100.jpg");
  }

  public static BufferedImage createThumb(BufferedImage srcImg, int w, int h)
  {
    if ((w >= srcImg.getWidth()) && (h >= srcImg.getHeight()))
      return srcImg;
    float x;
    float cropW;
    float cropH;
    float y;
    if (srcImg.getWidth() / srcImg.getHeight() > w / h)
    {
      y = 0.0F;
      cropH = srcImg.getHeight();
      cropW = w * cropH / h;
      x = (srcImg.getWidth() - cropW) / 2.0F;
    }
    else
    {
      x = 0.0F;
      cropW = srcImg.getWidth();
      cropH = h * cropW / w;
      y = (srcImg.getHeight() - cropH) / 2.0F;
    }

    BufferedImage cropImg = crop(srcImg, (int)x, (int)y, (int)cropW, (int)cropH);
    return scale(cropImg, w, h);
  }


  public static BufferedImage changeTransparency(BufferedImage img, Color bg, float alpha)
  {
    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), 2);
    Graphics2D g = (Graphics2D)result.getGraphics();
    if (bg != null)
    {
      g.setBackground(bg);
    }
    else
    {
      g.setBackground(Color.WHITE);
    }
    g.clearRect(0, 0, img.getWidth(), img.getHeight());
    g.setComposite(AlphaComposite.getInstance(3, alpha));
    g.drawImage(img, 0, 0, null);
    g.dispose();
    return result;
  }


  public static void main1(String[] args) throws Exception {
    long l = System.currentTimeMillis();
    BufferedImage src = ImageIO.read(new File("b:/test2.png"));
    BufferedImage ok = scale(src, 200, 200);
    BufferedImage dist = new BufferedImage(200, 200, 1);
    Graphics2D g = (Graphics2D)dist.getGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, dist.getWidth(), dist.getHeight());
    g.drawImage(ok, 0, 0, null);

    ImageIO.write(dist, "png", new File("b:/ok.png"));

    System.out.print(System.currentTimeMillis() - l);
  }

  public static void generateThumb(String storePath, File srcFile, int width, int height, String targetPath)
    throws IOException
  {
    BufferedImage src = ImageIO.read(srcFile);
    float srcRatio = 1.0F * src.getWidth() / src.getHeight();
    float targetRatio = 1.0F * width / height;

    if (targetRatio > srcRatio)
    {
      src = scale(src, width, 0);
    }
    else
    {
      src = scale(src, 0, height);
    }

    int x = 0; int y = 0;
    if (src.getWidth() > width)
    {
      x = (src.getWidth() - width) / 2;
    }
    if (src.getHeight() > height)
    {
      y = (src.getHeight() - height) / 2;
    }
    src = crop(src, x, y, width, height);

    String subfix = targetPath.substring(targetPath.lastIndexOf('.') + 1, targetPath.length());
    BufferedImage target;
    if (subfix.equalsIgnoreCase("png"))
    {
      target = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
      target.getGraphics().drawImage(src, 0, 0, null);
    }
    else
    {
      target = src;
    }
    OutputStream out=new FileOutputStream( new File(storePath + targetPath));
    ImageIO.write(target, subfix,out);
    
    out.flush();
    out.close();
  }
}