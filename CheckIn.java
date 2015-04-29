import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class CheckIn {
	private final RSASign rsaSign;
	private final String fingerprint;

	public static String BytesToHex( byte[] in )
	{
		StringBuilder sb = new StringBuilder();
		for( byte b : in )
			sb.append( String.format( "%02x", b ) );
		return sb.toString();
	}

	public static String md5sum( byte[] data )
	{
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance( "MD5" );
		} catch( NoSuchAlgorithmException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( md == null )
			return null;

		return BytesToHex( md.digest( data ) );
	}

	public CheckIn( String privateKeyStr, String publicKeyStr )
	{
		rsaSign = new RSASign( privateKeyStr );
		fingerprint = md5sum( publicKeyStr.getBytes() ).substring( 0, 20 );
	}

	private String genId( byte[] data )
	{
		return md5sum( data ) + String.format( "%012x", System.currentTimeMillis() ) + fingerprint;
	}

	public boolean store( String str )
	{
		try {
			return store( str.getBytes( "UTF8" ) );
		} catch ( UnsupportedEncodingException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean store( byte[] data )
	{
		String id = genId( data );
		byte[] head = ( id + "\r\n" ).getBytes();
		byte[] concat = new byte[ head.length + data.length ];
		System.arraycopy( head, 0, concat, 0, head.length );
		System.arraycopy( data, 0, concat, head.length, data.length );
		String sign = rsaSign.sign( concat );
		if( sign == null )
			return false;
		return writeToDb( sign, id, data );
	}

	protected abstract boolean writeToDb( String sign, String id, byte[] data );
}
