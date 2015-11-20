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

/* ***************** */
/* PACKAGE			 */
/* ***************** */
package net.sf.jmsmq;

/* ***************** */
/* IMPORTS			 */
/* ***************** */

/**
 * Class of constants of the Win32 MSMQ Api for native implementation. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author Jorge Ruesga
 */
public final class MSMQConstants {

	/**
	 * Internal variables
	 */
        //Protected constants of access type concatenation
        protected static final String MQ_DIRECT_OS_DEF = "DIRECT=OS:"; //$NON-NLS-1$
        protected static final String MQ_DIRECT_TCP_DEF = "DIRECT=TCP:"; //$NON-NLS-1$

        //Protected constants of queue type concatenation
        protected static final String MQ_TYPE_PRIVATE_DEF = "\\private$\\"; //$NON-NLS-1$
        protected static final String MQ_TYPE_PUBLIC_DEF = "\\"; //$NON-NLS-1$


//** -------------------------------------------------------------- ** \\
//                              MAX_LEN                                \\
//** -------------------------------------------------------------- ** \\

        //MSMQ properties maxlength public constants (HRESULT)
	        /**
	         * MQ_MAX_Q_NAME_LEN [124]. Queue name maxlength
	         */
        	public static final int MQ_MAX_Q_NAME_LEN		= 124;

        	/**
	         * MQ_MAX_Q_LABEL_LEN [124]. Queue label maxlength
	         */
        	public static final int MQ_MAX_Q_LABEL_LEN		= 124;

        	/**
	         * MQ_MAX_MSG_LABEL_LEN [250]. Message label maxlength
	         */
        	public static final int MQ_MAX_MSG_LABEL_LEN	= 250;


//** -------------------------------------------------------------- ** \\
//                              HRESULT                                \\
//** -------------------------------------------------------------- ** \\

        //Public error constants (HRESULT)
            /**
             * MQ_OK [0x00000000]
             */
            public static final int MQ_OK = 0x00000000;

            /**
             * MQ_ERROR [0xC00E0001]
             */
            public static final int MQ_ERROR = 0xC00E0001;

            /**
             * MQ_ERROR_ACCESS_DENIED [0xC00E0025]
             */
            public static final int MQ_ERROR_ACCESS_DENIED = 0xC00E0025;

            /**
             * MQ_ERROR_BAD_SECURITY_CONTEXT [0xC00E0035]
             */
            public static final int MQ_ERROR_BAD_SECURITY_CONTEXT = 0xC00E0035;

            /**
             * MQ_ERROR_BAD_XML_FORMAT [0xC00E0092]
             */
            public static final int MQ_ERROR_BAD_XML_FORMAT = 0xC00E0092;

            /**
             * MQ_ERROR_BUFFER_OVERFLOW [0xC00E001A]
             */
            public static final int MQ_ERROR_BUFFER_OVERFLOW = 0xC00E001A;

            /**
             * MQ_ERROR_CANNOT_CREATE_CERT_STORE [0xC00E006F]
             */
            public static final int MQ_ERROR_CANNOT_CREATE_CERT_STORE = 0xC00E006F;

            /**
             * MQ_ERROR_CANNOT_CREATE_HASH_EX [0xC00E0081]
             */
            public static final int MQ_ERROR_CANNOT_CREATE_HASH_EX = 0xC00E0081;

            /**
             * MQ_ERROR_CANNOT_CREATE_PSC_OBJECTS [0xC00E0095]
             */
            public static final int MQ_ERROR_CANNOT_CREATE_PSC_OBJECTS = 0xC00E0095;

            /**
             * MQ_ERROR_CANNOT_DELETE_PSC_OBJECTS [0xC00E0083]
             */
            public static final int MQ_ERROR_CANNOT_DELETE_PSC_OBJECTS = 0xC00E0083;

            /**
             * MQ_ERROR_CANNOT_LOAD_MQAD [0xC00E0085]
             */
            public static final int MQ_ERROR_CANNOT_LOAD_MQAD = 0xC00E0085;

            /**
             * MQ_ERROR_CANNOT_SIGN_DATA_EX [0xC00E0080]
             */
            public static final int MQ_ERROR_CANNOT_SIGN_DATA_EX = 0xC00E0080;

            /**
             * MQ_ERROR_CANNOT_IMPERSONATE_CLIENT [0xC00E0024]
             */
            public static final int MQ_ERROR_CANNOT_IMPERSONATE_CLIENT = 0xC00E0024;

            /**
             * MQ_ERROR_CANNOT_OPEN_CERT_STORE [0xC00E0070]
             */
            public static final int MQ_ERROR_CANNOT_OPEN_CERT_STORE = 0xC00E0070;

            /**
             * MQ_ERROR_CANNOT_SET_CRYPTO_SEC_DESCR [0xC00E006C]
             */
            public static final int MQ_ERROR_CANNOT_SET_CRYPTO_SEC_DESCR = 0xC00E006C;

            /**
             * MQ_ERROR_CANNOT_UPDATE_PSC_OBJECTS [0xC00E0096]
             */
            public static final int MQ_ERROR_CANNOT_UPDATE_PSC_OBJECTS = 0xC00E0096;

            /**
             * MQ_ERROR_CANT_RESOLVE_SITES [0xC00E0089]
             */
            public static final int MQ_ERROR_CANT_RESOLVE_SITES = 0xC00E0089;

            /**
             * MQ_ERROR_CERTIFICATE_NOT_PROVIDED [0xC00E006D]
             */
            public static final int MQ_ERROR_CERTIFICATE_NOT_PROVIDED = 0xC00E006D;

            /**
             * MQ_ERROR_COMPUTER_DOES_NOT_SUPPORT_ENCRYPTION [0xC00E0033]
             */
            public static final int MQ_ERROR_COMPUTER_DOES_NOT_SUPPORT_ENCRYPTION = 0xC00E0033;

            /**
             * MQ_ERROR_CORRUPTED_INTERNAL_CERTIFICATE [0xC00E002D]
             */
            public static final int MQ_ERROR_CORRUPTED_INTERNAL_CERTIFICATE = 0xC00E002D;

            /**
             * MQ_ERROR_CORRUPTED_PERSONAL_CERT_STORE [0xC00E0031]
             */
            public static final int MQ_ERROR_CORRUPTED_PERSONAL_CERT_STORE = 0xC00E0031;

            /**
             * MQ_CORRUPTED_QUEUE_WAS_DELETED [0xC00E0068]
             */
            public static final int MQ_CORRUPTED_QUEUE_WAS_DELETED = 0xC00E0068;

            /**
             * MQ_ERROR_CORRUPTED_SECURITY_DATA [0xC00E0030]
             */
            public static final int MQ_ERROR_CORRUPTED_SECURITY_DATA = 0xC00E0030;

            /**
             * MQ_ERROR_COULD_NOT_GET_ACCOUNT_INFO [0xC00E0037]
             */
            public static final int MQ_ERROR_COULD_NOT_GET_ACCOUNT_INFO = 0xC00E0037;

            /**
             * MQ_ERROR_COULD_NOT_GET_USER_SID [0xC00E0036]
             */
            public static final int MQ_ERROR_COULD_NOT_GET_USER_SID = 0xC00E0036;

            /**
             * MQ_ERROR_DELETE_CN_IN_USE [0xC00E0048]
             */
            public static final int MQ_ERROR_DELETE_CN_IN_USE = 0xC00E0048;

            /**
             * MQ_ERROR_DEPEND_WKS_LICENSE_OVERFLOW [0xC00E0067]
             */
            public static final int MQ_ERROR_DEPEND_WKS_LICENSE_OVERFLOW = 0xC00E0067;

            /**
             * MQ_ERROR_DS_BIND_ROOT_FOREST [0xC00E008F]
             */
            public static final int MQ_ERROR_DS_BIND_ROOT_FOREST = 0xC00E008F;

            /**
             * MQ_ERROR_DS_ERROR [0xC00E0043]
             */
            public static final int MQ_ERROR_DS_ERROR = 0xC00E0043;

            /**
             * MQ_ERROR_DS_IS_FULL [0xC00E0042]
             */
            public static final int MQ_ERROR_DS_IS_FULL = 0xC00E0042;

            /**
             * MQ_ERROR_DS_LOCAL_USER [0xC00E0090]
             */
            public static final int MQ_ERROR_DS_LOCAL_USER = 0xC00E0090;

            /**
             * MQ_ERROR_DTC_CONNECT [0xC00E004C]
             */
            public static final int MQ_ERROR_DTC_CONNECT = 0xC00E004C;

            /**
             * MQ_ERROR_ENCRYPTION_PROVIDER_NOT_SUPPORTED [0xC00E006B]
             */
            public static final int MQ_ERROR_ENCRYPTION_PROVIDER_NOT_SUPPORTED = 0xC00E006B;

            /**
             * MQ_ERROR_FAIL_VERIFY_SIGNATURE_EX [0xC00E0082]
             */
            public static final int MQ_ERROR_FAIL_VERIFY_SIGNATURE_EX = 0xC00E0082;

            /**
             * MQ_ERROR_FORMATNAME_BUFFER_TOO_SMALL [0xC00E001F]
             */
            public static final int MQ_ERROR_FORMATNAME_BUFFER_TOO_SMALL = 0xC00E001F;

            /**
             * MQ_ERROR_GC_NEEDED [0xC00E008E]
             */
            public static final int MQ_ERROR_GC_NEEDED = 0xC00E008E;

            /**
             * MQ_ERROR_ILLEGAL_CONTEXT [0xC00E005B]
             */
            public static final int MQ_ERROR_ILLEGAL_CONTEXT = 0xC00E005B;

            /**
             * MQ_ERROR_ILLEGAL_CURSOR_ACTION [0xC00E001C]
             */
            public static final int MQ_ERROR_ILLEGAL_CURSOR_ACTION = 0xC00E001C;

            /**
             * MQ_ERROR_ILLEGAL_ENTERPRISE_OPERATION [0xC00E0071]
             */
            public static final int MQ_ERROR_ILLEGAL_ENTERPRISE_OPERATION = 0xC00E0071;

            /**
             * MQ_ERROR_ILLEGAL_FORMATNAME [0xC00E001E]
             */
            public static final int MQ_ERROR_ILLEGAL_FORMATNAME = 0xC00E001E;

            /**
             * MQ_ERROR_ILLEGAL_MQCOLUMNS [0xC00E0038]
             */
            public static final int MQ_ERROR_ILLEGAL_MQCOLUMNS = 0xC00E0038;

            /**
             * MQ_ERROR_ILLEGAL_MQPRIVATEPROPS [0xC00E007B]
             */
            public static final int MQ_ERROR_ILLEGAL_MQPRIVATEPROPS = 0xC00E007B;

            /**
             * MQ_ERROR_ILLEGAL_MQQMPROPS [0xC00E0041]
             */
            public static final int MQ_ERROR_ILLEGAL_MQQMPROPS = 0xC00E0041;

            /**
             * MQ_ERROR_ILLEGAL_MQQUEUEPROPS [0xC00E003D]
             */
            public static final int MQ_ERROR_ILLEGAL_MQQUEUEPROPS = 0xC00E003D;

            /**
             * MQ_ERROR_ILLEGAL_OPERATION [0xC00E0064]
             */
            public static final int MQ_ERROR_ILLEGAL_OPERATION = 0xC00E0064;

            /**
             * MQ_ERROR_ILLEGAL_PROPERTY_SIZE [0xC00E003B]
             */
            public static final int MQ_ERROR_ILLEGAL_PROPERTY_SIZE = 0xC00E003B;

            /**
             * MQ_ERROR_ILLEGAL_PROPERTY_VALUE [0xC00E0018]
             */
            public static final int MQ_ERROR_ILLEGAL_PROPERTY_VALUE = 0xC00E0018;

            /**
             * MQ_ERROR_ILLEGAL_PROPERTY_VT [0xC00E0019]
             */
            public static final int MQ_ERROR_ILLEGAL_PROPERTY_VT = 0xC00E0019;

            /**
             * MQ_ERROR_ILLEGAL_PROPID [0xC00E0039]
             */
            public static final int MQ_ERROR_ILLEGAL_PROPID = 0xC00E0039;

            /**
             * MQ_ERROR_ILLEGAL_QUEUE_PATHNAME [0xC00E0014]
             */
            public static final int MQ_ERROR_ILLEGAL_QUEUE_PATHNAME = 0xC00E0014;

            /**
             * MQ_ERROR_ILLEGAL_RELATION [0xC00E003A]
             */
            public static final int MQ_ERROR_ILLEGAL_RELATION = 0xC00E003A;

            /**
             * MQ_ERROR_ILLEGAL_RESTRICTION_PROPID [0xC00E003C]
             */
            public static final int MQ_ERROR_ILLEGAL_RESTRICTION_PROPID = 0xC00E003C;

            /**
             * MQ_ERROR_ILLEGAL_SECURITY_DESCRIPTOR [0xC00E0021]
             */
            public static final int MQ_ERROR_ILLEGAL_SECURITY_DESCRIPTOR = 0xC00E0021;

            /**
             * MQ_ERROR_ILLEGAL_SORT [0xC00E0010]
             */
            public static final int MQ_ERROR_ILLEGAL_SORT = 0xC00E0010;

            /**
             * MQ_ERROR_ILLEGAL_USER [0xC00E0011]
             */
            public static final int MQ_ERROR_ILLEGAL_USER = 0xC00E0011;

            /**
             * MQ_ERROR_INSUFFICIENT_PROPERTIES [0xC00E003F]
             */
            public static final int MQ_ERROR_INSUFFICIENT_PROPERTIES = 0xC00E003F;

            /**
             * MQ_ERROR_INSUFFICIENT_RESOURCES [0xC00E0027]
             */
            public static final int MQ_ERROR_INSUFFICIENT_RESOURCES = 0xC00E0027;

            /**
             * MQ_ERROR_INTERNAL_USER_CERT_EXIST [0xC00E002E]
             */
            public static final int MQ_ERROR_INTERNAL_USER_CERT_EXIST = 0xC00E002E;

            /**
             * MQ_ERROR_INVALID_CERTIFICATE [0xC00E002C]
             */
            public static final int MQ_ERROR_INVALID_CERTIFICATE = 0xC00E002C;

            /**
             * MQ_ERROR_INVALID_HANDLE [0xC00E0007]
             */
            public static final int MQ_ERROR_INVALID_HANDLE = 0xC00E0007;

            /**
             * MQ_ERROR_INVALID_OWNER [0xC00E0044]
             */
            public static final int MQ_ERROR_INVALID_OWNER = 0xC00E0044;

            /**
             * MQ_ERROR_INVALID_PARAMETER [0xC00E0006]
             */
            public static final int MQ_ERROR_INVALID_PARAMETER = 0xC00E0006;

            /**
             * MQ_ERROR_IO_TIMEOUT [0xC00E001B]
             */
            public static final int MQ_ERROR_IO_TIMEOUT = 0xC00E001B;

            /**
             * MQ_ERROR_LABEL_TOO_LONG [0xC00E005D]
             */
            public static final int MQ_ERROR_LABEL_TOO_LONG = 0xC00E005D;

            /**
             * MQ_ERROR_LABEL_BUFFER_TOO_SMALL [0xC00E005E]
             */
            public static final int MQ_ERROR_LABEL_BUFFER_TOO_SMALL = 0xC00E005E;

            /**
             * MQ_ERROR_MACHINE_EXISTS [0xC00E0040]
             */
            public static final int MQ_ERROR_MACHINE_EXISTS = 0xC00E0040;

            /**
             * MQ_ERROR_MACHINE_NOT_FOUND [0xC00E000D]
             */
            public static final int MQ_ERROR_MACHINE_NOT_FOUND = 0xC00E000D;

            /**
             * MQ_ERROR_MESSAGE_ALREADY_RECEIVED [0xC00E001D]
             */
            public static final int MQ_ERROR_MESSAGE_ALREADY_RECEIVED = 0xC00E001D;

            /**
             * MQ_ERROR_MESSAGE_NOT_FOUND [0xC00E0088]
             */
            public static final int MQ_ERROR_MESSAGE_NOT_FOUND = 0xC00E0088;

            /**
             * MQ_ERROR_MESSAGE_STORAGE_FAILED [0xC00E002A]
             */
            public static final int MQ_ERROR_MESSAGE_STORAGE_FAILED = 0xC00E002A;

            /**
             * MQ_ERROR_MISSING_CONNECTOR_TYPE [0xC00E0055]
             */
            public static final int MQ_ERROR_MISSING_CONNECTOR_TYPE = 0xC00E0055;

            /**
             * MQ_ERROR_MQIS_SERVER_EMPTY [0xC00E005F]
             */
            public static final int MQ_ERROR_MQIS_SERVER_EMPTY = 0xC00E005F;

            /**
             * MQ_ERROR_MULTI_SORT_KEYS [0xC00E008D]
             */
            public static final int MQ_ERROR_MULTI_SORT_KEYS = 0xC00E008D;

            /**
             * MQ_ERROR_NO_DS [0xC00E0013]
             */
            public static final int MQ_ERROR_NO_DS = 0xC00E0013;

            /**
             * MQ_ERROR_NO_INTERNAL_USER_CERT [0xC00E002F]
             */
            public static final int MQ_ERROR_NO_INTERNAL_USER_CERT = 0xC00E002F;

            /**
             * MQ_ERROR_NO_MQUSER_OU [0xC00E0084]
             */
            public static final int MQ_ERROR_NO_MQUSER_OU = 0xC00E0084;

            /**
             * MQ_ERROR_NO_RESPONSE_FROM_OBJECT_SERVER [0xC00E0049]
             */
            public static final int MQ_ERROR_NO_RESPONSE_FROM_OBJECT_SERVER = 0xC00E0049;

            /**
             * MQ_ERROR_NOT_A_CORRECT_OBJECT_CLASS [0xC00E008C]
             */
            public static final int MQ_ERROR_NOT_A_CORRECT_OBJECT_CLASS = 0xC00E008C;

            /**
             * MQ_ERROR_NOT_SUPPORTED_BY_DEPENDENT_CLIENTS [0xC00E008A]
             */
            public static final int MQ_ERROR_NOT_SUPPORTED_BY_DEPENDENT_CLIENTS = 0xC00E008A;

            /**
             * MQ_ERROR_OBJECT_SERVER_NOT_AVAILABLE [0xC00E004A]
             */
            public static final int MQ_ERROR_OBJECT_SERVER_NOT_AVAILABLE = 0xC00E004A;

            /**
             * MQ_ERROR_OPERATION_CANCELLED [0xC00E0008]
             */
            public static final int MQ_ERROR_OPERATION_CANCELLED = 0xC00E0008;

            /**
             * MQ_ERROR_OPERATION_NOT_SUPPORTED_BY_REMOTE_COMPUTER [0xC00E008B]
             */
            public static final int MQ_ERROR_OPERATION_NOT_SUPPORTED_BY_REMOTE_COMPUTER = 0xC00E008B;

            /**
             * MQ_ERROR_PRIVILEGE_NOT_HELD [0xC00E0026]
             */
            public static final int MQ_ERROR_PRIVILEGE_NOT_HELD = 0xC00E0026;

            /**
             * MQ_ERROR_PROPERTIES_CONFLICT [0xC00E0087]
             */
            public static final int MQ_ERROR_PROPERTIES_CONFLICT = 0xC00E0087;

            /**
             * MQ_ERROR_PROPERTY [0xC00E0002]
             */
            public static final int MQ_ERROR_PROPERTY = 0xC00E0002;

            /**
             * MQ_ERROR_PROPERTY_NOTALLOWED [0xC00E003E]
             */
            public static final int MQ_ERROR_PROPERTY_NOTALLOWED = 0xC00E003E;

            /**
             * MQ_ERROR_PROV_NAME_BUFFER_TOO_SMALL [0xC00E0063]
             */
            public static final int MQ_ERROR_PROV_NAME_BUFFER_TOO_SMALL = 0xC00E0063;

            /**
             * MQ_ERROR_PUBLIC_KEY_DOES_NOT_EXIST [0xC00E007A]
             */
            public static final int MQ_ERROR_PUBLIC_KEY_DOES_NOT_EXIST = 0xC00E007A;

            /**
             * MQ_ERROR_PUBLIC_KEY_NOT_FOUND [0xC00E0079]
             */
            public static final int MQ_ERROR_PUBLIC_KEY_NOT_FOUND = 0xC00E0079;

            /**
             * MQ_ERROR_Q_ADS_PROPERTY_NOT_SUPPORTED [0xC00E0091]
             */
            public static final int MQ_ERROR_Q_ADS_PROPERTY_NOT_SUPPORTED = 0xC00E0091;

            /**
             * MQ_ERROR_Q_DNS_PROPERTY_NOT_SUPPORTED [0xC00E006E]
             */
            public static final int MQ_ERROR_Q_DNS_PROPERTY_NOT_SUPPORTED = 0xC00E006E;

            /**
             * MQ_ERROR_QUEUE_DELETED [0xC00E005A]
             */
            public static final int MQ_ERROR_QUEUE_DELETED = 0xC00E005A;

            /**
             * MQ_ERROR_QUEUE_EXISTS [0xC00E0005]
             */
            public static final int MQ_ERROR_QUEUE_EXISTS = 0xC00E0005;

            /**
             * MQ_ERROR_QUEUE_NOT_ACTIVE [0xC00E0004]
             */
            public static final int MQ_ERROR_QUEUE_NOT_ACTIVE = 0xC00E0004;

            /**
             * MQ_ERROR_QUEUE_NOT_AVAILABLE [0xC00E004B]
             */
            public static final int MQ_ERROR_QUEUE_NOT_AVAILABLE = 0xC00E004B;

            /**
             * MQ_ERROR_QUEUE_NOT_FOUND [0xC00E0003]
             */
            public static final int MQ_ERROR_QUEUE_NOT_FOUND = 0xC00E0003;

            /**
             * MQ_ERROR_REMOTE_MACHINE_NOT_AVAILABLE [0xC00E0069]
             */
            public static final int MQ_ERROR_REMOTE_MACHINE_NOT_AVAILABLE = 0xC00E0069;

            /**
             * MQ_ERROR_RESULT_BUFFER_TOO_SMALL [0xC00E0046]
             */
            public static final int MQ_ERROR_RESULT_BUFFER_TOO_SMALL = 0xC00E0046;

            /**
             * MQ_ERROR_SECURITY_DESCRIPTOR_TOO_SMALL [0xC00E0023]
             */
            public static final int MQ_ERROR_SECURITY_DESCRIPTOR_TOO_SMALL = 0xC00E0023;

            /**
             * MQ_ERROR_SENDER_CERT_BUFFER_TOO_SMALL [0xC00E002B]
             */
            public static final int MQ_ERROR_SENDER_CERT_BUFFER_TOO_SMALL = 0xC00E002B;

            /**
             * MQ_ERROR_SENDERID_BUFFER_TOO_SMALL [0xC00E0022]
             */
            public static final int MQ_ERROR_SENDERID_BUFFER_TOO_SMALL = 0xC00E0022;

            /**
             * MQ_ERROR_SERVICE_NOT_AVAILABLE [0xC00E000B]
             */
            public static final int MQ_ERROR_SERVICE_NOT_AVAILABLE = 0xC00E000B;

            /**
             * MQ_ERROR_SIGNATURE_BUFFER_TOO_SMALL [0xC00E0062]
             */
            public static final int MQ_ERROR_SIGNATURE_BUFFER_TOO_SMALL = 0xC00E0062;

            /**
             * MQ_ERROR_SHARING_VIOLATION [0xC00E0009]
             */
            public static final int MQ_ERROR_SHARING_VIOLATION = 0xC00E0009;

            /**
             * MQ_ERROR_STALE_HANDLE [0xC00E0056]
             */
            public static final int MQ_ERROR_STALE_HANDLE = 0xC00E0056;

            /**
             * MQ_ERROR_SYMM_KEY_BUFFER_TOO_SMALL [0xC00E0061]
             */
            public static final int MQ_ERROR_SYMM_KEY_BUFFER_TOO_SMALL = 0xC00E0061;

            /**
             * MQ_ERROR_TRANSACTION_ENLIST [0xC00E0058]
             */
            public static final int MQ_ERROR_TRANSACTION_ENLIST = 0xC00E0058;

            /**
             * MQ_ERROR_TRANSACTION_IMPORT [0xC00E004E]
             */
            public static final int MQ_ERROR_TRANSACTION_IMPORT = 0xC00E004E;

            /**
             * MQ_ERROR_TRANSACTION_SEQUENCE [0xC00E0051]
             */
            public static final int MQ_ERROR_TRANSACTION_SEQUENCE = 0xC00E0051;

            /**
             * MQ_ERROR_TRANSACTION_USAGE [0xC00E0050]
             */
            public static final int MQ_ERROR_TRANSACTION_USAGE = 0xC00E0050;

            /**
             * MQ_ERROR_WRITE_NOT_ALLOWED [0xC00E0065]
             */
            public static final int MQ_ERROR_WRITE_NOT_ALLOWED = 0xC00E0065;

            /**
             * MQ_ERROR_UNINITIALIZED_OBJECT [0xC00E0094]
             */
            public static final int MQ_ERROR_UNINITIALIZED_OBJECT = 0xC00E0094;

            /**
             * MQ_ERROR_UNSUPPORTED_ACCESS_MODE [0xC00E0045]
             */
            public static final int MQ_ERROR_UNSUPPORTED_ACCESS_MODE = 0xC00E0045;

            /**
             * MQ_ERROR_UNSUPPORTED_CLASS [0xC00E0093]
             */
            public static final int MQ_ERROR_UNSUPPORTED_CLASS = 0xC00E0093;

            /**
             * MQ_ERROR_UNSUPPORTED_FORMATNAME_OPERATION [0xC00E0020]
             */
            public static final int MQ_ERROR_UNSUPPORTED_FORMATNAME_OPERATION = 0xC00E0020;

            /**
             * MQ_ERROR_UNSUPPORTED_OPERATION [0xC00E006A]
             */
            public static final int MQ_ERROR_UNSUPPORTED_OPERATION = 0xC00E006A;

            /**
             * MQ_ERROR_USER_BUFFER_TOO_SMALL [0xC00E0028]
             */
            public static final int MQ_ERROR_USER_BUFFER_TOO_SMALL = 0xC00E0028;

            /**
             * MQ_ERROR_WKS_CANT_SERVE_CLIENT [0xC00E0066]
             */
            public static final int MQ_ERROR_WKS_CANT_SERVE_CLIENT = 0xC00E0066;



        //Contantes publicas de informaciones (HRESULT)
            /**
             * MQ_INFORMATION_DUPLICATE_PROPERTY [0x400E0005]
             */
            public static final int MQ_INFORMATION_DUPLICATE_PROPERTY = 0x400E0005;

            /**
             * MQ_INFORMATION_FORMATNAME_BUFFER_TOO_SMALL [0x400E0009]
             */
            public static final int MQ_INFORMATION_FORMATNAME_BUFFER_TOO_SMALL = 0x400E0009;

            /**
             * MQ_INFORMATION_ILLEGAL_PROPERTY [0x400E0002]
             */
            public static final int MQ_INFORMATION_ILLEGAL_PROPERTY = 0x400E0002;

            /**
             * MQ_INFORMATION_INTERNAL_USER_CERT_EXIST [0x400E000A]
             */
            public static final int MQ_INFORMATION_INTERNAL_USER_CERT_EXIST = 0x400E000A;

            /**
             * MQ_INFORMATION_OPERATION_PENDING [0x400E0006]
             */
            public static final int MQ_INFORMATION_OPERATION_PENDING = 0x400E0006;

            /**
             * MQ_INFORMATION_OWNER_IGNORED [0x400E000B]
             */
            public static final int MQ_INFORMATION_OWNER_IGNORED = 0x400E000B;

            /**
             * MQ_INFORMATION_PROPERTY [0x400E0001]
             */
            public static final int MQ_INFORMATION_PROPERTY = 0x400E0001;

            /**
             * MQ_INFORMATION_PROPERTY_IGNORED [0x400E0003]
             */
            public static final int MQ_INFORMATION_PROPERTY_IGNORED = 0x400E0003;

            /**
             * MQ_INFORMATION_UNSUPPORTED_PROPERTY [0x400E0004]
             */
            public static final int MQ_INFORMATION_UNSUPPORTED_PROPERTY = 0x400E0004;

}
