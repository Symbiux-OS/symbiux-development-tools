/*
* ============================================================================
*  Name        : TestFileName.h
*  Part of     : TestSubSystem / TestModule       *** Info from the SWAD
*  Description : TestDescription
*                
*                continues
*                
*                ---
*                still
*  Version     : %version: % << Don't touch! Updated by Synergy at check-out.
*
*  Copyright © 2000-2001 Nokia Corporation and/or its subsidiary(-ies).
*  All rights reserved.
*  This component and the accompanying materials are made available
*  under the terms of "Eclipse Public License v1.0"
*  which accompanies this distribution, and is available
*  at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
*  Initial Contributors:
*  Nokia Corporation - initial contribution.
*
*  Contributors:
*  Nokia Corporation
* ============================================================================
* Template version: 4.2
*/

#ifndef C_TESTCLASSNAME_H
#define C_TESTCLASSNAME_H


#include <?include_file>

#include "?include_file"

class ?forward_classname;

/**  ?description */
extern ?data_type;

/**
 * ?description
 *
 * @since S60 ?S60_version
 * @param ?arg1 ?description
 * @return ?description
 */
?type ?function_name( ?arg_list );

/**  ?description */
const ?type ?constant_var = ?constant;


/**
 *  ?one_line_short_description
 *  ?more_complete_description
 *
 *  @code
 *   ?good_class_usage_example(s)
 *  @endcode
 *
 *  @lib ?library
 *  @since S60 ?S60_version *** for example, S60 v3.0
 */
class TestClassName : public ?base_class_list
    {

    friend class ?class1;
    friend class ?class2;

public:

    /**  ?description */
    enum ?declaration

    /**  ?description */
    typedef ?declaration

    ?IMPORT_C static TestClassName* NewL();
    ?IMPORT_C static TestClassName* NewLC();

    /**
     * Two-phased constructor.
     * @param ?arg1 ?description
     * @param ?arg2 ?description
     */
    ?IMPORT_C static TestClassName* NewL(?type1 ?arg1, ?type2 ?arg2);
    

    /**
    * Destructor.
    */
    virtual ~TestClassName();

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

private:

    TestClassName();

    void ConstructL();

private: // data

    /**
     * ?description_of_member
     */
    ?type ?member_name;

    /**
     * ?description_of_pointer_member
     * Own.  *** Write "Own" if this class owns the object pointed to; in
                 other words, if this class is responsible for deleting it.
     */
    ?type* ?member_name;

    /**
     * ?description_of_pointer_member
     * Not own.  *** Write "Not own" if some other class owns this object.
     */
    ?type* ?member_name;

    };

#include "?include_file.inl"


#endif // C_TESTCLASSNAME_H
