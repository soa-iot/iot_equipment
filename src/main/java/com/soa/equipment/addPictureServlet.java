package com.soa.equipment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.soa.util.DataUtil;

public class addPictureServlet extends HttpServlet{
	
		private static final long serialVersionUID = 1L;

		/**
		 * 查看设备图片
		 * @throws UnsupportedEncodingException 
		 */
		protected void service(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String EQU_MEMO_ONE=request.getParameter("EQU_MEMO_ONE");
			String EQU_POSITION_NUM=request.getParameter("EQU_POSITION_NUM");
			String fName = "";
			String suffix = "";
			String sql="";
			File f;
			Map map=new HashMap();
			System.out.println("EQU_MEMO_ONE:"+EQU_MEMO_ONE);
			System.out.println("EQU_POSITION_NUM:"+EQU_POSITION_NUM);
			// 获得磁盘文件条目工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取文件�?要上传到的路�?
			// String path = request.getRealPath("/upload1");//该方法已经被淘汰
		
			// String path = "c:/upload1";
			// 如果没以下两行设置的话，上传大的 文件 会占�? 很多内存�?
			// 设置暂时存放�? 存储�? , 这个存储室，可以�? �?终存储文�? 的目录不�?
			/**
			* 原理 它是先存�? 暂时存储室，然后在真正写�? 对应目录的硬盘上�? 按理来说 当上传一个文件时，其实是上传了两份，第一个是�? .tem
			* 格式�? 然后再将其真正写�? 对应目录的硬盘上
			*/

			// 高水平的API文件上传处理
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
			if (!item.isFormField()) {

			/**
			* 以下三步，主要获�? 上传文件的名�?
			*/
			// 获取路径�?
			String value = item.getName();
		    System.out.println("value:"+value);
			// 索引到最后一个反斜杠
			int start = value.lastIndexOf("\\");
			// 截取 上传文件�? 字符串名字，�?1�? 去掉反斜杠，
			String filename = value.substring(start + 1);

			// 真正写到磁盘�?
			// 它抛出的异常 用exception 捕捉
			// item.write( new File(path,filename) );//第三方提供的
			// 手动写入�?
			// 如果有文件名
			if (filename.indexOf(".") >= 0) {
			// 就截�?.之前的字符串
			int indexdot = filename.indexOf(".");

			suffix = filename.substring(indexdot);

			fName = filename
			.substring(0, filename.lastIndexOf("."));
			Date now = new Date();
			fName = fName + "_" + now.getTime();
			fName = fName + suffix;

			}
			
			//获取文件类型
			String fileType=value.substring(value.lastIndexOf(".")+1);
			
			String path="f:"+File.separator+"ftp"+File.separator+"czqk2";
			System.out.println("上传文件类型�?"+fileType.toLowerCase());
			System.out.println("上传文件名称�?"+fName);
			//如果是图�? 
			if(fileType.toLowerCase().equals("png")||fileType.equals("jpg")||fileType.equals("jpeg")) {
				 path+=File.separator+fName;
				String  EQU_FIL_ID=UUID.randomUUID().toString();
				sql="insert into CZ_EQUIPMENT_FILES (EQU_FIL_ID,FIL_STORE_NAME,FIL_DISPIAY_NAME,FIL_ADDRESS,EQU_POSITION_NUM)"+
			"values('"+EQU_FIL_ID+"','"+fName+"','"+fName+"','"+path+"','"+EQU_POSITION_NUM+"')";
				f=new File(path);
				System.out.println("文件插入sql"+sql);
				int i=DataUtil.doExecuteSql(sql);
				
			}else  {
				path+=File.separator+EQU_MEMO_ONE+File.separator+value;
				f=new File(path);
				if(!f.getParentFile().exists()) {
					f.mkdirs();
				}
				
			}
			factory.setRepository(f);
			// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放�? 暂时存储�?
			factory.setSizeThreshold(1024 * 1024);
			OutputStream out = new FileOutputStream(new File(path));
			InputStream in = item.getInputStream();
			int length = 0;
			byte[] buf = new byte[1024];
			System.out.println("获取上传文件的�?�共的容量：" + item.getSize());
			// in.read(buf) 每次读到的数据存放在 buf 数组�?
			while ((length = in.read(buf)) != -1) {
			// �? buf 数组�? 取出数据 写到 （输出流）磁盘上
			out.write(buf, 0, length);
			}
			out.flush();
			in.close();
			out.close();
			}
			}
			} catch (FileUploadException e) {
			e.printStackTrace();
			} catch (Exception e) {
			e.printStackTrace();
			}
		
			}
		
		
}
