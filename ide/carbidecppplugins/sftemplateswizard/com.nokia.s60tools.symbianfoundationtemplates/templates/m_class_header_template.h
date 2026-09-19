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

*** INSTRUCTIONS TO THE TEMPLATE USER:

*** This template follows the Symbian Foundation coding conventions.
*** Remove all unneeded declarations and definitions before checking 
*** the file in!  Also remove the template's usage instructions, that is, 
*** everything that begins with "***".

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

*** An M class defines only pure virtual functions, therefore, is an
*** abstract base class.  An M class will derive from zero or more
*** other M classes.


#ifndef ?M_CLASSNAME_H
#define ?M_CLASSNAME_H


*** system include files go here:

#include <?include_file>

*** user include files go here:

#include "?include_file"

*** external data types go here:

/**  ?description */
extern ?data_type;

*** forward declarations go here:

class ?forward_classname;

*** constants go here:

/**  ?description */
const ?type ?constant_var = ?constant;


*** the class declaration goes here:

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

*** data types go here:

    /**  ?description */
    enum ?declaration

    /**  ?description */
    typedef ?declaration

*** pure virtual functions go here:

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

*** function declarations as above

    };


#endif // ?M_CLASSNAME_H
