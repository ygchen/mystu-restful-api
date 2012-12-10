package edu.stu.generic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface StaticFileService {
	String save2file(byte[] srcFileData,String suffix) throws IOException;
	String save2file(InputStream srcIns,String suffix) throws IOException;
	String save2file(File src,String suffix) throws IOException;
	String getFilePath(String url);
	String getFileUrl(String filePath);
	String generateUrl(String suffix);
	boolean delete(String url);
}
