import java.io.IOException;

public class Debug {
	public static void dumpRowListStr( RowList rl )
	{
		int colcount = rl.getColumnCount();
		boolean run = rl.first();

		while( run )
		{
			String line = "";
			boolean first = true;
			for( int i = 0; i < colcount; i++ )
			{
				if( !first )
					line += "|";
				line += rl.getString( i );
				first = false;
			}
			System.out.println( line );
			run = rl.next();
		}
	}

	public static void dumpRowListBin( RowList rl )
	{
		int colcount = rl.getColumnCount();
		boolean run = rl.first();

		while( run )
		{
			boolean first = true;
			for( int i = 0; i < colcount; i++ )
			{
				if( !first )
					System.out.print( '|' );
				byte[] tmp = rl.getBinary( i );
				try {
					if( tmp == null )
						System.out.print( "null" );
					else
						System.out.write( tmp );
				} catch ( IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				first = false;
			}
			System.out.print( '\n' );
			run = rl.next();
		}
	}
}
