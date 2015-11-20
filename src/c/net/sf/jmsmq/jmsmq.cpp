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
 * Implementation of functions of Microsoft® Message Queuing Server.
 *
 * See MQ Functions Ref:
 * http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true
 */

// Include files of Win32 API
#include <stdio.h>
#include <mqoai.h>
#include <mq.h>
#include <jni.h>

// Include files of Microsoft® Message Queuing Server implementation
#include "net_sf_jmsmq_MSMQQueueFactory.h"
#include "net_sf_jmsmq_MSMQQueue.h"
#include "jmsmq.h"

/**
 * Method that creates a message queue into a Microsoft® Message Queuing Server. 
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jstring) jszQueuePathName: Path name of the queue that will be created
 *	[IN](jint) jintXAct: Transaction type of queue [MQ_XACT_NONE --> Non transactional queue; MQ_XACT_TRANSACTIONAL --> Transactional queue]
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQCreateQueue
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQCreateQueue
  (JNIEnv * env, jobject _this, jstring jszQueuePathName, jint jintXAct)
{

	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 2;	// PROPID_Q_PATHNAME; PROPID_Q_TRANSACTION

	// Queue properties structure
	MQQUEUEPROPS  QueueProps;							// Queue properties structure
	QUEUEPROPID	  aQueuePropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aQueuePropVar[NUMBEROFPROPERTIES];	// Array of value types of properties
	HRESULT       aQueueStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Validate all passed params
	const jchar* pjcharQueueName = env->GetStringChars(jszQueuePathName,NULL);
	if (pjcharQueueName == NULL)
	{
		return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
	}

	// Establishing queue properties
		// ----------------------------
		// 1 - Queue identifier
		// ----------------------------
		aQueuePropId[cPropId] = PROPID_Q_PATHNAME;
		aQueuePropVar[cPropId].vt = VT_LPWSTR;	
		aQueuePropVar[cPropId].pwszVal = (LPWSTR)pjcharQueueName;
		cPropId++; //Increments counter

		// ----------------------------
		// 2 - Transactional?
		// ----------------------------
		aQueuePropId[cPropId] = PROPID_Q_TRANSACTION;
		aQueuePropVar[cPropId].vt = VT_UI1;	
		aQueuePropVar[cPropId].bVal = (unsigned char)jintXAct;
		cPropId++; // Increments counter

	// Initializing MQQUEUEPROPS properties struture for create the queue
	QueueProps.cProp = cPropId;					// Number of properties
	QueueProps.aPropID = aQueuePropId;			// Properties identifiers
	QueueProps.aPropVar = aQueuePropVar;		// Properties values
	QueueProps.aStatus = aQueueStatus;			// Pointers to returns state of properties

	// Buffer for read the queue format name returs
	WCHAR wszFormatName[MQ_MAX_FORMAT_NAME_LEN];
	DWORD dwFormatNameLength = MQ_MAX_FORMAT_NAME_LEN;

	// MQCreateQueue invocation for create the queue
	hr = MQCreateQueue(
					NULL,					  // Security descriptor
                    &QueueProps,              // Creation queue properties
                    wszFormatName,			  // Pointer to the queue format name created for MSMQ
                    &dwFormatNameLength);	  // Pointer to the length of wszFormatName
	
	// Delete pointers
	env->ReleaseStringChars(jszQueuePathName,pjcharQueueName);
	free(wszFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that search a queue into a Microsoft® Message Queuing Server. 
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jstring) jszQueueFormatName: Format name of the queue to search
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQLookupQueue
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQLookupQueue
  (JNIEnv *env, jobject _this, jstring jszQueueFormatName)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 1;	// PROPID_MGMT_MSMQ_PRIVATEQ

	// Queue properties structure
	MQQUEUEPROPS  QueueProps;							// Queue properties structure
	QUEUEPROPID	  aQueuePropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aQueuePropVar[NUMBEROFPROPERTIES];	// Array of value types of properties
	HRESULT       aQueueStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Validate all passed params
	const jchar* pjcharQueueFormatName = env->GetStringChars(jszQueueFormatName,NULL);
	if (pjcharQueueFormatName == NULL)
	{
		return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
	}

	// Establishing queue properties
		// ----------------------------
		// 1 - Queue path name
		// ----------------------------
		aQueuePropId[cPropId] = PROPID_Q_PATHNAME;
		aQueuePropVar[cPropId].vt = VT_NULL;	
		cPropId++; // Increments counter

	// Initializing MQQUEUEPROPS properties struture for search the queue
	QueueProps.cProp = cPropId;					// Number of properties
	QueueProps.aPropID = aQueuePropId;			// Properties identifiers
	QueueProps.aPropVar = aQueuePropVar;		// Properties values
	QueueProps.aStatus = aQueueStatus;			// Pointers to returns state of properties

	// MQMgmtGetInfo invocation for retrive queue information. If success, the queue exists an returns a reference for the queue
	hr = MQGetQueueProperties(
					(LPWSTR)pjcharQueueFormatName,		//Queue format name
                    &QueueProps);						//Lookup queue properties

	// Invoked correctly?
	if ( !FAILED(hr) )
	{
		//Save to internal property (for Java recovery)
		jclass clazzField = env->GetObjectClass(_this);				
		jfieldID fieldId = env->GetFieldID(clazzField, "__internal_lookup_queue__", "Ljava/lang/String;");
		jstring newValue = env->NewString( (const jchar*)aQueuePropVar[0].pwszVal, wcslen((const wchar_t*)aQueuePropVar[0].pwszVal ) );
		if (newValue == NULL) {
			hr = MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources			
		}
		else
			env->SetObjectField(_this, fieldId, newValue);
	}
	
	// Delete pointers
	env->ReleaseStringChars(jszQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that enumerate the private queues of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQEnumPrivateQueue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQEnumPrivateQueue
  (JNIEnv *env, jobject _this)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 1;	// PROPID_MGMT_MSMQ_PRIVATEQ

	// Queue properties structure
	MQMGMTPROPS	  MQMGProps;							// Management Queue properties structure
	MGMTPROPID	  aMQMGPropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aMQMGPropVar[NUMBEROFPROPERTIES];		// Array of value types of properties
	HRESULT       aMQMGPStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter
	DWORD		  cQ = 0;								// Private queues counter

	// Result
	HRESULT       hr = MQ_OK;

	// Recovery the server name
		// Recovery the class
		jclass clazz = env->GetObjectClass(_this);
		// Recovery the field
		jfieldID field = env->GetFieldID(clazz, "msmqServer", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a instance of a jstring from the field
		jstring jstrServerName = (jstring)env->GetObjectField(_this, field);
		// Recovery the server name to a char pointer
		const jchar* pjcharServerName = env->GetStringChars(jstrServerName,NULL);
		if (pjcharServerName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

	// Establishing management queue properties
		// ----------------------------
		// 1 - Private queue enumeration property
		// ----------------------------
		aMQMGPropId[cPropId] = PROPID_MGMT_MSMQ_PRIVATEQ;
		aMQMGPropVar[cPropId].vt = VT_NULL;	
		cPropId++; // Increments counter

	// Initializing MQMGMTROPS properties struture for enumerate the private queues
	MQMGProps.cProp = cPropId;					// Number of properties
	MQMGProps.aPropID = aMQMGPropId;			// Properties identifiers
	MQMGProps.aPropVar = aMQMGPropVar;			// Properties values
	MQMGProps.aStatus = aMQMGPStatus;			// Pointers to returns state of properties

	// MQMgmtGetInfo invocation for retrive queue information. If success then create a enumerator references of private queues
	LPWSTR lpwszLocalhost = L"localhost";
	hr = MQMgmtGetInfo(
					(wcscmp( (LPWSTR)pjcharServerName, L"." ) == 0)?lpwszLocalhost:(LPWSTR)pjcharServerName, //Name of the MSMQ server (replace to "." from "localhost" to ensure correctly invocation)
                    MO_MACHINE_TOKEN,         // Object Type
                    &MQMGProps);              // Request properties structure

	// Invoked correctly?
	if ( !FAILED(hr) )
	{
		// For each private queue recovery
		if (aMQMGPropVar[0].calpwstr.cElems > 0)
		{
			// How much elements found
			for (cQ = 0; cQ < aMQMGPropVar[0].calpwstr.cElems; cQ++){

				// Charge the internal synchronized enumeration object
					// 1.- Charge the internal synchronized object for save queue format name
					jclass clazzField = env->GetObjectClass(_this);				
					jfieldID fieldId = env->GetFieldID(clazzField, "__internal_format_name__", "Ljava/lang/String;");
					jstring newValue = env->NewString( (const jchar*)aMQMGPropVar[0].calpwstr.pElems[cQ], wcslen((const wchar_t*)aMQMGPropVar[0].calpwstr.pElems[cQ]) );
					if (newValue == NULL) {
						hr = MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
						break;
					}
					env->SetObjectField(_this, fieldId, newValue);

					// 2.- Add the internal queue format name to enumeration reference
					jclass clazzMethod = env->GetObjectClass(_this);
					jmethodID mid = env->GetMethodID(clazzMethod, "__addEnumFormatName__", "()V");
					if (mid == NULL) {
						hr = MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
						break;
					}
					env->CallVoidMethod(_this, mid);
			}
			
		}

		// Free format names memory
		for (cQ = 0; cQ < aMQMGPropVar[0].calpwstr.cElems; cQ++)
			MQFreeMemory(aMQMGPropVar[0].calpwstr.pElems[cQ]);
		MQFreeMemory(aMQMGPropVar[0].calpwstr.pElems);
	}

	// Delete pointers
	free(lpwszLocalhost);
	env->ReleaseStringChars(jstrServerName,pjcharServerName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that close the handle of the open queue of a Microsoft® Message Queuing Server. 
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQCloseHandle
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQCloseHandle
  (JNIEnv *env, jobject _this)
{
	// Result
	HRESULT       hr = MQ_OK;

	// Recovery the handles of the queue
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);				
		jfieldID field = env->GetFieldID(clazz, "hQueue", "I");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		unsigned long hQueue = (unsigned long)env->GetIntField(_this, field);

	// Close the open´s handle, if it´s open
	if( hQueue != 0 ){
		hr = MQCloseQueue((QUEUEHANDLE)hQueue);

		// Ok? Handle=0
		if( !FAILED(hr) )
			env->SetIntField(_this, field, (jint)0);
	}

	// Returns operation´s result
	return hr;
}

/**
 * Method that close the handle of the cursor of the open queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQCloseCursorHandle
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQCloseCursorHandle
  (JNIEnv *env, jobject _this)
{
	// Result
	HRESULT       hr = MQ_OK;

	// Recovery the handles of the cursor
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);				
		jfieldID field = env->GetFieldID(clazz, "hCursor", "I");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		unsigned long hCursor = (unsigned long)env->GetIntField(_this, field);

	//  Close the cursor´s handle, if it´s open
	if( hCursor != 0 ){
		hr = MQCloseCursor((QUEUEHANDLE)hCursor);

		// Ok? Handle=0
		if( !FAILED(hr) )
			env->SetIntField(_this, field, (jint)0);
	}

	// Returns operation´s result
	return hr;
}

/**
 * Method that returns a string property for a queue of a Microsoft® Message Queuing Server. 
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jint) jintPropertyID: Id of the property to recovery
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQGetStringQueueProperty
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQGetStringQueueProperty
  (JNIEnv *env, jobject _this, jint jintPropertyID)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 1;	// PROPERTYID

	// Queue properties structure
	MQQUEUEPROPS  QueueProps;							// Queue properties structure
	QUEUEPROPID	  aQueuePropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aQueuePropVar[NUMBEROFPROPERTIES];	// Array of value types of properties
	HRESULT       aQueueStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Recovery the queue format name
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "queueFormatName", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		jstring jstrQueueFormatName = (jstring)env->GetObjectField(_this, field);
		// Recovery queue format name
		const jchar* pjcharQueueFormatName = env->GetStringChars(jstrQueueFormatName,NULL);
		if (pjcharQueueFormatName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

	// Establishing queue properties
		// ----------------------------
		// 1 - Property to recovery
		// ----------------------------
		aQueuePropId[cPropId] = (unsigned long)jintPropertyID;
		aQueuePropVar[cPropId].vt = VT_NULL;	
		cPropId++; // Increments counter

	// Initializing MQQUEUEPROPS properties struture for recovery queue property
	QueueProps.cProp = cPropId;               // Number of properties
	QueueProps.aPropID = aQueuePropId;        // Properties identifiers
	QueueProps.aPropVar = aQueuePropVar;	  // Properties values
	QueueProps.aStatus = aQueueStatus;        // Pointers to returns state of properties

	// MQGetQueueProperties invocation for retrive queue property information. If success then set the internal queue string property synchronized object
	hr = MQGetQueueProperties(
					(LPWSTR)pjcharQueueFormatName,  // Queue format name
                    &QueueProps);					// Request properties structure

	// Invoked correctly?
	if ( !FAILED(hr) )
	{
		// Save internal property
		jclass clazzField = env->GetObjectClass(_this);				
		jfieldID fieldId = env->GetFieldID(clazzField, "__internal_queue_string_property__", "Ljava/lang/String;");
		if (fieldId == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		jstring newValue = env->NewString( (const jchar*)aQueuePropVar[0].pwszVal, wcslen((const wchar_t*)aQueuePropVar[0].pwszVal ) );
		if (newValue == NULL) {
			hr = MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources			
		}
		else
			env->SetObjectField(_this, fieldId, newValue);
	}

	// Delete pointers
	env->ReleaseStringChars(jstrQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that returns a integer property for a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jint) jintPropertyID: Id of the property to recovery
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQGetIntQueueProperty
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQGetIntQueueProperty
  (JNIEnv *env, jobject _this, jint jintPropertyID)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 1;	// PROPERTYID

	// Queue properties structure
	MQQUEUEPROPS  QueueProps;							// Queue properties structure
	QUEUEPROPID	  aQueuePropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aQueuePropVar[NUMBEROFPROPERTIES];	// Array of value types of properties
	HRESULT       aQueueStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Recovery the queue format name
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "queueFormatName", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		jstring jstrQueueFormatName = (jstring)env->GetObjectField(_this, field);
		// Recovery queue format name
		const jchar* pjcharQueueFormatName = env->GetStringChars(jstrQueueFormatName,NULL);
		if (pjcharQueueFormatName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

	// Establishing queue properties
		// ----------------------------
		// 1 - Property to recovery
		// ----------------------------
		aQueuePropId[cPropId] = (unsigned long)jintPropertyID;
		aQueuePropVar[cPropId].vt = VT_NULL;	
		cPropId++; // Increments counter


	// Initializing MQQUEUEPROPS properties struture for recovery queue property
	QueueProps.cProp = cPropId;               // Number of properties
	QueueProps.aPropID = aQueuePropId;        // Properties identifiers
	QueueProps.aPropVar = aQueuePropVar;	  // Properties values
	QueueProps.aStatus = aQueueStatus;        // Pointers to returns state of properties

	// MQGetQueueProperties invocation for retrive queue property information. If success then set the internal queue string property synchronized object
	hr = MQGetQueueProperties(
					(LPWSTR)pjcharQueueFormatName,  // Queue format name
                    &QueueProps);					// Request properties structure

	// Invoked correctly?
	if ( !FAILED(hr) )
	{
		// Save internal property
		jclass clazzField = env->GetObjectClass(_this);				
		jfieldID fieldId = env->GetFieldID(clazzField, "__internal_queue_int_property__", "I");
		if (fieldId == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		env->SetIntField(_this, fieldId, (jint)aQueuePropVar[0].bVal);
	}

	// Delete pointers
	env->ReleaseStringChars(jstrQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that sets a string property for a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jint) jintPropertyID: Id of the property to set
 *	[IN](jstring) jszNewValue: Value to set
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQSetQueueProperty
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQSetQueueProperty
  (JNIEnv *env, jobject _this, jint jintPropertyID, jstring jszNewValue)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 1;	// PROPERTYID

	// Queue properties structure
	MQQUEUEPROPS  QueueProps;							// Queue properties structure
	QUEUEPROPID	  aQueuePropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	MQPROPVARIANT aQueuePropVar[NUMBEROFPROPERTIES];	// Array of value types of properties
	HRESULT       aQueueStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Validate all passed params
	const jchar* pjcharNewValue = env->GetStringChars(jszNewValue,NULL);
	if (pjcharNewValue == NULL)
	{
		return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
	}

	// Recovery the queue format name
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "queueFormatName", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		jstring jstrQueueFormatName = (jstring)env->GetObjectField(_this, field);
		// Recovery queue format name
		const jchar* pjcharQueueFormatName = env->GetStringChars(jstrQueueFormatName,NULL);
		if (pjcharQueueFormatName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

	// Establishing queue properties
		// ----------------------------
		// 1 - Property to recovery
		// ----------------------------
		aQueuePropId[cPropId] = (unsigned long)(jintPropertyID & 0x00000000FFFFFFFFL);
		aQueuePropVar[cPropId].vt = VT_LPWSTR;	
		aQueuePropVar[cPropId].pwszVal = (LPWSTR)pjcharNewValue;
		cPropId++; // Increments counter

	// Initializing MQQUEUEPROPS properties struture for recovery queue property
	QueueProps.cProp = cPropId;               // Number of properties
	QueueProps.aPropID = aQueuePropId;        // Properties identifiers
	QueueProps.aPropVar = aQueuePropVar;      // Properties values
	QueueProps.aStatus = aQueueStatus;        // Pointers to returns state of properties

	// MQSetQueueProperties invocation for set queue property information
	hr = MQSetQueueProperties((LPWSTR)pjcharQueueFormatName, &QueueProps);

	// Delete pointers
	env->ReleaseStringChars(jszNewValue,pjcharNewValue);
	env->ReleaseStringChars(jstrQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that delete a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQDeleteQueue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQDeleteQueue
  (JNIEnv *env, jobject _this)
{
	// Result
	HRESULT hr = MQ_OK;

	// Recovery the queue format name
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "queueFormatName", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		jstring jstrQueueFormatName = (jstring)env->GetObjectField(_this, field);
		// Recovery queue format name
		const jchar* pjcharQueueFormatName = env->GetStringChars(jstrQueueFormatName,NULL);
		if (pjcharQueueFormatName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

	// MQDeleteQueue invocation for delete the queue
	hr = MQDeleteQueue( (LPWSTR)pjcharQueueFormatName );

	// Delete pointers
	env->ReleaseStringChars(jstrQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that open a handle for a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jintAccessMode) jintAccessMode: Queue access mode
 *	[IN](jint) jintShareMode: Queue share mode
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQOpenQueue
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQOpenQueue
  (JNIEnv *env, jobject _this, jint jintAccessMode, jint jintShareMode)
{
	// Result
	HRESULT hr = MQ_OK;

	// Queue handle
	QUEUEHANDLE hQueue;

	// Recovery the queue format name
		// Recovery property
		jclass clazz = env->GetObjectClass(_this);
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "queueFormatName", "Ljava/lang/String;");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		jstring jstrQueueFormatName = (jstring)env->GetObjectField(_this, field);
		// Recovery queue format name
		const jchar* pjcharQueueFormatName = env->GetStringChars(jstrQueueFormatName,NULL);
		if (pjcharQueueFormatName == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
	
	// MQOpenQueue invocation for open the queue
	hr = MQOpenQueue(
				 (LPWSTR)pjcharQueueFormatName,     // Queue format name
				 (DWORD)jintAccessMode,				// Access mode
				 (DWORD)jintShareMode,  			// Share Mode
				 &hQueue							// OUT: Handle of the queue opens
				 );
	
	// Invoked correctly?
	if( !FAILED(hr) )
	{
		//Recupero el handle de la cola
			// Recovery property
			jclass clazz = env->GetObjectClass(_this);				
			jfieldID field = env->GetFieldID(clazz, "hQueue", "I");
			if (field == NULL) {
				return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
			}
			env->SetIntField(_this, field, (jint)hQueue );
	}

	//  Delete pointers
	env->ReleaseStringChars(jstrQueueFormatName,pjcharQueueFormatName);

	// Returns operation´s result
	return hr;
}

/**
 * Method that delete all messages in a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQPurgeQueue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQPurgeQueue
  (JNIEnv *env, jobject _this)
{
	// Result
	HRESULT       hr = MQ_OK;

	// Retrieve queue handle
		// Recovery the property
		jclass clazz = env->GetObjectClass(_this);		
		// Recovery field reference
		jfieldID field = env->GetFieldID(clazz, "hQueue", "I");
		if (field == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		long hQueueLong = (jint)env->GetIntField(_this, field) & 0x00000000FFFFFFFFL;
		QUEUEHANDLE hQueue = (QUEUEHANDLE)((jint)env->GetIntField(_this, field) & 0x00000000FFFFFFFFL); // Queue handle

	// MQPurgeQueue invocation for purge the queue. Only if queue is open
	if( hQueue != 0 )
		hr = MQPurgeQueue((QUEUEHANDLE)hQueue);

	// Returns operation´s result
	return hr;
}

/**
 * Method that read a message from a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jint) jintActionType: Read action type
 *	[IN](jint) jintTransactionMode: Read transaction mode
 *	[OUT](jobject) jobjectQueueMessage: Message read from the queue
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQReadQueue
 * Signature: (IIJLnet/sf/jmsmq/MSMQMessage;)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQReadQueue
  (JNIEnv *env, jobject _this, jint jintActionType, jint jintTransactionMode, jlong jlongTimeOut, jobject jobjectQueueMessage)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 6;	// PROPID_M_LABEL_LEN; PROPID_M_LABEL; 
										// PROPID_M_BODY_SIZE; PROPID_M_BODY_TYPE; PROPID_M_BODY;
										// PROPID_M_XACTID;

	// Queue properties structure
	MQMSGPROPS	  MsgProps;								// Message properties structure
	MSGPROPID	  aMsgPropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	PROPVARIANT   aMsgPropVar[NUMBEROFPROPERTIES];		// Array of value types of properties
	HRESULT       aMsgStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Read action
	DWORD dwAction = (DWORD)jintActionType;

	// Retrieve queue handle
	jclass clazz = env->GetObjectClass(_this);
		// Recovery property
		jfieldID fieldHandle = env->GetFieldID(clazz, "hQueue", "I");
		// Recovery field reference
		if (fieldHandle == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		unsigned long hQueue = (unsigned long)env->GetIntField(_this, fieldHandle);

		// Validate that queue is opened. If queue is closed then returns an error 
		if (hQueue == 0 )
		{
			return MQ_ERROR_INVALID_HANDLE; // Queue is not open
		}

	//Retrieve cursor handle	
		// Recovery property
		jfieldID fieldCursor = env->GetFieldID(clazz, "hCursor", "I");
		// Recovery field reference
		if (fieldCursor == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		unsigned long hCursor = (unsigned long)env->GetIntField(_this, fieldCursor);

		// Read operation timeout
		unsigned long MQ_TIMEOUT = (unsigned long)jlongTimeOut;

	// Establishing message properties
		// ----------------------------
		// 1 - Message Label
		// ----------------------------
			// Label length property
			aMsgPropId[cPropId] = PROPID_M_LABEL_LEN;
			aMsgPropVar[cPropId].vt = VT_UI4;
			aMsgPropVar[cPropId].ulVal = MQ_MAX_MSG_LABEL_LEN;
			cPropId++; // Increments counter

			// Label property
			WCHAR wszMsgLabel[ MQ_MAX_MSG_LABEL_LEN ];
			memset(wszMsgLabel, 0, MQ_MAX_MSG_LABEL_LEN);
			aMsgPropId[cPropId] = PROPID_M_LABEL;
			aMsgPropVar[cPropId].vt = VT_LPWSTR;
			aMsgPropVar[cPropId].pwszVal = wszMsgLabel;
			cPropId++; // Increments counter			

		// ----------------------------
		// 2 - Message Body
		// ----------------------------
		// Reserve memory for message body
		ULONG ulBufferSize = 0;						// The buffer is adjusted in second invocation
		char* szBodyBuffer = (char*)malloc(ulBufferSize); 
		if (szBodyBuffer == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;
		}
		memset(szBodyBuffer, 0, ulBufferSize);

			// Body size property
			aMsgPropId[cPropId] = PROPID_M_BODY_SIZE;
			aMsgPropVar[cPropId].vt = VT_NULL;
			cPropId++; // Increments counter

			// Body type property
			aMsgPropId[cPropId] = PROPID_M_BODY_TYPE;
			aMsgPropVar[cPropId].vt = VT_NULL;
			cPropId++; // Increments counter

			// Body property
			aMsgPropId[cPropId] = PROPID_M_BODY;
			aMsgPropVar[cPropId].vt = VT_VECTOR | VT_UI1;
			aMsgPropVar[cPropId].caub.pElems = (LPBYTE)szBodyBuffer;		
			aMsgPropVar[cPropId].caub.cElems = ulBufferSize;
			
			cPropId++; // Increments counter

		// ----------------------------
		// 3 - Message transaction mode
		// ----------------------------
		aMsgPropId[cPropId] = PROPID_M_XACTID;          // Transaction Id
		aMsgPropVar[cPropId].vt = VT_UI1 | VT_VECTOR;
		MSGQUEUEOBJECTID xid;                              
		aMsgPropVar[cPropId].caub.pElems = (PUCHAR)&xid;
		aMsgPropVar[cPropId].caub.cElems = sizeof(MSGQUEUEOBJECTID);
		cPropId++;  // Increments counter


	// Initializing MQMSGPROPS properties struture for recovery message
	MsgProps.cProp = cPropId;             // Number of properties
	MsgProps.aPropID = aMsgPropId;        // Properties identifiers
	MsgProps.aPropVar = aMsgPropVar;	  // Properties values
	MsgProps.aStatus = aMsgStatus;        // Pointers to returns state of properties

	// ITransaction flag from transaction mode
	ITransaction* eTransaction = MQ_NO_TRANSACTION;
	if( (int)jintTransactionMode == (int)MQ_MTS_TRANSACTION )
		eTransaction = MQ_MTS_TRANSACTION;
	else if( (int)jintTransactionMode == (int)MQ_XA_TRANSACTION )
		eTransaction = MQ_XA_TRANSACTION;
	else if( (int)jintTransactionMode == (int)MQ_SINGLE_MESSAGE )
		eTransaction = MQ_SINGLE_MESSAGE;


	// If not create, then create queue cursor handle. MQCreateCursor invocation for create the queue cursor
	if( hCursor == 0 ){
		HANDLE hNewCursor;
		hr = MQCreateCursor(
						  (QUEUEHANDLE)hQueue,        // Queue handle
						  &hNewCursor                 // OUT: New cursor handle
						  );
		// Invoked correctly?
		if( FAILED(hr) ) 
		{
			return hr;	// Error. No cursor
		}
		else{
			// Save cursor handle
				// Recovery property
				jclass clazz = env->GetObjectClass(_this);
				// Recovery field reference
				jfieldID field = env->GetFieldID(clazz, "hCursor", "I");
				if (field == NULL) {
					return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
				}
				env->SetIntField(_this, field, (jint)hNewCursor );
				hCursor = (unsigned long)hNewCursor;
		}
	}

	// MQReceiveMessage invocation for read message from the queue. 
	for( int i=0; i<=1; i++ ){ // Retry a second time for recovery message body buffer
		hr = MQReceiveMessage(
						(QUEUEHANDLE)hQueue,				// Queue handle
						(DWORD)MQ_TIMEOUT,					// Read operation timeout
						dwAction,							// Read action
						&MsgProps,							// Message property structure
						NULL, NULL,							// OVERLAPPED structure (Asynchronous request)
						(hCursor==0)?NULL:(HANDLE)hCursor,	// Read cursor (MQ_ACTION_PEEK_NEXT)
						eTransaction						// Transaction flag
						);

		// Invoked correctly?
		if( !FAILED(hr) )
		{
			// Save the message
			jclass clazzMsg = env->GetObjectClass(jobjectQueueMessage);				
				//LABEL
				jfieldID fieldMsgLabel = env->GetFieldID(clazzMsg, "messageLabel", "Ljava/lang/String;");
				if (fieldMsgLabel == NULL) {
					return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
				}
				jstring jstrMsgLabel = env->NewString((const jchar*)wszMsgLabel,wcslen(wszMsgLabel));
				env->SetObjectField(jobjectQueueMessage, fieldMsgLabel, (jobject)jstrMsgLabel);


				//BODY
				// It´s necesary detect the message´s code page (ANSI ó UNICODE). So, lookup if the extract message 
				// length looks a ANSI character (1 byte) or a UNICODE character (2 byte)
				bool bUNICODE = (aMsgPropVar[2].ulVal >= 2 && strlen(szBodyBuffer) == 1);
				if( bUNICODE ){ //UNICODE
					// Message length recovery
					ulBufferSize = ((aMsgPropVar[2].ulVal==0)?0:aMsgPropVar[2].ulVal/sizeof(WCHAR));

					// Save message body into the param message object passed
					jfieldID fieldMsgBody = env->GetFieldID(clazzMsg, "messageBody", "Ljava/lang/String;");
					if (fieldMsgBody == NULL) {
						return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
					}		
					jstring jstrMsgBody = env->NewString((const jchar*)szBodyBuffer,(jsize)ulBufferSize);
					env->SetObjectField(jobjectQueueMessage, fieldMsgBody, (jobject)jstrMsgBody);
				}
				else{	//ANSI
					// Message length recovery
					ulBufferSize = aMsgPropVar[2].ulVal;

					// Save into the param message object passed
					jfieldID fieldMsgBody = env->GetFieldID(clazzMsg, "messageBody", "Ljava/lang/String;");
					if (fieldMsgBody == NULL) {
						return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
					}

					//Readjust buffer size to the length recovery from invocation
					char* szBodyBufferAux = (char*)malloc(ulBufferSize); 
					if (szBodyBufferAux == NULL)
					{
						return MQ_ERROR_INSUFFICIENT_RESOURCES;
					}
					memset(szBodyBufferAux, 0, ulBufferSize+1);		//ulBufferSize + \0
					for( int i=0; i<ulBufferSize; i++ )
						szBodyBufferAux[i]=szBodyBuffer[i];

					// Save message body into the param message object passed
					jstring jstrMsgBody = env->NewStringUTF(szBodyBufferAux);				
					env->SetObjectField(jobjectQueueMessage, fieldMsgBody, (jobject)jstrMsgBody);

					// Free unused memory
					free(szBodyBufferAux);
				}

				// Is transactional message?
				bool bMsgTransactional = (xid.Uniquifier!=0);
				jfieldID fieldMsgXAct = env->GetFieldID(clazzMsg, "transactionalMessage", "Z");
				if (fieldMsgXAct == NULL) {
					return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
				}
				env->SetBooleanField(jobjectQueueMessage, fieldMsgXAct, (jboolean)bMsgTransactional);
		}
		else{
			// Buffer is too small?
			if (hr == MQ_ERROR_BUFFER_OVERFLOW)
			{
				// Readjust buffer size
				ulBufferSize = aMsgPropVar[2].ulVal*sizeof(UCHAR);
				szBodyBuffer = (char*)realloc(szBodyBuffer, ulBufferSize);
				if (szBodyBuffer == NULL)
				{
					return MQ_ERROR_INSUFFICIENT_RESOURCES;
				}
				memset(szBodyBuffer, 0, ulBufferSize);
				aMsgPropVar[4].caub.pElems = (LPBYTE)szBodyBuffer;
				aMsgPropVar[4].caub.cElems = ulBufferSize;

				// Correcting action for retrieve actual message
				if( dwAction != MQ_ACTION_RECEIVE)
					dwAction = MQ_ACTION_PEEK_CURRENT;

				// I have the necesary memory size for adjust buffer and read message entirely; extract message
				continue;
			}
		}

		// Message read ok
		break;
	}

	// Free memory
	free(szBodyBuffer);

	// Returns operation´s result
	return hr;
}

/**
 * Method that send a message to a queue of a Microsoft® Message Queuing Server.
 *
 *	[IN](JNIEnv *) env: JNI' environment
 *	[IN](jobject) _this: Reference of the class that invokes JNI
 *	[IN](jobject) jobjectQueueMessage: Message to send
 *	[IN](jint) jintTransactionMode: Send transaction mode
 *	[IN](jboolean) jbooleanAsUnicode: Send message body with Unicode code page
 *  [RETURN] HRESULT Information or error code returns for the function (http://windowssdk.msdn.microsoft.com/library/default.asp?url=/library/en-us/msmq/html/174399f2-f058-4410-82ff-1e578427f668.asp)
 */
/*
 * Class:     net_sf_jmsmq_MSMQQueue
 * Method:    jniMQSendQueue
 * Signature: (Lnet/sf/jmsmq/MSMQMessage;IZ)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueue_jniMQSendQueue
  (JNIEnv *env, jobject _this, jobject jobjectQueueMessage, jint jintTransactionMode, jboolean jbooleanAsUnicode)
{
	// Number of properties used in invocation
	const int NUMBEROFPROPERTIES = 3;	// PROPID_M_LABEL; PROPID_M_BODY_TYPE; PROPID_M_BODY;

	// Queue properties structure
	MQMSGPROPS	  MsgProps;								// Message properties structure
	MSGPROPID	  aMsgPropId[NUMBEROFPROPERTIES];		// Array of properties identifiers
	PROPVARIANT   aMsgPropVar[NUMBEROFPROPERTIES];		// Array of value types of properties
	HRESULT       aMsgStatus[NUMBEROFPROPERTIES];		// Array for returns the state of the properties after invocation
	DWORD         cPropId = 0;							// Properties counter

	// Result
	HRESULT       hr = MQ_OK;

	// Retrieve queue handle
	jclass clazz = env->GetObjectClass(_this);
		// Recovery property
		jfieldID fieldHandle = env->GetFieldID(clazz, "hQueue", "I");
		// Recovery field reference
		if (fieldHandle == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		// Recovery a jstring reference from the field
		unsigned long hQueue = (unsigned long)env->GetIntField(_this, fieldHandle);

		// Validate that queue is opened. If queue is closed then returns an error 
		if (hQueue == 0 )
		{
			return MQ_ERROR_INVALID_HANDLE; // Queue is not open
		}

	// Send as unicode code page?
	bool MQ_SEND_AS_UNICODE = (bool)jbooleanAsUnicode;


	// Retrieve message information for set invocation properties
	jclass clazzMsg = env->GetObjectClass(jobjectQueueMessage);
		//1.- LABEL
		jfieldID fieldMsgLabel = env->GetFieldID(clazzMsg, "messageLabel", "Ljava/lang/String;");
		if (fieldMsgLabel == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		jstring jstrMsgLabel = (jstring)env->GetObjectField(jobjectQueueMessage, fieldMsgLabel);
		const jchar* lpwstrMsgLabel = env->GetStringChars(jstrMsgLabel,NULL);
		if (lpwstrMsgLabel == NULL)
		{
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}

		//2.- BODY
		jfieldID fieldMsgBody = env->GetFieldID(clazzMsg, "messageBody", "Ljava/lang/String;");
		if (fieldMsgBody == NULL) {
			return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
		}
		jstring jstrMsgBody = (jstring)env->GetObjectField(jobjectQueueMessage, fieldMsgBody);
		jsize jsizeMsgBody;
		const jchar* lpwszMsgBody;
		const char* szMsgBody;
		if( MQ_SEND_AS_UNICODE ){
			//UNICODE
			jsizeMsgBody = env->GetStringLength(jstrMsgBody);
			lpwszMsgBody = env->GetStringChars(jstrMsgBody,NULL);
			if (lpwszMsgBody == NULL)
			{
				return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
			}
		}
		else{
			//ANSI
			jsizeMsgBody = env->GetStringUTFLength(jstrMsgBody);
			szMsgBody = env->GetStringUTFChars(jstrMsgBody,NULL);
			if (szMsgBody == NULL)
			{
				return MQ_ERROR_INSUFFICIENT_RESOURCES;		// Insufficient resources
			}
		}

	
	// To ensure call no crash, verify that pointers are valid
	if ( lpwstrMsgLabel == NULL || (lpwszMsgBody == NULL && szMsgBody == NULL) )
	{
		return MQ_ERROR_INVALID_PARAMETER;
	}

	// Send message properties
		// ----------------------------
		// 1 - Message Label
		// ----------------------------
		aMsgPropId[cPropId] = PROPID_M_LABEL;
		aMsgPropVar[cPropId].vt = VT_LPWSTR;
		aMsgPropVar[cPropId].pwszVal = (LPWSTR)lpwstrMsgLabel;
		cPropId++; // Increments counter

		// ----------------------------
		// 2 - Message Body
		// ----------------------------
			// Message body type
			aMsgPropId[cPropId] = PROPID_M_BODY_TYPE;
			aMsgPropVar[cPropId].vt = VT_UI4;
			aMsgPropVar[cPropId].ulVal = VT_BSTR;
			cPropId++; // Increments counter

			// Message body
			aMsgPropId[cPropId] = PROPID_M_BODY;
			aMsgPropVar[cPropId].vt = VT_VECTOR | VT_UI1;
			if( MQ_SEND_AS_UNICODE ){
				aMsgPropVar[cPropId].caub.pElems = (UCHAR*)lpwszMsgBody;
				aMsgPropVar[cPropId].caub.cElems = jsizeMsgBody*sizeof(WCHAR);
			}
			else{
				aMsgPropVar[cPropId].caub.pElems = (LPBYTE)szMsgBody;
				aMsgPropVar[cPropId].caub.cElems = strlen(szMsgBody);
			}
			cPropId++; // Increments counter

	// Initializing MQMSGPROPS properties struture for recovery message
	MsgProps.cProp = cPropId;             // Number of properties
	MsgProps.aPropID = aMsgPropId;        // Properties identifiers
	MsgProps.aPropVar = aMsgPropVar;	  // Properties values
	MsgProps.aStatus = aMsgStatus;        // Pointers to returns state of properties

	// ITransaction flag from transaction mode
	ITransaction* eTransaction = MQ_NO_TRANSACTION;
	if( (int)jintTransactionMode == (int)MQ_MTS_TRANSACTION )
		eTransaction = MQ_MTS_TRANSACTION;
	else if( (int)jintTransactionMode == (int)MQ_XA_TRANSACTION )
		eTransaction = MQ_XA_TRANSACTION;
	else if( (int)jintTransactionMode == (int)MQ_SINGLE_MESSAGE )
		eTransaction = MQ_SINGLE_MESSAGE;


	// MQSendMessage invocation for send message to the queue. 
	hr = MQSendMessage(
					(QUEUEHANDLE)hQueue,				// Queue handle
					&MsgProps,							// Message property structure
					eTransaction						// Transaction flag
					);

	//  Delete pointers
	env->ReleaseStringChars(jstrMsgLabel,lpwstrMsgLabel);
	if( MQ_SEND_AS_UNICODE )
		env->ReleaseStringChars(jstrMsgBody,lpwszMsgBody);
	else
		env->ReleaseStringUTFChars(jstrMsgBody,szMsgBody);

	// Returns operation´s result
	return hr;
}
