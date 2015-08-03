import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCheckIn extends CheckIn {
	private final PreparedStatement pstmt;

	public SQLCheckIn( String privateKeyStr, String publicKeyStr, String location, Connection con, String table ) throws SQLException {
		super( privateKeyStr, publicKeyStr );
		String query = "INSERT INTO " + table + " ( location, sign, id, data ) VALUES ( ?, ?, ?, ? );";
		pstmt = con.prepareStatement( query );
		pstmt.setString( 1, location );
	}

	@Override
	protected boolean writeToDb( String sign, String id, byte[] data ) {
		try {
			pstmt.setString( 2, sign );
			pstmt.setString( 3, id );
			pstmt.setBytes( 4, data );
			pstmt.execute();
			return true;
		} catch( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
