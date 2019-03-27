/*    */ package com.soa.equipment;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class GetEquipmentAttachmentServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws UnsupportedEncodingException
/*    */   {
/* 21 */     request.setCharacterEncoding("utf-8");
/* 22 */     response.setContentType("text/html;charset=utf-8");
/* 23 */     String EQU_POSITION_NUM = request.getParameter("EQU_POSITION_NUM");
/* 24 */     String EQU_MEMO_ONE = request.getParameter("EQU_MEMO_ONE");
/* 25 */     JSONArray json = new JSONArray();
/*    */ 
/* 27 */     System.out.println("获取附件的分类为：" + EQU_MEMO_ONE);
/*    */ 
/* 29 */     String path = "f:" + File.separator + "ftp" + File.separator + "czqk2" + File.separator + EQU_MEMO_ONE;
/*    */ 
/* 31 */     File f = new File(path);
/* 32 */     File[] fs = f.listFiles();
/*    */ 
/* 34 */     for (File file : fs) {
/* 35 */       JSONObject jo = new JSONObject();
/*    */       try {
/* 37 */         jo.put("file", file.getName());
/* 38 */         json.put(jo);
/*    */       }
/*    */       catch (JSONException e) {
/* 41 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 47 */       response.getWriter().write(json.toString());
/*    */     }
/*    */     catch (IOException e) {
/* 50 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.GetEquipmentAttachmentServlet
 * JD-Core Version:    0.6.2
 */