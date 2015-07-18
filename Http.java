import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Http {
	public static List<String> RecvStringList( String urlString ) throws IOException
	{
		BufferedReader reader = null;

		try {
			List<String > res = new ArrayList<String>();
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
}
