/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;

/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;

/*    */ 
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class EquipmentImageServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/* 21 */     String EQU_POSITION_NUM = "";
/* 22 */     EQU_POSITION_NUM = request.getParameter("EQU_POSITION_NUM");
/* 23 */     request.setCharacterEncoding("utf-8");
/* 24 */     response.setContentType("text/html;charset=utf-8");
/*    */     try {
/* 26 */       JSONArray returndata = getEquiImage(EQU_POSITION_NUM);
/* 27 */       response.getWriter().write(returndata.toString());
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   private JSONArray getEquiImage(String EQU_POSITION_NUM) throws JSONException {
/* 35 */     JSONArray data = new JSONArray();
/* 36 */     String sql = "SELECT e.EQU_NAME, i.FIL_STORE_NAME,i.FIL_DISPIAY_NAME, i.equ_fil_id FROM CZ_EQUIPMENT_info e, CZ_EQUIPMENT_FILES i WHERE  e.EQU_POSITION_NUM = i.EQU_POSITION_NUM AND e.EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "'";
/* 37 */     System.out.println("查询图片的SQL" + sql);
/* 38 */     data = DataUtil.getData(sql);
/* 39 */     return data;
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.EquipmentImageServlet
 * JD-Core Version:    0.6.2
 */