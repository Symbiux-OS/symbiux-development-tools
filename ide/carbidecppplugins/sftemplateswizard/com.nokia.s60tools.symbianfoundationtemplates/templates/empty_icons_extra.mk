# ============================================================================
#  Name        : icons_extra.mk
#  Part of     : ?Subsystem_name / ?Module_name
#  Description : extra icons makefile for project ?myapp
#  Version     : %version: %
#
#  Copyright © ?year-?year ?Company_copyright. 
#  All rights reserved.
#  This component and the accompanying materials are made available
#  under the terms of the License "?License"
#  which accompanies this distribution, and is available
#  at the URL "?LicenseUrl".
#
#  Initial Contributors:
#  ?Company_name - initial contribution.
#
#  Contributors:
#  ?Company_name
# ============================================================================
# Template version: 4.1.1


ifeq (WINS,$(findstring WINS, $(PLATFORM)))
ZDIR=$(EPOCROOT)epoc32\release\$(PLATFORM)\$(CFG)\z
else
ZDIR=$(EPOCROOT)epoc32\data\z
endif

TARGETDIR=$(ZDIR)\resource\apps
ICONTARGETFILENAME=$(TARGETDIR)\?smallmyapp_extraicons.mif

HEADERDIR=\epoc32\include
HEADERFILENAME=$(HEADERDIR)\?smallmyapp_extraicons.mbg


do_nothing :
	@rem do_nothing

MAKMAKE : do_nothing

BLD : do_nothing

CLEAN :
	@if exist $(ICONTARGETFILENAME) erase $(ICONTARGETFILENAME)
	@if exist $(HEADERFILENAME) erase $(HEADERFILENAME)

LIB : do_nothing

CLEANLIB : do_nothing

RESOURCE : $(ICONTARGETFILENAME)

$(ICONTARGETFILENAME) (HEADERFILENAME) : qgn_?smallmyapp_icon_1.svg qgn_?smallmyapp_icon_2.svg
	mifconv $(ICONTARGETFILENAME) /h$(HEADERFILENAME) \
		/c8,8 qgn_?smallmyapp_icon_1.svg \
		/c8,8 qgn_?smallmyapp_icon_2.svg

FREEZE : do_nothing

SAVESPACE : do_nothing

RELEASABLES :
	@echo $(HEADERFILENAME) && \
	@echo $(ICONTARGETFILENAME)

FINAL : do_nothing