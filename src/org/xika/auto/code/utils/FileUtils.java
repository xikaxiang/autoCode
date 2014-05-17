package org.xika.auto.code.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {

	
	/**
	 * 删除指定的文件夹及文件夹内的文件
	 * 
	 * @param f
	 */
	public static void delFiles(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File tf : files) {
				delFiles(tf);
			}

		}
		f.delete();
	}

	/**
	 * 从指定文件夹中读取所有文件
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static void readFiles(List<File> files, File f) throws IOException {
		if (null != f) {

			String aPath = f.getAbsolutePath();
			
			if (f.isDirectory()) {
				for (File tf : f.listFiles()) {
					if (tf.isFile()) {
						files.add(tf);
					} else {
						readFiles(files, tf);
					}
				}
			} else {
				files.add(f);
			}
		}
	}

	public static Map<String, File> getFilesAndRelativePath(File rootFile)
			throws IOException {
		Map<String, File> maps = new HashMap<String, File>();

		List<File> files = new ArrayList<File>();
		readFiles(files, rootFile);
		String rootPath = rootFile.getAbsolutePath();
		for (File f : files) {
			maps.put(f.getAbsolutePath().substring(rootPath.length()), f);
		}

		return maps;
	}

	/**
	 * 文件复制
	 * @param sf
	 * @param tf
	 */
	public static void copyFile(String sf, String tf) {
		File fa = new File(sf);
		if (!fa.exists()) {
			System.out.println(sf + "Not Exists. ");
			return;
		}

		File fb = new File(tf);
		if (fa.isFile()) {
			FileInputStream fis = null;
			FileOutputStream fos = null;

			byte[] bb = new byte[(int) fa.length()];
			try {
				 fis  =   new  FileInputStream(fa);
	             fos  =    new  FileOutputStream(fb);
				
				fis.read(bb);
				fos.write(bb);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else if (fa.isDirectory()) {
			if (!fb.exists()) {
				fb.mkdir();
			}

			String[] fileList = fa.list();
			for (int i = 0; i < fileList.length; i++) {
				copyFile(sf + File.separator + fileList[i], tf + File.separator+ fileList[i]);
			}
		}

	}

	public static void main(String[] args) throws IOException {
		String templeteCatalogue = "java";
		copyFile("java","output");
	}
	
	/**
	 * 根据2个文件获取文件的相对路径
	 * @param file
	 * @param tf
	 * @return
	 */
	public static String getRelativePath(File rootFile, File file) {
		if(rootFile.equals(file)){
			return "";
		}
		
		if(rootFile.getParentFile()==null){
			return file.getAbsolutePath().substring(rootFile.getAbsolutePath().length());
		}

		return file.getAbsolutePath().substring((int)rootFile.getAbsoluteFile().length()+1);
	}
}
