/*
* ============================================================================
*  Name        : ?filename.h
*  Part of     : ?Subsystem_name / ?Module_name       *** Info from the SWAD
*  Description : ?Description
*  Version     : %version: % << Don't touch! Updated by Synergy at check-out.
*
*  Copyright © ?year-?year Nokia Corporation and/or its subsidiary(-ies).
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

*** INSTRUCTIONS TO THE TEMPLATE USER:

*** This template follows the S60 coding conventions
*** (S60_Coding_Conventions.doc).  Remove all unneeded declarations
*** and definitions before checking the file in!  Also remove the
*** template's usage instructions, that is, everything that begins
*** with "***".

*** The copyright years should be < the year of the file's creation >
*** - < the year of the file's latest update >.

*** Words that begin with "?" are for you to replace with your own
*** identifiers, filenames, or comments.  ?IMPORT_C means either the
*** IMPORT_C visibility directive, or nothing, depending on whether
*** the function is to be exported or not.

*** To support building on Linux, use only forward slashes in include
*** directives.  Also, all filenames and pathnames must be completely
*** in lowercase.

*** Indent four spaces per step, using spaces, not tabs, to display
*** the code consistently across different editors.

*** A C class (except for CBase) will derive from exactly one C class, and
*** from zero or more M classes.  A C class owns heap-allocated (dynamic)
*** memory, and thus needs an explicit destructor.  A C class may be
*** instantiated only on the heap.


#ifndef ?C_CLASSNAME_H
#define ?C_CLASSNAME_H


*** system include files go here:

#include <?include_file>

*** user include files go here:

#include "?include_file"

*** forward declarations go here:

class ?forward_classname;

*** external data types go here:

/**  ?description */
extern ?data_type;

*** global function prototypes (which should be very rare) go here:

/**
 * ?description
 *
 * @since S60 ?S60_version
 * @param ?arg1 ?description
 * @return ?description
 */
?type ?function_name( ?arg_list );

*** constants go here:

/**  ?description */
const ?type ?constant_var = ?constant;


*** the class declaration goes here:

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
class ?classname : public ?base_class_list
    {

*** friend classes go here:

    friend class ?class1;
    friend class ?class2;

public:

*** data types go here:

    /**  ?description */
    enum ?declaration

    /**  ?description */
    typedef ?declaration

*** non-derived functions go here, starting with the Symbian
*** constructors and the C++ destructor:

    ?IMPORT_C static ?classname* NewL();
    ?IMPORT_C static ?classname* NewLC();

    /**
     * Two-phased constructor.
     * @param ?arg1 ?description
     * @param ?arg2 ?description
     */
    ?IMPORT_C static ?classname* NewL(?type1 ?arg1, ?type2 ?arg2);
    

    /**
    * Destructor.
    */
    virtual ~?classname();

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

*** declarations of functions derived from base_class1 are grouped
*** together here:

    /**
     * From ?base_class1.
     * ?description
     *
     * @since S60 ?S60_version
     * @param ?arg1 ?description
     */
    ?IMPORT_C ?type ?member_function( ?type ?arg1 );

// from base class ?base_class2

*** function declarations as above

protected:

*** function declarations as above

// from base class ?base_class2

*** function declarations as above

// from base class ?base_class3

*** function declarations as above


private:

*** private, non-derived functions go here, starting with the C++
*** constructor and the Symbian second-phase constructor

    ?classname();

    void ConstructL();

*** other function declarations as above

*** avoid the use of public or protected data; data should nearly
*** always be accessed through getter and setter functions

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


*** put the inline functions, if any, in a file included here:

#include "?include_file.inl"


#endif // ?C_CLASSNAME_H
