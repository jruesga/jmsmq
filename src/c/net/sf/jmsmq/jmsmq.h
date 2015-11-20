/********************************************************************************
 *
 * Copyright (C) 2006 Jorge Ruesga
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 ********************************************************************************/

/**
 * CVS
 * ----------------------------
 * 24-mar-2006 :
 *    File creation
 */

/**
 * Header of definition of functions of Microsoft® Message Queuing Server.
 *
 * See MQ Functions Ref:
 * http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true
 */

/**
 *	Longitud de formato de nombre máxima
 */
#define MQ_MAX_FORMAT_NAME_LEN	255 * sizeof(WCHAR)


/**
 * Object identifier structure (OBJECTID) for use in Microsoft® Message Queuing Server.
 *
 * From winnt.h OBJECTID definition
 *
 */
typedef struct  _MSMQUEUEOBJECTID {     // size is 20
    GUID Lineage;
    DWORD Uniquifier;
} MSGQUEUEOBJECTID;
