import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Http {
	public static List<String> RecvStringList( String urlString ) throws IOException
	{
		BufferedReader reader = null;

		try {
			List<String> res = new ArrayList<String>();
			URL url = new URL( urlString );
			URLConnection connection = url.openConnection();
			connection.setReadTimeout( 20000 );
			connection.setConnectTimeout( 20000 );
			reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

			String line;
			while( ( line = reader.readLine() ) != null )
				res.add( line );

			return res;
		}
		finally {
			if( reader != null )
				reader.close();
		}
	}

	public static void BinRowListOut( OutputStream out, RowList rl ) throws IOException
	{
		int colcount = rl.getColumnCount();
		boolean run = rl.first();

		while( run )
		{
			StringBuilder sb = new StringBuilder();
			for( int i = 0; i < colcount - 1; i++ )
				sb.append( rl.getString( i ) + "\r\n" );
			byte[] head = sb.toString().getBytes();
			byte[] tail = rl.getBinary( colcount - 1 );
			run = rl.next();
			if( tail == null )
				continue;
			int len = head.length + tail.length;
			out.write( ( "start:" + len + "\r\n" ).getBytes() );
			out.write( head );
			out.write( tail );
			out.write( "\r\n".getBytes() );
		}
	}

	private static byte[] nextBlock( BufferedInputStream inputstream ) throws IOException
	{
		int len;
		byte[] tmp = new byte[1024];
		inputstream.mark( 1024 );
		len = inputstream.read( tmp );
		inputstream.reset();
		String start = new String( tmp, 0, len, "ISO8859-1" );
		int startpos = start.indexOf( "start:" );
		if( startpos == -1 )
			return null;
		int endpos = start.indexOf( "\r\n", startpos + 6 );
		if( endpos == -1 )
			return null;
		String lenstr = start.substring( startpos + 6, endpos );
		if( !lenstr.matches( "\\d{1,5}" ) )
			throw new IOException( "Invalid: \"" + lenstr + "\"" );
		len = Integer.valueOf( lenstr );
		byte[] res = new byte[len];
		inputstream.skip( endpos + 2 );
		if( inputstream.read( res ) != len )
			throw new IOException( "Unexpected EOF." );

		return res;
	}
}
