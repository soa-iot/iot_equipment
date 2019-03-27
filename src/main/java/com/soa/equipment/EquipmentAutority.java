/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;
/*    */ import java.net.URLDecoder;

/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;

/*    */ 
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class EquipmentAutority extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/* 44 */     request.setCharacterEncoding("utf-8");
/* 45 */     response.setContentType("text/html;charset=utf-8");
/* 46 */     JSONObject json = new JSONObject();
/*    */ 
/* 48 */     String userName = URLDecoder.decode(request.getParameter("userName"), "utf-8");
/* 49 */     if (userName !=null) {
/*    */       try {
/* 51 */         json.put("state", 1);
/* 52 */         json.put("messge", "用户名为空");
/* 53 */         json.put("data", 0);
/*    */       } catch (JSONException e) {
/* 55 */         e.printStackTrace();
/*    */       }
/* 57 */       response.getWriter().write(json.toString());
/*    */     }
/*    */ 
/* 60 */     userName = userName.trim();
/* 61 */     String sql = " select auth_name      from equipment_user_authority \t   where user_authority  like '%" + 
/* 63 */       userName + "%'";
/* 64 */     System.out.println("查询用户权限sql:" + sql);
/* 65 */     JSONArray jsonArr = DataUtil.getData(sql);
/* 66 */     String authoritys = "";
/* 67 */     if (jsonArr.length() > 0)
/*    */       try {
/* 69 */         for (int i = 0; i < jsonArr.length(); i++) {
/* 70 */           if ("".equals(authoritys)) {
/* 71 */             authoritys = jsonArr.getJSONObject(i).getString("AUTH_NAME");
/*    */           }
/*    */           else {
/* 74 */             authoritys = authoritys + "," + 
/* 75 */               jsonArr.getJSONObject(i).getString("AUTH_NAME");
/*    */           }
/*    */         }
/* 78 */         json.put("state", 0);
/* 79 */         json.put("messge", "获取权限成功");
/* 80 */         json.put("data", authoritys);
/*    */       } catch (JSONException e) {
/* 82 */         e.printStackTrace();
/*    */       }
/*    */     else {
/*    */       try {
/* 86 */         json.put("state", 1);
/* 87 */         json.put("messge", "获取权限失败");
/* 88 */         json.put("data", "");
/*    */       } catch (JSONException e) {
/* 90 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 93 */     response.getWriter().write(json.toString());
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.EquipmentAutority
 * JD-Core Version:    0.6.2
 */