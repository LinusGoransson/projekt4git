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
    
    public JsonArray getRec_Ing() {
        try {

            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM rec_ing_view;";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String namn = data.getString("r_namn");
                String instruktion = data.getString("instruktion");
                String i_namn = data.getString("i_namn");
                String author = data.getString("author");
                String amount = data.getString("amount");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("r_namn", namn)
                        .add("instruktion", instruktion)
                        .add("author", author)
                        .add("i_namn", i_namn)
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
        String author = data.getString("author");
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("INSERT INTO recept VALUES (NULL,?,?,?)");
                 stmt.setString(1, r_namn);
                 stmt.setString(2, instruktion);
                 stmt.setString(3, author);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
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
        String author = data.getString("author");
        
            try {
                Connection connection = ConnectionFactory.make("127.0.0.1");
                 PreparedStatement stmt = connection.prepareStatement("UPDATE recept SET r_namn = ?, instruktion = ?, author = ? WHERE recept_id = ?");
                 stmt.setString(1, r_namn);
                 stmt.setString(2, instruktion);
                 stmt.setString(3, author);
                 stmt.setInt(4, recept_id);
                 stmt.executeUpdate();
                 connection.close();
                 return true;
            } catch (Exception e) {
                return false;
            }

        
    }
}
