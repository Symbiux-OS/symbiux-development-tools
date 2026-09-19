rem
rem Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
rem All rights reserved.
rem This component and the accompanying materials are made available
rem under the terms of "Eclipse Public License v1.0"
rem which accompanies this distribution, and is available
rem at the URL "http://www.eclipse.org/legal/epl-v10.html".
rem
rem Initial Contributors:
rem Nokia Corporation - initial contribution.
rem
rem Contributors:
rem
rem Description:
rem

set ECLIPSE_HOME=E:\APPS\Carbide.c_SYMSEE_layout_2.2.0
java -jar %ECLIPSE_HOME%\plugins\org.eclipse.equinox.launcher_1.0.200.v20090520.jar -application org.eclipse.ant.core.antRunner -buildfile test.xml -Declipse-home=%ECLIPSE_HOME% -Dos=win32 -Dws=win32 -Darch=x86 -data E:/workspaces/testworkspace