/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;

/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;

/*    */ 
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class GetEquipmentInfoByKeyServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/* 24 */     request.setCharacterEncoding("utf-8");
/* 25 */     response.setContentType("text/html;charset=utf-8");
/* 26 */     String equID = request.getParameter("EQU_ID");
/* 27 */     JSONObject json = new JSONObject();
/*    */     try {
/* 29 */       json = getEquiInfo(equID);
/* 30 */       response.getWriter().write(json.toString());
/*    */     }
/*    */     catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   private JSONObject getEquiInfo(String EQU_ID) throws JSONException {
/* 38 */     String sql = "SELECT * FROM cz_equipment_info WHERE EQU_ID = '" + EQU_ID + "'";
/* 39 */     JSONArray data = DataUtil.getData(sql);
/* 40 */     JSONObject json = new JSONObject();
/* 41 */     if (data.length() > 0) {
/* 42 */       json = data.getJSONObject(0);
/*    */     }
/* 44 */     return json;
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.GetEquipmentInfoByKeyServlet
 * JD-Core Version:    0.6.2
 */