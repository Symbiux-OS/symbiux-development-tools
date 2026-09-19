#
# Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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

To build data folder for configuration file, select "data" folder and right-click:
 
  Export > General > File System
  
And Select "To directory" to be target platforms plugins folder, and type
com.nokia.s60tools.metadataeditor_<feature version number> as directory.

e.g.
 
  C:\Program Files\Nokia\Carbide_2.0.0.014_Internal\plugins\com.nokia.s60tools.metadataeditor_1.1.1
  
Make sure that metadataeditor.properties is ticked.