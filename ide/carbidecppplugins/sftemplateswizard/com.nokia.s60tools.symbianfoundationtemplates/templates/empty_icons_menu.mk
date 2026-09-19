# ============================================================================
#  Name        : icons_menu.mk
#  Part of     : ?Subsystem_name / ?Module_name
#  Description : menu icons makefile for project ?myapp
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
ICONTARGETFILENAME=$(TARGETDIR)\?smallmyapp_aif.mif


do_nothing :
	@rem do_nothing

MAKMAKE : do_nothing

BLD : do_nothing

CLEAN :
	@if exist $(ICONTARGETFILENAME) erase $(ICONTARGETFILENAME)
	
LIB : do_nothing

CLEANLIB : do_nothing

RESOURCE : $(ICONTARGETFILENAME)

$(ICONTARGETFILENAME) : qgn_menu_?smallmyapp.svg
	mifconv $(ICONTARGETFILENAME) \
		/c8,8 qgn_menu_?smallmyapp.svg

FREEZE : do_nothing

SAVESPACE : do_nothing

RELEASABLES :
	@echo $(ICONTARGETFILENAME)

FINAL : do_nothing