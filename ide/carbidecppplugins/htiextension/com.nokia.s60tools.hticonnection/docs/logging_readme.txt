#
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
#

Configuring logging:

- Copy release\htiapi.jar to the lib directory of your application.
- Modify conf\htiapilogging.properties, OR if your app is already using java logging, copy the htiapi detailed
  logging levels (com.nokia.HTI.*) to the existing logging properties file from conf\htiapilogging.properties
  file and adjust them as you like.
- Add to your application's start script following java system property (only for applications NOT using java
  logging at the moment, otherwise the following should already be included):
  
   -Djava.util.logging.config.file=some_directory_path_under_your_app/conf/htiapilogging.properties
   