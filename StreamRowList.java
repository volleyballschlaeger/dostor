import java.nio.charset.Charset;

abstract class StreamRowList implements RowList {
	private static final Charset UTF8 = Charset.forName( "UTF-8" );

	protected String cols[] = null;
	private final int colcount;
	protected byte[] data = null;
	protected int status = 0;

	public StreamRowList( int c ) {
		colcount = c;
	}

	@Override
	public final boolean first() {
		return next();
	}

	@Override
	public final int getColumnCount() {
		return colcount;
	}

	@Override
	public final String getString( int columnIndex ) {
		if( cols == null || columnIndex < 0 || columnIndex >= colcount )
			return null;
		if( columnIndex == colcount - 1 )
			return new String( data, UTF8 );
		else
			return cols[columnIndex];
	}

	@Override
	public final byte[] getBinary( int columnIndex ) {
		if( columnIndex == colcount - 1 )
			return data;
		else
			return null;
	}

	public final int getStatus() {
		return status;
	}
}
