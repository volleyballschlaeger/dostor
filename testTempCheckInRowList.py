#!/usr/bin/env jython

import sys
import TempCheckInRowList
import Debug

privkey = """MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtioM/ucS9EFq48upIDu32gofJGnu
hSwBgya56xjiG3QzWgCsblvNMbFoNj6RKj6tHUJGLu9iX8zp3ihnXIo6vQIDAQABAkBkentimqis
y+yeq7alDeuSPCUDW97x290Vr2W7lKUbSKMgERDs72eIxlppbQ5SL3x7Vw7Y5YbYYO0vJLrwKWgt
AiEA+wHCV7mpnUYthQuMaWRmJjHPTBR3UtIGyOZJgITynh8CIQC5yba8V0VF2d4+JCKGKhjeF0DZ
xKnKfiVh0NfwksTTowIgeNka+WOBPyBnkBPEUYWuDJuUo5uC65QFAjqljsPzj58CIQCFXl5Fmchq
ulml9j6qXTH4zGyLZ3gTmravH0LEujd9TQIgdYEfqEAgE5rss3qmq3FfdwhNpzoA+IzCUVSPyHoz
JLE="""

pubkey = """MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALYqDP7nEvRBauPLqSA7t9oKHyRp7oUsAYMmuesY4ht0
M1oArG5bzTGxaDY+kSo+rR1CRi7vYl/M6d4oZ1yKOr0CAwEAAQ=="""

t = TempCheckInRowList( privkey, pubkey, "root" )
t.store( sys.stdin.readline() )
Debug.dumpRowListStr( t )
