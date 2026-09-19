/*
* ============================================================================
*  Name        : TestFileName.cpp
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
TestClassName::TestClassName()
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
void TestClassName::ConstructL()
    {
    ?code
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
EXPORT_C TestClassName* TestClassName::NewL()
    {
    TestClassName* self = TestClassName::NewLC();
    CleanupStack::Pop( self );
    return self;
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
EXPORT_C TestClassName* TestClassName::NewLC()
    {
    TestClassName* self = new( ELeave ) TestClassName;
    CleanupStack::PushL( self );
    self->ConstructL();
    return self;
    }


// ---------------------------------------------------------------------------
// ?description_if_needed
// ---------------------------------------------------------------------------
//
TestClassName::~TestClassName()
    {
    ?code
    }


// ---------------------------------------------------------------------------
// ?implementation_description
// ---------------------------------------------------------------------------
//
?EXPORT_C ?type TestClassName::?member_function(
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
