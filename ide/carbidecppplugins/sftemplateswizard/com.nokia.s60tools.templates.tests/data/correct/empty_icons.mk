# ============================================================================
#  Name        : icons.mk
#  Part of     : TestSubSystem / TestModule       *** Info from the SWAD
#  Description : icons makefile for project TestAppName
#  Version     : %version: % << Don't touch! Updated by Synergy at check-out.
#
#  Copyright © 2000-2001 Nokia Corporation and/or its subsidiary(-ies). 
#  All rights reserved.
#  This component and the accompanying materials are made available
#  under the terms of "Eclipse Public License v1.0"
#  which accompanies this distribution, and is available
#  at the URL "http://www.eclipse.org/legal/epl-v10.html".
#
#  Initial Contributors:
#  Nokia Corporation - initial contribution.
#
#  Contributors:
#  Nokia Corporation
# ============================================================================
# Template version: 4.0.1

ifeq (WINS,$(findstring WINS,$(PLATFORM)))
ZDIR=$(EPOCROOT)epoc32\release\$(PLATFORM)\$(CFG)\z
else
ZDIR=$(EPOCROOT)epoc32\data\z
endif

TARGETDIR=$(ZDIR)\resource\apps
HEADERDIR=$(EPOCROOT)epoc32\include
ICONTARGETFILENAME=$(TARGETDIR)\TestAppName.mif
HEADERFILENAME=$(HEADERDIR)\TestAppName.mbg

MAKMAKE :
	mifconv $(ICONTARGETFILENAME) /h$(HEADERFILENAME) \
		/c8,1 qgn_myapp_icon_1 \
		/c8,1 qgn_myapp_icon_2

BLD : ;

CLEAN : ;

LIB : ;

CLEANLIB : ;

RESOURCE : ;

FREEZE : ;

SAVESPACE : ;

RELEASABLES :
	@echo $(HEADERFILENAME)&& \
	@echo $(ICONTARGETFILENAME)

FINAL : ;
