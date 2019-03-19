package com.bdxh.common.helper.excel;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelImportUtil {
	private final static String xls = "xls";
	private final static String XLS = "XLS";
	private final static String xlsx = "xlsx";
	private final static String XLSX = "XLSX";

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws Exception 
	 */
	public static List<String[]> readExcel(File file) throws Exception {
		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象 lim
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				if (file.getName().endsWith(xls) || file.getName().endsWith(XLS)) {
				// 获得当前sheet的开始行
				int firstRowNum = sheet.getFirstRowNum();
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// 获得当前行
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					int lastCellNum = row.getPhysicalNumberOfCells();
					String[] cells = new String[row.getPhysicalNumberOfCells()];
					// 循环当前行
					if (cells != null && cells.length > 0) {
						for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
							Cell cell = row.getCell(cellNum);
							if (cell == null) {
								continue;
							}
							cells[cellNum] = getCellValue(cell);
						}
						list.add(cells);
					}
				}
				}else{
					Sheet sheetxlsx = xlsxLoad(file).getSheetAt(sheetNum);
			        //遍历所有的行
			        for (Row row : sheetxlsx) {
						String[] cells = new String[row.getPhysicalNumberOfCells()];
						for(int i = 0;i < cells.length;i++){
							Cell cell = row.getCell(i);
							if (cell != null) {
								cells[i] = getCellValue(cell);
							}
						}
						list.add(cells);
			        }
				}
			}
		}
		return list;
	}

	/**
	 * 读入某页excel文件，解析后返回
	 * 
	 * @param file
	 *            ,
	 * @param sheetNum
	 *            页数
	 * @throws IOException
	 */
	public static List<String[]> readExcelNums(File file, int sheetNum) throws IOException {
		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();

		try {

			if (workbook != null) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					return null;
				}
				if (file.getName().endsWith(xls) || file.getName().endsWith(XLS)) {
					// 获得当前sheet的开始行
					int firstRowNum = sheet.getFirstRowNum();
					// 获得当前sheet的结束行
					int lastRowNum = sheet.getLastRowNum();
					// 循环除了第一行的所有行
					for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
						// 获得当前行
						Row row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						// 获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						// 获得当前行的列数
						int lastCellNum = row.getPhysicalNumberOfCells();
						String[] cells = new String[row.getPhysicalNumberOfCells()];
						// 循环当前行
						if (cells != null && cells.length > 0) {
							for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
								Cell cell = row.getCell(cellNum);
								if (cell != null) {
									cells[cellNum] = getCellValue(cell);
								}
							}
							list.add(cells);
						}
					}
				}else{
					Sheet sheetxlsx = xlsxLoad(file).getSheetAt(sheetNum);
			        //遍历所有的行
			        for (Row row : sheetxlsx) {
						String[] cells = new String[row.getPhysicalNumberOfCells()];
						for(int i = 0;i < cells.length;i++){
							Cell cell = row.getCell(i);
							if (cell != null) {
								cells[i] = getCellValue(cell);
							}
						}
						list.add(cells);
			        }
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(workbook != null){
				workbook.close();
			}
		}
		return list;
	}

	public static void checkFile(File file) throws IOException {
		// 判断文件是否存在
		if (null == file) {
			throw new FileNotFoundException("文件不存在！");
		}
		// 获得文件名
		String fileName = file.getName();
		// 判断文件是否是excel文件
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx) && !fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
			throw new IOException(fileName + "不是excel文件");
		}
	}

	public static Workbook getWorkBook(File file) {
		// 获得文件名
		String fileName = file.getName();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			// InputStream is = file.getInputStream();
			InputStream is = new FileInputStream(file);

			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls) || fileName.endsWith(XLS)) {
				// 2003
				workbook = WorkbookFactory.create(is);
				// workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx) || fileName.endsWith(XLSX)) {
				// 2007
				//workbook = new XSSFWorkbook(is);
				try {
					workbook = xlsxLoad(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(workbook != null){
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
/*		  if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
		  cell.setCellType(Cell.CELL_TYPE_STRING); }*/
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
			  short format = cell.getCellStyle().getDataFormat(); 
			  SimpleDateFormat sdf = null;
			  sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			  Double value = cell.getNumericCellValue();
			  Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value); 
			  if(date != null){
				  cellValue = sdf.format(date); 
			  }else{
				  cellValue = ""; 
			  }
			} else {
				DecimalFormat df = new DecimalFormat("0");  
				String val = df.format(cell.getNumericCellValue()); 
				cellValue = val;
			}
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
            try {  
                cellValue = cell.getStringCellValue(); 
            } catch (IllegalStateException e) {  
                cellValue = String.valueOf(cell.getNumericCellValue());  
            }  
            cellValue = cellValue != null ? cellValue.replace("\"","").replace("\"",""):null;
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
	
	/**
	 * 获取某个目录下所有目录
	 */
	public static List<String> getFileDir(String path) throws Exception {
		try {
			List<String> arrayPath = new ArrayList<String>();
			File[] file = new File(path).listFiles();				//获取file下面的所有文件夹绝对目录
			for(int i = 0 ; i < file.length ; i++) {
				String childFile = file[i].toString();				//获取子目录
				arrayPath.add(childFile);				//获取目录下的所有文件地址
			}
			return arrayPath;
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 获取某个目录下所有文件路径
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFilePath(String path) throws Exception {
		try {
			List<String> arrayPath = new ArrayList<String>();
			File[] file = new File(path).listFiles();				//获取file下面的所有文件夹绝对目录
			for(int i = 0 ; i < file.length ; i++) {
				String childFile = file[i].toString();				//获取子目录
				File[] files = new File(childFile).listFiles();
				for(int j = 0 ; j < files.length ; j++) {
					arrayPath.add(files[j].toString());				//获取目录下的所有文件地址
				}
			}
			return arrayPath;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(MultipartFile file) throws IOException{
          //检查文件  
         checkFile(file);  
         //获得Workbook工作薄对象  
          Workbook workbook = getWorkBook(file);  
         //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
         List<String[]> list = new ArrayList<String[]>();  
          if(workbook != null){  
              for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                  //获得当前sheet工作表  
                  Sheet sheet = workbook.getSheetAt(sheetNum);  
                  if(sheet == null){  
                      continue;  
                  }  
                  //获得当前sheet的开始行  
                  int firstRowNum  = sheet.getFirstRowNum();  
                  //获得当前sheet的结束行  
                  int lastRowNum = sheet.getLastRowNum();
                 //循环除了第一行的所有行  
 				for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                      //获得当前行  
                      Row row = sheet.getRow(rowNum);  
                      if(row == null){  
                          continue;  
                      }  
                      //获得当前行的开始列  
                     int firstCellNum = row.getFirstCellNum();  
                      //获得当前行的列数  
                      int lastCellNum = row.getPhysicalNumberOfCells();  
                      String[] cells = new String[row.getPhysicalNumberOfCells()];  
                      //循环当前行  
                      if(cells !=null && cells.length>0){
                      for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                          Cell cell = row.getCell(cellNum);  
                          if(cell == null && ("") != cell.toString()){
                        	  continue;
                          }
                          cells[cellNum] = getCellValue(cell);  
                      } 
                      	list.add(cells);  
                      }
                  }  
              }  
          }  
          return list;  
      }
	/**
	 * 读入某页excel文件，解析后返回
	 * 
	 * @param file,
	 * @param sheetNum 页数
	 * @throws IOException
	 */
	public static List<String[]> readExcelNums(MultipartFile file, int sheetNum) throws IOException{
          //检查文件  
         checkFile(file);  
         //获得Workbook工作薄对象  
          Workbook workbook = getWorkBook(file);  
         //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
         List<String[]> list = new ArrayList<String[]>();  
         
         String error = null;
        try {



         if(workbook != null){  
                  //获得当前sheet工作表  
                  Sheet sheet = workbook.getSheetAt(sheetNum);  
                  if(sheet == null){  
                      return null;  
                  }  
                  //获得当前sheet的开始行  
                  int firstRowNum  = sheet.getFirstRowNum();  
                  //获得当前sheet的结束行  
                  int lastRowNum = sheet.getLastRowNum();
                 //循环除了第一行的所有行  
 				for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                      //获得当前行  
                      Row row = sheet.getRow(rowNum);  
                      if(row == null){  
                          continue;  
                      }  
                      //获得当前行的开始列  
                     int firstCellNum = row.getFirstCellNum();  
                      //获得当前行的列数  
                      int lastCellNum = row.getPhysicalNumberOfCells();  
                      String[] cells = new String[row.getPhysicalNumberOfCells()];  
                      //循环当前行  
                      if(cells !=null && cells.length>0){
	                      for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
	                          Cell cell = row.getCell(cellNum);  
	                          if(cell!=null && ("") != cell.toString()){
	                        	  cells[cellNum] = getCellValue(cell);  
	                          }
	                      }  
	                      list.add(cells);
                      }    
                  }
          }  
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(error);
		}
          return list;  
      }
	public static void checkFile(MultipartFile file) throws IOException {
		// 判断文件是否存在
		if (null == file) {
			throw new FileNotFoundException("文件不存在！");
		}
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 判断文件是否是excel文件
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			throw new IOException(fileName + "不是excel文件");
		}
	}

	public static Workbook getWorkBook(MultipartFile file) {
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			InputStream is = file.getInputStream();
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = WorkbookFactory.create(is);
				//workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
			
		}catch(InvalidFormatException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}
	 public static Workbook xlsxLoad(File path) throws Exception{
	        FileInputStream in = new FileInputStream(path);
	        Workbook wk = StreamingReader.builder()
	                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
	                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
	                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
	        Sheet sheet = wk.getSheetAt(0);
	        return wk;
    }

	public static void main(String[] args) {
		try {

			List<String[]> list = ExcelImportUtil.readExcelNums(new File("D:\\新建文件夹\\成绩导入测试数据.xls"), 0);
			for(String[] strs : list){
				if(strs.length == 0){
					continue;
				}
				if(strs[0] == null || strs[0] == ""){
					continue;
				}
				System.out.println(Arrays.toString(strs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
