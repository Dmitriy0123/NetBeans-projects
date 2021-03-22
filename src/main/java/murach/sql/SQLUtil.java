package murach.sql;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
/**
 *
 * @author Dmitriy
 */
public class SQLUtil {
    
    public static String getHtmlTable(ResultSet results) throws SQLException {
      StringBuilder htmlTable= new StringBuilder();
      ResultSetMetaData metaData=results.getMetaData();
      int columnCount = metaData.getColumnCount();
      
      htmlTable.append("<table>");
      
      //add header row
      htmlTable.append("<tr>");
      for (int i=1; i<=columnCount; i++) {
          htmlTable.append("<th>");
          htmlTable.append(metaData.getColumnName(i));
          htmlTable.append("</th>");
      }
      htmlTable.append("</tr>");  
      
      //add all other rows
      while (results.next()) {
          htmlTable.append("<tr>");
          for (int i=1; i<=columnCount;i++) {
              htmlTable.append("<td>");
              htmlTable.append(results.getString(i));
              htmlTable.append("</td>");
          }
          htmlTable.append("</tr>");
      }
      htmlTable.append("</table>");
      return htmlTable.toString();
     
    }
    
}
