/*    */ package com.soa.equipment;
/*    */ 
/*    */ import com.alibaba.fastjson.JSONObject;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class DeleteEquipmentAttachmentServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 23 */     String FileName = req.getParameter("FileName");
/* 24 */     String EQU_MEMO_ONE = req.getParameter("EQU_MEMO_ONE");
/* 25 */     String path = "f:" + File.separator + "ftp" + File.separator + "czqk2" + File.separator + EQU_MEMO_ONE + File.separator + FileName;
/* 26 */     File file = new File(path);
/* 27 */     int i = 1;
/* 28 */     if (file.exists()) {
/* 29 */       file.delete();
/* 30 */       i--;
/*    */     }
/*    */ 
/* 33 */     Map map = new HashMap();
/* 34 */     if (i == 0)
/* 35 */       map.put("msg", Integer.valueOf(0));
/*    */     else {
/* 37 */       map.put("msg", Integer.valueOf(1));
/*    */     }
/* 39 */     String result = new JSONObject(map).toString();
/* 40 */     resp.getWriter().write(result);
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.DeleteEquipmentAttachmentServlet
 * JD-Core Version:    0.6.2
 */