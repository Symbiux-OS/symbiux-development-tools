/*
* ============================================================================
*  Name        : ?filename.h
*  Part of     : ?Subsystem_name / ?Module_name
*  Description : ?Description
*  Version     : %version: %
*
*  Copyright © ?year-?year ?Company_copyright.
*  All rights reserved.
*  This component and the accompanying materials are made available
*  under the terms of the License "?License"
*  which accompanies this distribution, and is available
*  at the URL "?LicenseUrl".
*
*  Initial Contributors:
*  ?Company_name - initial contribution.
*
*  Contributors:
*  ?Company_name
* ============================================================================
* Template version: 4.2
*/

#ifndef ?T_CLASSNAME_H
#define ?T_CLASSNAME_H

#include <?include_file>

#include "?include_file"

class ?forward_classname;

/**  ?description */
extern ?data_type;

const ?type ?constant_var = ?constant;

/**
 *  ?one_line_short_description
 *
 *  ?more_complete_description
 *
 *  @code
 *   ?good_class_usage_example(s)
 *  @endcode
 *
 *  @lib ?library
 *  @since S60 ?S60_version *** for example, S60 v3.0
 */
class ?classname : public ?base_class_list
    {

    friend class ?class1;
    friend class ?class2;

public:

    /**  ?description */
    enum ?declaration

    /**  ?description */
    typedef ?declaration

    ?classname();

    /**
     * Constructor.
     *
     * @since S60 ?S60_version
     * @param ?arg1 ?description
     * @param ?arg2 ?description
     * @return ?description
     */
    ?classname( ?type1 ?arg1, ?type2 ?arg2 );

    /**
     * ?description
     *
     * @since S60 ?S60_version
     * @param ?arg1 ?description
     * @param ?arg2 ?description
     * @return ?description
     */
    ?IMPORT_C ?type ?member_function( ?type1 ?arg1, ?type2 ?arg2 );

// from base class ?base_class1

    /**
     * From ?base_class1.
     * ?description
     *
     * @since S60 ?S60_version
     * @param ?arg1 ?description
     */
    ?IMPORT_C ?type ?member_function( ?type ?arg1 );

// from base class ?base_class2

protected:

// from base class ?base_class2

// from base class ?base_class3

private: // data

    /**
     * ?description_of_member
     */
    ?type ?member_name;

    };

#include "?include_file.inl"


#endif // ?T_CLASSNAME_H
