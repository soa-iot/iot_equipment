/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;

/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ 
/*    */ import com.alibaba.fastjson.JSONObject;
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class deletePicServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 21 */     String equ_fil_id = request.getParameter("equ_fil_id");
/*    */ 
/* 23 */     String sql = "delete from CZ_EQUIPMENT_FILES where  equ_fil_id='" + equ_fil_id + "'";
/* 24 */     System.out.println("查询图片的SQL" + sql);
/* 25 */     int count = DataUtil.doExecuteSql(sql);
/* 26 */     Map map = new HashMap();
/* 27 */     if (count > 0)
/* 28 */       map.put("code", Integer.valueOf(0));
/*    */     else
/* 30 */       map.put("code", Integer.valueOf(1));
/*    */     try
/*    */     {
/* 33 */       String result = new JSONObject(map).toString();
/* 34 */       response.getWriter().write(result);
/*    */     }
/*    */     catch (IOException e) {
/* 37 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.deletePicServlet
 * JD-Core Version:    0.6.2
 */