import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDAO {
    
    public boolean saveBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, turf_id, booking_date, time_slot) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getTurfId());
            stmt.setString(3, booking.getBookingDate());
            stmt.setString(4, booking.getTimeSlot());

            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Booking submission failed. SQL Error: " + e.getMessage());
            return false;
        }
    }
}
