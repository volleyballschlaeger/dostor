import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Http {
	private static final Pattern LENPATTERN = Pattern.compile( "start:(\\d{1,5})\r\n" );
	private static final Charset ASCII = Charset.forName( "US-ASCII" );
	private static final Charset LATIN1 = Charset.forName( "ISO-8859-1" );

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
			byte[] head = sb.toString().getBytes( ASCII );
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
		if( len == -1 )
			return null;
		inputstream.reset();
		Matcher m = LENPATTERN.matcher( new String( tmp, 0, len, LATIN1 ) );
		if( !m.find() )
			return null;
		len = Integer.valueOf( m.group( 1 ) );
		byte[] res = new byte[len];
		inputstream.skip( m.end() );
		if( inputstream.read( res ) != len )
			throw new IOException( "Unexpected end of stream." );

		return res;
	}
}
