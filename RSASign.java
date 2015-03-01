import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//import android.util.Base64;

public class RSASign {
	private PrivateKey privateKey = null;

	/**
	* @param privateKeyStr The private key encoded with base64.
	*/
	public RSASign( String privateKeyStr )
	{
		try {
			byte[] privateKeyBytes = new BASE64Decoder().decodeBuffer( privateKeyStr );
			KeySpec privateKeySpec = new PKCS8EncodedKeySpec( privateKeyBytes );
			KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
			privateKey = keyFactory.generatePrivate( privateKeySpec );
		}
		catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	* @param data The data to be signed.
	* @return The signature encoded with base64.
	*/
	public String sign( byte[] data )
	{
		if( privateKey == null )
			return null;
		String result = null;
		try {
			Signature sig = Signature.getInstance( "MD5WithRSA" );
			sig.initSign( privateKey );
			sig.update( data );
			byte[] sigBytes = sig.sign();
			result = new BASE64Encoder().encode( sigBytes );
		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
