/*    */ package com.soa.equipment;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class DBUtil
/*    */ {
/*    */   private static String driver;
/*    */   private static String url;
/*    */   private static String user;
/*    */   private static String password;
/* 12 */   private static ThreadLocal<Connection> tl = new ThreadLocal();
/*    */ 
/*    */   static
/*    */   {
/* 16 */     Properties pro = new Properties();
/*    */     try {
/* 18 */       Class.forName("oracle.jdbc.driver.OracleDriver");
/*    */     } catch (Exception e) {
/* 20 */       e.printStackTrace();
/* 21 */       throw new RuntimeException("读取数据库配置文件失败！");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Connection getConn() throws SQLException {
/* 26 */     Connection conn = null;
/* 27 */     if (conn == null) {
/*    */       try {
/* 29 */         conn = DriverManager.getConnection("jdbc:oracle:thin:@10.89.90.118:1521:orcl", "sts", "sts123");
/* 30 */         tl.set(conn);
/*    */       }
/*    */       catch (SQLException e) {
/* 33 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 36 */     return (Connection)tl.get();
/*    */   }
/*    */ 
/*    */   public static void closeConn() {
/* 40 */     if (tl.get() != null) {
/*    */       try {
/* 42 */         ((Connection)tl.get()).close();
/*    */       } catch (SQLException e) {
/* 44 */         e.printStackTrace();
/*    */       }
/* 46 */       tl.set(null);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.DBUtil
 * JD-Core Version:    0.6.2
 */