#!/usr/bin/env jython

import java.sql.DriverManager
import jarray
import random
import SQLCheckIn

privkey = """MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtioM/ucS9EFq48upIDu32gofJGnu
hSwBgya56xjiG3QzWgCsblvNMbFoNj6RKj6tHUJGLu9iX8zp3ihnXIo6vQIDAQABAkBkentimqis
y+yeq7alDeuSPCUDW97x290Vr2W7lKUbSKMgERDs72eIxlppbQ5SL3x7Vw7Y5YbYYO0vJLrwKWgt
AiEA+wHCV7mpnUYthQuMaWRmJjHPTBR3UtIGyOZJgITynh8CIQC5yba8V0VF2d4+JCKGKhjeF0DZ
xKnKfiVh0NfwksTTowIgeNka+WOBPyBnkBPEUYWuDJuUo5uC65QFAjqljsPzj58CIQCFXl5Fmchq
ulml9j6qXTH4zGyLZ3gTmravH0LEujd9TQIgdYEfqEAgE5rss3qmq3FfdwhNpzoA+IzCUVSPyHoz
JLE="""

pubkey = """MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALYqDP7nEvRBauPLqSA7t9oKHyRp7oUsAYMmuesY4ht0
M1oArG5bzTGxaDY+kSo+rR1CRi7vYl/M6d4oZ1yKOr0CAwEAAQ=="""

con = java.sql.DriverManager.getConnection( "jdbc:mysql://localhost/test?user=root&password=secret" )
t = SQLCheckIn( privkey, pubkey, "root", con, "test" )

print "Storing random binary data ..."

for i in range( 10 ):
	data = jarray.array( [ random.randint( -128, 127 ) for i in range( 80 ) ], 'b' )
	t.store( data )
