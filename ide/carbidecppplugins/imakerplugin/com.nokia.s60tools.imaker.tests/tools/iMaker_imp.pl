# Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
# All rights reserved.
# This component and the accompanying materials are made available
# under the terms of "Eclipse Public License v1.0"
# which accompanies this distribution, and is available
# at the URL "http://www.eclipse.org/legal/epl-v10.html".
#
# Initial Contributors:
# Nokia Corporation - initial contribution.
#
# Contributors:
#
# Description:
#
##########################################################################################################################
##########################################################################################################################
#
# iMakerStub.pl
#
# Test stub for iMaker plugin JUnit tests.
#
# This stub is used by IMakerWrapperTest and runs one iMaker call at the time.
# File must be placed to C:\tests\ directory.
#
#
##########################################################################################################################
##########################################################################################################################

$numArgs = $#ARGV + 1;

if($numArgs eq 0) {
print "
iMaker 10.03.01, 24-Apr-2009.
";
exit(0);
}

sub printArgs {
	print "-----------------------\n";
	print "Commandline arguments:\n";
	foreach $argnum (0 .. $#ARGV) {
	   print "$ARGV[$argnum]\n";
	}
}

if(($numArgs eq 2) and($ARGV[0] eq "-f") and ($ARGV[1] =~ /.imp$/)) {
	print "Okei!\n";
	printArgs();
	exit 0;
} else {
	print "Bad arguments!\n";
	printArgs();
	exit 2;
}
#$testCase = $ARGV[0];
#print $testCase;
