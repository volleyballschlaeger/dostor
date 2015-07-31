<?php
function checkId( $id, $data )
{
	if( strlen( $id ) != 64 )
		return FALSE;
	if( !ctype_xdigit( $id ) )
		return FALSE;
	$md5a = substr( $id, 0, 32 );
	$md5b = md5( $data );
	return $md5a === $md5b;
}
?>
