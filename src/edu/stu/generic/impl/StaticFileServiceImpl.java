package edu.stu.generic.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

import edu.stu.generic.StaticFileService;

public class StaticFileServiceImpl implements StaticFileService {

	/**
	 * @uml.property  name="folder"
	 */
	private String folder;
	/**
	 * @uml.property  name="serverUrl"
	 */
	private String serverUrl;
	
	
	public void setFolder(String folder) {
		this.folder = folder;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	private 	Random rand = new Random();

	@Override
	public String save2file(byte[] fileData,String suffix) throws IOException {
		
		//生成保存路径
		StringBuilder path=getStoreFolder();
		File storeFolder=new File(path.toString());
		storeFolder.mkdirs();
		
		path.append("/").append(generateFileName(suffix));
		
		//转jpeg图
		OutputStream out=null;
		try{
			out=new FileOutputStream(path.toString());
			BufferedImage img=ImageIO.read(new ByteArrayInputStream(fileData));	
			if("png".equals(suffix))
			{
				ImageIO.write(img, "png", out);
			}
			else
			{
				JPEGEncodeParam param=new JPEGEncodeParam();
				param.setQuality(0.9f);		
				ImageEncoder encoder=ImageCodec.createImageEncoder("JPEG",out,param);
				encoder.encode(img);
			}
		}finally
		{
			if(out!=null)
			{
				out.flush();
				out.close();
			}			
		}
				
		//返回web可访问的地址
		return path.delete(0, folder.length()).insert(0, serverUrl).toString();
	}
	
	private synchronized StringBuilder generateFileName(String suffix)
	{		
		char[] randoms = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
				'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuilder buf=new StringBuilder();
		buf.append(System.currentTimeMillis()).append(randoms[rand.nextInt(randoms.length)]).append(randoms[rand.nextInt(randoms.length)]);
		if(suffix!=null && suffix.length()>0)
			buf.append(".").append(suffix);
		return buf;
	}
	
	private StringBuilder getStoreFolder()
	{		
		Calendar cal=Calendar.getInstance();
		StringBuilder buf=new StringBuilder();
		buf.append(folder).append("/").append(cal.get(Calendar.YEAR)).append("/").append(cal.get(Calendar.MONTH)+1).append("/").append(cal.get(Calendar.DAY_OF_MONTH));	
		return buf;
	}

	@Override
	public String save2file(InputStream ins, String suffix) throws IOException {
		//生成保存路径
		StringBuilder path=getStoreFolder();
		File storeFolder=new File(path.toString());
		storeFolder.mkdirs();
		
		path.append("/").append(generateFileName(suffix));
		
		OutputStream out=null;
		try{
			out=new FileOutputStream(path.toString());

			int len=0;
			byte[] b=new byte[512];
			while((len=ins.read(b))>0){
				out.write(b,0,len);
			}
		}finally
		{
			if(out!=null)
			{
				out.flush();
				out.close();
			}			
		}
				
		//返回web可访问的地址
		return path.delete(0, folder.length()).insert(0, serverUrl).toString();
	}

	@Override
	public boolean delete(String url) {
		String realPath=this.folder+url.substring(serverUrl.length());
		return new File(realPath).delete();
	}

	@Override
	public String save2file(File file, String suffix) throws IOException {
		InputStream ins=null;
		try
		{
			ins=new FileInputStream(file);
			return save2file(ins, suffix);
		}
		finally
		{
			if(ins!=null)
				ins.close();
		}
		
	}

	@Override
	public String getFilePath(String url) {
		return this.folder+url.substring(serverUrl.length());
	}
	
	@Override
	public String getFileUrl(String filePath) {
		return this.serverUrl+filePath.substring(this.folder.length());
	}
	
	

	@Override
	public String generateUrl(String suffix) {
		StringBuilder buf=generateFileName(suffix);
		return buf.insert(0, this.getStoreFolder().delete(0, folder.length())).insert(0,this.serverUrl).toString();
	}
	
	
}
