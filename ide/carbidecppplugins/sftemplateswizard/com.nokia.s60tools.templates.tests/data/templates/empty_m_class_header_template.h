/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description:  ?Description
*
*/


#ifndef ?M_CLASSNAME_H
#define ?M_CLASSNAME_H


#include <?include_file>

#include "?include_file"

/**  ?description */
extern ?data_type;

class ?forward_classname;

/**  ?description */
const ?type ?constant_var = ?constant;


/**
 *  ?one_line_short_description
 *
 *  ?more_complete_description
 *  @code
 *   ?good_class_usage_example(s)
 *  @endcode
 *
 *  @lib ?library
 *  @since S60 ?S60_version *** for example, S60 v3.0
 */
class ?classname : public ?base_class_list
    {

public:

    /**  ?description */
    enum ?declaration

    /**  ?description */
    typedef ?declaration

    /**
     * ?description
     *
     * @since S60 ?S60_version
     * @param ?arg1 ?description
     * @param ?arg2 ?description
     * @return ?description
     */
    ?IMPORT_C virtual ?type ?member_function( ?type1 ?arg1, ?type2 ?arg2 ) = 0;

protected:

    };


#endif // ?M_CLASSNAME_H
