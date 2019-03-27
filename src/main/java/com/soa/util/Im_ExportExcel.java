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
    * @Description: TODO(excel�ĵ��뵼������)
    * @author zhugang
    * @date 2017��10��29��
    *
    */

public class Im_ExportExcel {
	public static HSSFWorkbook exportExcel(String[] excelHeader,
			String[][] excelContent, String currentExcelTitle ) throws IOException {
		System.out.println( ">>>>>>>>>>>>>>>>>>ִ�з���������excel����---------" );
		 // ����HSSFWorkbook����(excel���ĵ�����)
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFRow row = null;
		HSSFCell cell = null;
		
		//����һ��excel��sheet
		String excelSheetName = currentExcelTitle;
		HSSFSheet sheet = wb.createSheet( excelSheetName );  
		sheet.setVerticallyCenter( true );
//		sheet.setDefaultColumnWidth( 50 ); // ����ȱʡ�п�
//	    sheet.setDefaultRowHeightInPoints( 30 );// ����ȱʡ�и�
		
	    /*
         * ��ӱ���-
         */
	    //���ñ�����ʽ
        HSSFCellStyle titleStyle = wb.createCellStyle();  //����һ����ʽ���������ñ�����ʽ  
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
        ztFont.setItalic( false );                     // ��������Ϊб����   
        ztFont.setColor( Font.COLOR_NORMAL);            // ����������Ϊ����ɫ��   
        ztFont.setFontHeightInPoints( (short) 38 );    // �������С����Ϊ18px   
        ztFont.setFontName( "����" );         
        ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    //�Ӵ�
//      ztFont.setUnderline(Font.U_DOUBLE);         // ��ӣ�Font.U_SINGLE�����»���/Font.U_DOUBLE˫���»��ߣ�   
//      ztFont.setStrikeout(true);                  // �Ƿ����ɾ����   
        titleStyle.setFont(ztFont); 
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        //���ñ߿�THICK:�ֱ���    THIN:ϸ����     MEDIUM:�еȱ���    HAIR:СԲ�����߱���     DOUBLE:˫����   DASHED:���߱���  
//        style.setBorderBottom(BorderStyle.THICK);  //�±߿�Ϊ�ֱ���
//        style.setBorderLeft(BorderStyle.THICK);  
//        style.setBorderRight(BorderStyle.THICK);  
//        style.setBorderTop(BorderStyle.THICK);  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        //����һ������  
//        font.setColor(HSSFColor.VIOLET.index);  
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  

		// ��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
		row = sheet.createRow( 0 );
		row.setHeightInPoints( (short) 40 );
		// ������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
		cell = row.createCell( 0 );
		// �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
		sheet.addMergedRegion( new CellRangeAddress(0, 0, 0, excelHeader.length - 1 ) );
		// ���õ�Ԫ������
		cell.setCellValue( currentExcelTitle );
		cell.setCellStyle( titleStyle );
		
        
		/*
         * ��Ӹ�����
         */
	    //���ñ�����ʽ
        HSSFCellStyle littleTitleStyle = wb.createCellStyle();  
        littleTitleStyle.setAlignment( HSSFCellStyle.ALIGN_RIGHT);
        littleTitleStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        Font ltFont = wb.createFont();   
        ltFont.setItalic( false );                     // ��������Ϊб����   
        ltFont.setColor( Font.COLOR_NORMAL);            // ����������Ϊ����ɫ��   
        ltFont.setFontHeightInPoints( (short) 8 );    // �������С����Ϊ18px   
        ltFont.setFontName( "����" );         
        ltFont.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );    //�Ӵ�
        littleTitleStyle.setFont(ltFont); 
        HSSFRow littleTitleRow = sheet.createRow( 1 );  
        littleTitleRow.setHeightInPoints( (short) 12 );
		cell = littleTitleRow.createCell( 0 );
		sheet.addMergedRegion( new CellRangeAddress(1, 1, 0, excelHeader.length - 1 ) );
		// ���õ�Ԫ������
		cell.setCellValue( "SL/QHSE.YJ01" );
		cell.setCellStyle( littleTitleStyle );		
		
		/*
		 * ��ӱ�ͷ
		 */
		//���ñ�ͷ��ʽ
		HSSFCellStyle headStyle = wb.createCellStyle();        //�����ʽ
		headStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		headStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		headStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN );
		headStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN  );
		headStyle.setBorderRight( HSSFCellStyle.BORDER_THIN  );
		headStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
		Font ztFont2 = wb.createFont();   
		ztFont.setFontHeightInPoints( (short) 24 ); 
		ztFont2.setItalic( false );                     // ��������Ϊб����   
		ztFont2.setColor( Font.COLOR_NORMAL );            // ����������Ϊ����ɫ��  
		ztFont2.setFontName( "����" );             // ����Ӧ�õ���ǰ��Ԫ����   
		//ztFont2.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );    //�Ӵ�
		headStyle.setFont( ztFont2 );
		
		//��ӱ�ͷ����
        HSSFRow headRow = sheet.createRow( 2 );  
        headRow.setHeightInPoints( (short) 30 );
        for (int i = 0; i < excelHeader.length; i++) {
        	 HSSFCell cellTemp = headRow.createCell( i );  
        	 cellTemp.setCellStyle( headStyle );  
             HSSFRichTextString text = new HSSFRichTextString( excelHeader[i] );  
             cellTemp.setCellValue(text);  
		}
        
		/*
		 * ��ӱ��content
		 */
        //���ñ�ͷ��ʽ
        HSSFCellStyle contentStyle = wb.createCellStyle();        //�����ʽ
        contentStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        contentStyle.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        contentStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN  );
        contentStyle.setBorderRight( HSSFCellStyle.BORDER_THIN  );
        contentStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
  		Font contentFont = wb.createFont();   
  		contentFont.setFontHeightInPoints( (short) 10 ); 
  		contentFont.setItalic( false );                     // ��������Ϊб����   
  		contentFont.setColor( Font.COLOR_NORMAL );            // ����������Ϊ����ɫ��  
  		contentFont.setFontName( "����" );             // ����Ӧ�õ���ǰ��Ԫ����   
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
        
        //�Զ������п�
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
	    * @Description: ����excel���߷���
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	 */
	public static List<EquipmentInfo> importExcel (InputStream input, String EQU_MEMO_ONE) throws IOException, InvalidFormatException {
		System.out.println(">>>>>>>>>>>>>>>>>>ִ�з�����Im_ExportExcel.importExcel");
		 List<EquipmentInfo>  temp = new ArrayList<EquipmentInfo> ();
		 //����ָ�����ļ�����������Excel�Ӷ�����Workbook����
		 Workbook wb0 = WorkbookFactory.create(input);;
		 //��ȡExcel�ĵ��еĵ�һ����
		 Sheet sht0 = wb0.getSheetAt(0);
		 //��Sheet�е�ÿһ�н��е���
		 for ( Row r : sht0 ) {
		     //�����ǰ�е��кţ���0��ʼ��δ�ﵽ2�������У������ѭ��
			 if( r.getRowNum() < 3 ){
				 continue;
			 }
			 if( EQU_MEMO_ONE ==null ) {
				 break;
			 }
			 if( "ѹ����".equals( EQU_MEMO_ONE )) {
				//����ʵ����
				 EquipmentInfo info=new EquipmentInfo();
				 
				 //ȡ����ǰ�е�1����Ԫ�����ݣ�����װ��infoʵ��stuName������
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
