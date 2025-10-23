import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurfDAO {

    public List<TurfModel> getAllTurfs() {
        List<TurfModel> turfs = new ArrayList<>();
        String sql = "SELECT turf_id, name, address, price_per_hour, operating_hours FROM turfs";
        
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) { 

            while (rs.next()) {
                int turfId = rs.getInt("turf_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int price = rs.getInt("price_per_hour");
                String hours = rs.getString("operating_hours");
                
                TurfModel turf = new TurfModel(turfId, name, address, price, hours);
                turfs.add(turf);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching turfs. SQL Error: " + e.getMessage());
        }
        
        return turfs;
    }
    
    public int getTurfIdByName(String turfName) {
        int turfId = -1;
        String sql = "SELECT turf_id FROM turfs WHERE name = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turfName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                turfId = rs.getInt("turf_id");
            }

        } catch (SQLException e) {
            System.err.println("Error looking up Turf ID. SQL Error: " + e.getMessage());
        }
        return turfId;
    }
}
