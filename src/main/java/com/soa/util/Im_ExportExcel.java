package com.soa.util;

    import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;




/**
    * @ClassName: Im_ExportExcel
    * @Description: TODO(excel的导入导出功能)
    * @author zhugang
    * @date 2017年10月29日
    *
    */

public class Im_ExportExcel {
	public static HSSFWorkbook exportExcel(String[] excelHeader,
			String[][] excelContent, String currentExcelTitle ) throws IOException {
		System.out.println( ">>>>>>>>>>>>>>>>>>执行方法：导出excel设置---------" );
		 // 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFRow row = null;
		HSSFCell cell = null;
		
		//生成一个excel的sheet
		String excelSheetName = currentExcelTitle;
		HSSFSheet sheet = wb.createSheet( excelSheetName );  
		sheet.setVerticallyCenter( true );
//		sheet.setDefaultColumnWidth( 50 ); // 设置缺省列宽
//	    sheet.setDefaultRowHeightInPoints( 30 );// 设置缺省列高
		
	    /*
         * 添加标题-
         */
	    //设置标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();  //生成一个样式，用来设置标题样式  
        //titleStyle.setFillForegroundColor( HSSFColor.GREY_40_PERCENT.index );
        //titleStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
//        titleStyle.setBorderBottom( HSSFCellStyle.BORDER_THICK );
//        titleStyle.setBorderLeft( HSSFCellStyle.BORDER_THICK  );
//        titleStyle.setBorderRight( HSSFCellStyle.BORDER_THICK  );
//        titleStyle.setBorderTop( HSSFCellStyle.BORDER_THICK );
        titleStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        //titleStyle.setFillBackgroundColor( (short) 200 );
        titleStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
 
        Font ztFont = wb.createFont();   
        ztFont.setItalic( false );                     // 设置字体为斜体字   
        ztFont.setColor( Font.COLOR_NORMAL);            // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints( (short) 38 );    // 将字体大小设置为18px   
        ztFont.setFontName( "宋体" );         
        ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    //加粗
//      ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）   
//      ztFont.setStrikeout(true);                  // 是否添加删除线   
        titleStyle.setFont(ztFont); 
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        //设置边框THICK:粗边线    THIN:细边线     MEDIUM:中等边线    HAIR:小圆点虚线边线     DOUBLE:双边线   DASHED:虚线边线  
//        style.setBorderBottom(BorderStyle.THICK);  //下边框为粗边线
//        style.setBorderLeft(BorderStyle.THICK);  
//        style.setBorderRight(BorderStyle.THICK);  
//        style.setBorderTop(BorderStyle.THICK);  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        //生成一个字体  
//        font.setColor(HSSFColor.VIOLET.index);  
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  

		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		row = sheet.createRow( 0 );
		row.setHeightInPoints( (short) 40 );
		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		cell = row.createCell( 0 );
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion( new CellRangeAddress(0, 0, 0, excelHeader.length - 1 ) );
		// 设置单元格内容
		cell.setCellValue( currentExcelTitle );
		cell.setCellStyle( titleStyle );
		
        
		/*
         * 添加副标题
         */
	    //设置标题样式
        HSSFCellStyle littleTitleStyle = wb.createCellStyle();  
        littleTitleStyle.setAlignment( HSSFCellStyle.ALIGN_RIGHT);
        littleTitleStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        Font ltFont = wb.createFont();   
        ltFont.setItalic( false );                     // 设置字体为斜体字   
        ltFont.setColor( Font.COLOR_NORMAL);            // 将字体设置为“红色”   
        ltFont.setFontHeightInPoints( (short) 8 );    // 将字体大小设置为18px   
        ltFont.setFontName( "宋体" );         
        ltFont.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );    //加粗
        littleTitleStyle.setFont(ltFont); 
        HSSFRow littleTitleRow = sheet.createRow( 1 );  
        littleTitleRow.setHeightInPoints( (short) 12 );
		cell = littleTitleRow.createCell( 0 );
		sheet.addMergedRegion( new CellRangeAddress(1, 1, 0, excelHeader.length - 1 ) );
		// 设置单元格内容
		cell.setCellValue( "SL/QHSE.YJ01" );
		cell.setCellStyle( littleTitleStyle );		
		
		/*
		 * 添加表头
		 */
		//设置表头样式
		HSSFCellStyle headStyle = wb.createCellStyle();        //表格样式
		headStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		headStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		headStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN );
		headStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN  );
		headStyle.setBorderRight( HSSFCellStyle.BORDER_THIN  );
		headStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
		Font ztFont2 = wb.createFont();   
		ztFont.setFontHeightInPoints( (short) 24 ); 
		ztFont2.setItalic( false );                     // 设置字体为斜体字   
		ztFont2.setColor( Font.COLOR_NORMAL );            // 将字体设置为“红色”  
		ztFont2.setFontName( "宋体" );             // 字体应用到当前单元格上   
		//ztFont2.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );    //加粗
		headStyle.setFont( ztFont2 );
		
		//添加表头数据
        HSSFRow headRow = sheet.createRow( 2 );  
        headRow.setHeightInPoints( (short) 30 );
        for (int i = 0; i < excelHeader.length; i++) {
        	 HSSFCell cellTemp = headRow.createCell( i );  
        	 cellTemp.setCellStyle( headStyle );  
             HSSFRichTextString text = new HSSFRichTextString( excelHeader[i] );  
             cellTemp.setCellValue(text);  
		}
        
		/*
		 * 添加表格content
		 */
        //设置表头样式
        HSSFCellStyle contentStyle = wb.createCellStyle();        //表格样式
        contentStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        contentStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        contentStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN  );
        contentStyle.setBorderRight( HSSFCellStyle.BORDER_THIN  );
        contentStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
  		Font contentFont = wb.createFont();   
  		contentFont.setFontHeightInPoints( (short) 10 ); 
  		contentFont.setItalic( false );                     // 设置字体为斜体字   
  		contentFont.setColor( Font.COLOR_NORMAL );            // 将字体设置为“红色”  
  		contentFont.setFontName( "宋体" );             // 字体应用到当前单元格上   
  		contentStyle.setFont( contentFont );
        for (int i = 3; i < excelContent.length + 3; i++ ) {
			String[] strings = excelContent[i-3];
			HSSFRow contentRow = sheet.createRow( i ); 
			contentRow.setHeightInPoints( (short) 20 );
			
			for ( int j = 0; j < strings.length; j++ ) {
				String str = strings[j];
				HSSFCell cellTemp = contentRow.createCell( j );  
				cellTemp.setCellStyle( contentStyle );  
	            HSSFRichTextString text = new HSSFRichTextString( str );  
	            cellTemp.setCellValue( text );  
			}
		}   
        
        //自动调整列宽
        for( int i=0; i<excelHeader.length; i++ ) {
        	sheet.autoSizeColumn( (short) i);
        }
              
        return wb;
	}
	
	/**
	 * @throws InvalidFormatException 
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 * @throws IOException 
	 * 
	    * @Title: importExcel
	    * @Description: 导入excel工具方法
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static List<EquipmentInfo> importExcel (InputStream input, String EQU_MEMO_ONE) throws IOException, InvalidFormatException {
		System.out.println(">>>>>>>>>>>>>>>>>>执行方法：Im_ExportExcel.importExcel");
		 List<EquipmentInfo>  temp = new ArrayList<EquipmentInfo> ();
		 //根据指定的文件输入流导入Excel从而产生Workbook对象
		 Workbook wb0 = WorkbookFactory.create(input);;
		 //获取Excel文档中的第一个表单
		 Sheet sht0 = wb0.getSheetAt(0);
		 //对Sheet中的每一行进行迭代
		 for ( Row r : sht0 ) {
		     //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
			 if( r.getRowNum() < 3 ){
				 continue;
			 }
			 if( EQU_MEMO_ONE ==null ) {
				 break;
			 }
			 if( "压力表".equals( EQU_MEMO_ONE )) {
				//创建实体类
				 EquipmentInfo info=new EquipmentInfo();
				 
				 //取出当前行第1个单元格数据，并封装在info实体stuName属性上
				 r.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
				 r.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
				 info.setEQU_ID( UUID.randomUUID().toString() );
				 info.setEQU_POSITION_NUM(r.getCell(1).getStringCellValue());
				 info.setEQU_MEMO_ONE(r.getCell(2).getStringCellValue());
				 info.setEQU_NAME(r.getCell(3).getStringCellValue());
				 info.setMANAGE_TYPE(r.getCell(4).getStringCellValue());
				 info.setEQU_MODEL(r.getCell(5).getStringCellValue());
				 info.setMEARING_RANGE(r.getCell(6).getStringCellValue());
				 info.setMEASURE_ACC(r.getCell(7).getStringCellValue());
				 info.setEQU_INSTALL_POSITION(r.getCell(8).getStringCellValue());
				 info.setEQU_MANUFACTURER(r.getCell(9).getStringCellValue());
				 info.setSERIAL_NUM(r.getCell(10).getStringCellValue());
				 info.setCHECK_CYCLE(r.getCell(11).getStringCellValue());
				 info.setCHECK_TIME(r.getCell(12).getStringCellValue());
				 info.setNEXT_CHECK_TIME(r.getCell(13).getStringCellValue());
				 info.setREMARK1(r.getCell(14).getStringCellValue());
				 info.setEQUIPMENT_ATTACH_URL(r.getCell(15).getStringCellValue());
				 
			 }else {
				 
			 }
		 }     
		 return temp;        
	}
}
