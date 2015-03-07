/**
 * A simplified database cursor.
 */

public interface RowList {
	/**
	 * Moves to the first position.
	 * @return Wether the move succeeded.
	 */
	public boolean first();

	/**
	 * Moves to the next position.
	 * @return Wether the move succeeded.
	 */
	public boolean next();

	/**
	 * @return Number of columns.
	 */
	public int getColumnCount();

	/**
	 * @param columnIndex The zero-based index of the target column.
	 * @return The value of that column.
	 */
	public String getString( int columnIndex );

	/**
	 * @param columnIndex The zero-based index of the target column.
	 * @return The value of that column.
	 */
	public byte[] getBinary( int columnIndex );
}
