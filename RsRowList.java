import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Make a ResultSet accessible via the RowList interface.
 */

public class RsRowList implements RowList {
	private final ResultSet resultSet;

	/**
	 * @param rs The ResultSet to be made accessible.
	 */
	public RsRowList( ResultSet rs ) {
		resultSet = rs;
	}

	@Override
	public boolean first() {
		try {
			return resultSet.first();
		} catch ( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean next() {
		try {
			return resultSet.next();
		} catch ( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getColumnCount() {
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch ( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getString( int columnIndex ) {
		try {
			return resultSet.getString( columnIndex + 1 );
		} catch ( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
