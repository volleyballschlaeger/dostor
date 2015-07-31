<?php
function nextBlock( $raw, &$readpos )
{
	$startpos = strpos( $raw, "start:", $readpos );
	if( $startpos === FALSE )
		return FALSE;
	$readpos = $startpos + 6;
	$mark = strpos( $raw, "\r\n", $readpos );
	if( $mark === FALSE )
		return FALSE;
	$len = intval( substr( $raw, $readpos, $mark - $readpos ) );
	if( $len < 0 )
		return FALSE;
	$readpos = $mark + 2;
	$res = substr( $raw, $readpos, $len );
	if( strlen( $res ) != $len )
		return FALSE;
	$readpos += $len;
	return $res;
}
?>
