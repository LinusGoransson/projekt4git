/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import nu.te4.support.ConnectionFactory;

@Stateless
public class ReceptBean {
    
    public JsonArray getLastRec() {
        try {

            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT recept_id FROM recept ORDER BY recept_id DESC LIMIT 1;";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                int recept_id = data.getInt("recept_id");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("recept_id", recept_id).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }
    
    public JsonArray getRec_Ing(int id) {
        try {

            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM rec_ing_view where r_id ="+id;
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String i_namn = data.getString("i_namn");
                int recept_id = data.getInt("r_id");
                String amount = data.getString("amount");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("i_namn", i_namn)
                        .add("r_id", recept_id)
                        .add("amount", amount).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }
    
    public boolean addIng(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        String i_namn = data.getString("i_namn");
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO ingredienser VALUES (NULL,?)");
                 stmt.setString(1, i_namn);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }

    }
    
    public boolean addRec(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        String r_namn = data.getString("r_namn");
        String instruktion = data.getString("instruktion");
        String kategori = data.getString("kategori");
        String imglink = data.getString("imglink");
        String author = data.getString("author");
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO recept VALUES (NULL,?,?,?,?,?)");
                 stmt.setString(1, r_namn);
                 stmt.setString(2, instruktion);
                 stmt.setString(3, author);
                 stmt.setString(4, kategori);
                 stmt.setString(5, imglink);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }

    }
    
    public boolean addRec_Ing(String body){
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        int r_id = data.getInt("r_id");
        int i_id = data.getInt("i_id");
        String amount = data.getString("amount");
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO recept_ingredienser VALUES (?, ?, ?);");
                 stmt.setInt(1, r_id);
                 stmt.setInt(2, i_id);
                 stmt.setString(3, amount);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }
    }
    public boolean deleteRec(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM recept WHERE recept_id=?");
            stmt.setInt(1, id);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteIng(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ingredienser WHERE id=?");
            stmt.setInt(1, id);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteRec_Ing(int recept_id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE a.*, b.* FROM recept as a, recept_ingredienser as b WHERE a.recept_id = b.r_id AND a.recept_id = ?");
            stmt.setInt(1, recept_id);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteIng_Rec(int r_id,int i_id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM recept_ingredienser WHERE r_id = ? AND i_id = ?");
            stmt.setInt(1, r_id);
            stmt.setInt(2, i_id);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }
    
    public boolean updateIng(String body) {
        
         JsonReader jsonReader = Json.createReader(new StringReader(body));
         
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
      
        String i_namn = data.getString("i_namn");
        int id = data.getInt("id");
        
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("UPDATE ingredienser SET i_namn = ? WHERE id = ?");
                 stmt.setString(1, i_namn);
                 stmt.setInt(2, id);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }

        
    }
    
    public boolean updateRec(String body) {
        
         JsonReader jsonReader = Json.createReader(new StringReader(body));
         
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
      
        String r_namn = data.getString("r_namn");
        int recept_id = data.getInt("recept_id");
        String instruktion = data.getString("instruktion");
        String imglink = data.getString("imglink");
        String kategori = data.getString("kategori");
        
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("UPDATE recept SET r_namn = ?, instruktion = ?, kategori = ?, imglink = ? WHERE recept_id = ?");
                 stmt.setString(1, r_namn);
                 stmt.setString(2, instruktion);
                 stmt.setString(3, kategori);
                 stmt.setString(4, imglink);
                 stmt.setInt(5, recept_id);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }

        
    }
    
    public JsonArray getTable(){
             try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM recept");
            System.out.println(stmt.toString());
            ResultSet data = stmt.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while(data.next()){
                
                arrayBuilder.add(Json.createObjectBuilder().add("recept_id",data.getInt("recept_id")).add("imglink",data.getString("imglink")).add("author",data.getString("author")).add("instruktion",data.getString("instruktion")).add("r_namn", data.getString("r_namn")).add("kategori", data.getString("kategori")).build());
            }
           connection.close();
            return arrayBuilder.build();
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
    }
    
    public JsonArray getIng(){
             try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ingredienser");
            System.out.println(stmt.toString());
            ResultSet data = stmt.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while(data.next()){
                arrayBuilder.add(Json.createObjectBuilder().add("id",data.getInt("id")).add("i_namn", data.getString("i_namn")).build());
            }
           connection.close();
            return arrayBuilder.build();
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
    }
   
}
