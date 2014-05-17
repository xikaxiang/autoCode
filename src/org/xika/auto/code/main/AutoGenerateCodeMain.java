package org.xika.auto.code.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.xika.auto.code.db.Column;
import org.xika.auto.code.db.Table;
import org.xika.auto.code.utils.ConfigUtils;
import org.xika.auto.code.utils.DBUtils;
import org.xika.auto.code.utils.FileUtils;
import org.xika.auto.code.utils.StringTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AutoGenerateCodeMain {

	private Configuration conf = null;
	public static final String generateTarget = "output";
	public static final String templeteCatalogue = "java";

	public static String Encoding = "UTF-8";

	public static Map<String, Object> dataMaps = new HashMap<String, Object>();;

	public static Map<String, String> maps = new HashMap<String, String>();

	private final static Logger log = Logger
			.getLogger(AutoGenerateCodeMain.class.getName());

	public AutoGenerateCodeMain() {
		
	}

	public static void main(String[] args) throws IOException,
			TemplateException {
		AutoGenerateCodeMain gc = new AutoGenerateCodeMain();
		long start = System.currentTimeMillis();
		gc.init();
		gc.getData();
		gc.gererate();
		System.out.println("/n自动生成共花费："+(System.currentTimeMillis() - start)+"[ms]");
	}

	private void getData() {
		
		
		String tableName = ConfigUtils.maps.get("db.tableName");
		String dbSchema  =ConfigUtils.maps.get("db.schema"); 
		
		Table table = new Table();
		table.setName(tableName);
		if(dbSchema==null||dbSchema.trim().length()==0){
			dbSchema = null;
		}else{
			table.setSpace(dbSchema);
		}
		
		List<Column> cols = DBUtils.getColumns(DBUtils.getConn(), table.getName());
		
		table.setColumns(cols);
		
		
		//dataMaps.put("entity", table.getName());
		dataMaps.put("entity",ConfigUtils.maps.get("java.entityName"));
		dataMaps.put("package", ConfigUtils.maps.get("generatePackage"));
		dataMaps.put("imports", table.getImports());
		dataMaps.put("javaFullFields", table.getFullFields());
		dataMaps.put("columns", table.getColumns());
		
		
		

		

	}

	public void gererate() {
		File f = new File(templeteCatalogue);
		List<File> files = new ArrayList<File>();
		try {
			FileUtils.readFiles(files, f);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读模板文件出错");
		}

		for (File tf : files) {
			String templateRelativePath = FileUtils.getRelativePath(new File(
					templeteCatalogue), tf);
			
			if (tf.isDirectory() || tf.isHidden())
				continue;
			if (templateRelativePath.trim().equals(""))
				continue;
			generateFile(templateRelativePath);
		}

	}
	
	private void generateFile(String templateRelativePath) {
		try {
			Template template = conf.getTemplate(templateRelativePath);

			String targetFilename = getTargetFilename(templateRelativePath);

			File absoluteOutputFilePath = getAbsoluteOutputFilePath(targetFilename);

			System.out.println("[generate]\t template:" + templateRelativePath
					+ " to " + targetFilename);

			saveNewOutputFileContent(template, absoluteOutputFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void saveNewOutputFileContent(Template template, File outputFile)
			throws IOException, TemplateException {
		FileWriter out = new FileWriter(outputFile);
		template.process(dataMaps, out);
		out.close();

	}

	private File getAbsoluteOutputFilePath(String targetFilename) {

		File outputFile = new File(generateTarget, targetFilename);
		outputFile.getParentFile().mkdirs();
		return outputFile;
	}

	private String getTargetFilename(String templateFilepath)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String targetFilename = resolveFile(templateFilepath, dataMaps);
		return targetFilename;
	}

	private String resolveFile(String templateFilepath, Map fileModel) {
		return StringTemplate.replace(templateFilepath, fileModel);
	}

	private void init() {
		conf = new Configuration();
		try {
			conf.setDirectoryForTemplateLoading(new File(templeteCatalogue));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 清理文件
		File generateCatalog = new File(generateTarget);
		FileUtils.delFiles(generateCatalog);

	}

}
