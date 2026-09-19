/*
* ============================================================================
*  Name        : ?filename.cpp
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
* Template version: 4.1
*/


#include <?include_file>

#include "?include_file"

extern ?external_function( ?arg_type, ?arg_type );

const ?type ?constant_var = ?constant;


// ======== LOCAL FUNCTIONS ========

// ---------------------------------------------------------------------------
// ?description
// ---------------------------------------------------------------------------
//
?type ?function_name( ?arg_type ?arg,
                      ?arg_type ?arg )
    {
    ?code  // ?implementation comment on this line
    // ?implementation comment on the following statement or block:
    ?code
    }


// ======== MEMBER FUNCTIONS ========


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
?classname::?classname()
    {
    if ( ?condition )
        {
        // ?implementation_comment
        ?code
        }
    else
        {
        // ?implementation_comment
        ?code
        }

    // ?implementation_comment
    while ( ?condition )
        {
        ?code
        }

    // ?implementation_comment
    for ( ?for_init_statement; ?condition; ?expression )
        {
        ?code
        }

    // ?implementation_comment
    switch ( ?condition )
        {
        case ?constant:
            ?code
            break;
        case ?constant:
            ?code
            // fall-through intended here
        case ?constant:
            ?code
            break;
        default:
            ?code
            break;
        }
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
void ?classname::ConstructL()
    {
    ?code
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
EXPORT_C ?classname* ?classname::NewL()
    {
    ?classname* self = ?classname::NewLC();
    CleanupStack::Pop( self );
    return self;
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
EXPORT_C ?classname* ?classname::NewLC()
    {
    ?classname* self = new( ELeave ) ?classname;
    CleanupStack::PushL( self );
    self->ConstructL();
    return self;
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
?classname::~?classname()
    {
    ?code
    }


// ---------------------------------------------------------------------------
// ?implementation_description
// ---------------------------------------------------------------------------
//
?EXPORT_C ?type ?classname::?member_function(
    ?really_really_really_really_long_arg_type_1 ?really_really_long_arg_1,
    ?really_really_really_really_long_arg_type_2 ?really_really_long_arg_2 )
    {
    ?code
    }


// ---------------------------------------------------------------------------
// From class ?base_class.
// ?implementation_description
// ---------------------------------------------------------------------------
//
?EXPORT_C ?type ?function_name( ?arg_type_1 ?arg_1, ?arg_type_2 ?arg_2 )
    {
    ?code
    }


// ======== GLOBAL FUNCTIONS ========

// ---------------------------------------------------------------------------
// Constructs and returns an application object.
// ---------------------------------------------------------------------------
//
EXPORT_C CApaApplication* NewApplication()
    {
    return new ?CMyApplication;
    }


// ---------------------------------------------------------------------------
// Main function of the application executable.
// ---------------------------------------------------------------------------
//
GLDEF_C TInt E32Main()
    {
    return EikStart::RunApplication( NewApplication );
    }
