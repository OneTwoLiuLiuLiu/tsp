package com.sunyard.frameworkset.plugin.tsp.client.utils;


import info.monitorenter.cpdetector.io.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;


/**
 * 文件操作工具类
 * @author 朱志刚
 *
 */
public abstract class FileUtil {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
//	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * 获取文件编码
	 * @param fileName			文件路径
	 * @return					文件编码
	 * @throws IOException
	 */
	public static Charset getFileEncoding(String fileName)throws IOException{
		File file = new File(fileName);
		return getFileEncoding(file);
	}
	/**
	 * 获取文件编码
	 * @param file				文件
	 * @return					文件编码
	 * @throws IOException
	 */
	public static Charset getStreamEncoding(InputStream in,int length) throws IOException{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        return detector.detectCodepage(in, length);
	}
	
	public static Charset getFileEncoding(File file) throws IOException{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        return detector.detectCodepage(file.toURI().toURL());
	}
	
	public static Charset getEncoding(URL url) throws IOException{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        return detector.detectCodepage(url);
	}
	
	/**
	 * 把输入流解析成字符串
	 * @param in				输入流
	 * @return					解析后的字符串
	 * @throws IOException
	 */
	public static String parseInputStreamToString(InputStream in)throws IOException{
		Charset charset = getStreamEncoding(in,1024);
		charset = null==charset ? DEFAULT_CHARSET : charset;
		return parseInputStreamToString(in,charset);

	}
	
	/**
	 * 把输入流解析成字符串
	 * @param in				输入流
	 * @param charset			编码(为null时默认为UTF-8)
	 * @return					解析后的字符串
	 * @throws IOException
	 */
	public static String parseInputStreamToString(InputStream in,Charset charset)throws IOException{
		StringBuilder sb = new StringBuilder();
		charset = null==charset ? DEFAULT_CHARSET : charset;
		InputStreamReader reader = new InputStreamReader(in,charset);
		char[] cbuf = new char[1024];
		int len = 0 ;
		while((len=reader.read(cbuf))!=-1){
			sb.append(cbuf,0,len);
		}
		reader.close();
		in.close();
		return sb.toString();
	}


	

	
	/**
	 * 把文件内容解析成字符串
	 * @param file				文件
	 * @return					解析后的字符串
	 * @throws IOException
	 */
	public static String parseFileToString(File file)throws IOException{
		if(file.exists()&&file.canRead()){
			Charset charset = getFileEncoding(file);
			FileInputStream fIn = new FileInputStream(file);
			String ret = parseInputStreamToString(fIn,charset);
			fIn.close();
			return ret;
		}
		throw new IOException("file not exist or file can't read!");
	}

	
	public static String parseClassesFileToString(String path) throws IOException{
		for (Enumeration<URL> e = Thread.currentThread()
				.getContextClassLoader().getResources(path); e
				.hasMoreElements();) {
			URL url = e.nextElement();
			return parseInputStreamToString(url.openStream(), getEncoding(url));
		}
		return "";
	}
	/**
	 * 改变文件编码
	 * @param dir				目录／文件
	 * @param charsetName		编码
	 * @throws IOException
	 */
	public static void chgFileEncoding(File dir,String charsetName) throws IOException{
		if(dir.exists()){
			if(dir.isDirectory()){
				for(File f : dir.listFiles()){
					chgFileEncoding(f,charsetName);
				}
			}else{
				String content = parseFileToString(dir);
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dir), charsetName);
				writer.write(content);
				writer.flush();
				writer.close();
			}
		}
	}
	
	public static void writeFileWithEncoding(File file,String content,String charsetName) throws IOException{
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), charsetName);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 改变文件编码
	 * @param dir				目录／文件
	 * @param charsetName		编码
	 * @throws IOException
	 */
	public static void chgFileEncoding(String dir,String charsetName) throws IOException{
		chgFileEncoding(new File(dir),charsetName);
	}
	
	
	
	

}
