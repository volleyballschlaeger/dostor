import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TempCheckInRowList extends CheckIn implements RowList {
	private static class Row {
		public final String Sign;
		public final String Id;
		private final byte[] d;

		public Row( String sign, String id, byte[] data )
		{
			Sign = sign;
			Id = id;
			d = data.clone();
		}

		public byte[] getData()
		{
			return d.clone();
		}
	}

	private final String location;
	private int curpos = -1;
	private List<Row> list = new ArrayList<Row>();

	public TempCheckInRowList( String privateKeyStr, String publicKeyStr, String l )
	{
		super( privateKeyStr, publicKeyStr );
		location = l;
	}

	private Row find( String id )
	{
		for( Row i : list )
			if( i.Id.equals( id ) )
				return i;
		return null;
	}

	public boolean delete( String id )
	{
		Row r = find( id );
		if( r == null )
			return false;
		return list.remove( r );
	}

	@Override
	public boolean first()
	{
		if( list.size() < 1 )
			return false;
		curpos = 0;
		return true;
	}

	@Override
	public boolean next()
	{
		if( list.size() <= curpos + 1 )
			return false;
		curpos++;
		return true;
	}

	@Override
	public int getColumnCount()
	{
		return 4;
	}

	@Override
	public String getString( int columnIndex )
	{
		Row r = list.get( curpos );

		switch( columnIndex )
		{
		case 0:
			return location;
		case 1:
			return r.Sign;
		case 2:
			return r.Id;
		case 3:
			try {
				return new String( r.getData(), "UTF8" );
			} catch ( UnsupportedEncodingException e ) {
				e.printStackTrace();
				return null;
			}
		default:
			return null;
		}
	}

	@Override
	public byte[] getBinary( int columnIndex )
	{
		Row r = list.get( curpos );

		switch( columnIndex )
		{
		case 3:
			return r.getData();
		default:
			return null;
		}
	}

	@Override
	protected boolean writeToDb( String sign, String id, byte[] data )
	{
		if( find( id ) != null )
			return false;
		list.add( new Row( sign, id, data ) );
		return true;
	}
}
