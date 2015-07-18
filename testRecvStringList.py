#!/usr/bin/env jython

import sys
import Http

for i in Http.RecvStringList( sys.argv[1] ):
	print i
