# ============================================================================
#  Name        : icons_aif_bitmaps.mk
#  Part of     : TestSubSystem / TestModule       *** Info from the SWAD
#  Description : scalable icons makefile for project TestAppName
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
# Template version: 4.1.1

ifeq (WINS,$(findstring WINS,$(PLATFORM)))
ZDIR=$(EPOCROOT)epoc32\release\$(PLATFORM)\$(CFG)\z
else
ZDIR=$(EPOCROOT)epoc32\data\z
endif

TARGETDIR=$(ZDIR)\resource\apps
ICONTARGETFILENAME=$(TARGETDIR)\TestAppName_aif.mbm

MAKMAKE : 
	mifconv $(ICONTARGETFILENAME) \
		/c8,8 qgn_myapp_lst.bmp \
		/c8,8 qgn_myapp_cxt.bmp

BLD : ;

CLEAN : ;

LIB : ;

CLEANLIB : ;

RESOURCE : ;

FREEZE : ;

SAVESPACE : ;

RELEASABLES :
	@echo $(ICONTARGETFILENAME)

FINAL : ;
