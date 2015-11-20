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
 * Class that contains the queue properties type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public final class MQ_QUEUE_PROP_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Queue properties type

        //Queue properties constants
            /**
             * Property PROPID_Q_BASE
             */
            private static final int _PROPID_Q_BASE = 100;
            /**
             * Property PROPID_Q_INSTANCE
             */
            public static final int _PROPID_Q_INSTANCE = (_PROPID_Q_BASE + 0x01);           /* VT_CLSID     */
            /**
             * Property PROPID_Q_TYPE
             */
            public static final int _PROPID_Q_TYPE = (_PROPID_Q_BASE + 0x02);               /* VT_CLSID     */
            /**
             * Property PROPID_Q_PATHNAME
             */
            public static final int _PROPID_Q_PATHNAME = (_PROPID_Q_BASE + 0x03);           /* VT_LPWSTR    */
            /**
             * Property PROPID_Q_JOURNAL
             */
            public static final int _PROPID_Q_JOURNAL = (_PROPID_Q_BASE + 0x04);            /* VT_UI1       */
            /**
             * Property PROPID_Q_QUOTA
             */
            public static final int _PROPID_Q_QUOTA = (_PROPID_Q_BASE + 0x05);              /* VT_UI4       */
            /**
             * Property PROPID_Q_BASEPRIORITY
             */
            public static final int _PROPID_Q_BASEPRIORITY = (_PROPID_Q_BASE + 0x06);       /* VT_I2        */
            /**
             * Property PROPID_Q_JOURNAL_QUOTA
             */
            public static final int _PROPID_Q_JOURNAL_QUOTA = (_PROPID_Q_BASE + 0x07);      /* VT_UI4       */
            /**
             * Property PROPID_Q_LABEL
             */
            public static final int _PROPID_Q_LABEL = (_PROPID_Q_BASE + 0x08);              /* VT_LPWSTR    */
            /**
             * Property PROPID_Q_CREATE_TIME
             */
            public static final int _PROPID_Q_CREATE_TIME = (_PROPID_Q_BASE + 0x09);        /* VT_I4        */
            /**
             * Property PROPID_Q_MODIFY_TIME
             */
            public static final int _PROPID_Q_MODIFY_TIME = (_PROPID_Q_BASE + 0x0A);        /* VT_I4        */
            /**
             * Property PROPID_Q_AUTHENTICATE
             */
            public static final int _PROPID_Q_AUTHENTICATE = (_PROPID_Q_BASE + 0x0B);       /* VT_UI1       */
            /**
             * Property PROPID_Q_PRIV_LEVEL
             */
            public static final int _PROPID_Q_PRIV_LEVEL = (_PROPID_Q_BASE + 0x0C);         /* VT_UI4       */
            /**
             * Property PROPID_Q_TRANSACTION
             */
            public static final int _PROPID_Q_TRANSACTION = (_PROPID_Q_BASE + 0x0D);        /* VT_UI1       */
            /**
             * Property PROPID_Q_PATHNAME_DNS
             */
            public static final int _PROPID_Q_PATHNAME_DNS = (_PROPID_Q_BASE + 0x18);       /* VT_LPWSTR    */
            /**
             * Property PROPID_Q_MULTICAST_ADDRESS
             */
            public static final int _PROPID_Q_MULTICAST_ADDRESS = (_PROPID_Q_BASE + 0x19);  /* VT_LPWSTR    */
            /**
             * Property PROPID_Q_ADS_PATH
             */
            public static final int _PROPID_Q_ADS_PATH = (_PROPID_Q_BASE + 0x1A);           /* VT_LPWSTR    */


        //Public classes
            /**
             * Property PROPID_Q_INSTANCE class [VT_CLSID]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_INSTANCE = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_INSTANCE);
            /**
             * Property PROPID_Q_TYPE class [VT_CLSID]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_TYPE = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_TYPE);
            /**
             * Property PROPID_Q_PATHNAME class [VT_LPWSTR]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_PATHNAME = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_PATHNAME);
            /**
             * Property PROPID_Q_JOURNAL class [VT_UI1]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_JOURNAL = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_JOURNAL);
            /**
             * Property PROPID_Q_QUOTA class [VT_UI4]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_QUOTA = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_QUOTA);
            /**
             * Property PROPID_Q_BASEPRIORITY class [VT_I2]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_BASEPRIORITY = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_BASEPRIORITY);
            /**
             * Property PROPID_Q_JOURNAL_QUOTA class [VT_UI4]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_JOURNAL_QUOTA = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_JOURNAL_QUOTA);
            /**
             * Property PROPID_Q_LABEL class [VT_LPWSTR]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_LABEL = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_LABEL);
            /**
             * Property PROPID_Q_CREATE_TIME class [VT_I4]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_CREATE_TIME = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_CREATE_TIME);
            /**
             * Property PROPID_Q_MODIFY_TIME class [VT_I4]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_MODIFY_TIME = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_INSTANCE);
            /**
             * Property PROPID_Q_AUTHENTICATE class [VT_UI1]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_AUTHENTICATE = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_MODIFY_TIME);
            /**
             * Property PROPID_Q_PRIV_LEVEL class [VT_UI4]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_PRIV_LEVEL = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_PRIV_LEVEL);
            /**
             * Property PROPID_Q_TRANSACTION class [VT_UI1]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_TRANSACTION = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_TRANSACTION);
            /**
             * Property PROPID_Q_PATHNAME_DNS class [VT_LPWSTR]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_PATHNAME_DNS = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_PATHNAME_DNS);
            /**
             * Property PROPID_Q_MULTICAST_ADDRESS class [VT_LPWSTR]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_MULTICAST_ADDRESS = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_MULTICAST_ADDRESS);
            /**
             * Property PROPID_Q_ADS_PATH class [VT_LPWSTR]
             */
            public static final MQ_QUEUE_PROP_TYPE PROPID_Q_ADS_PATH = new MQ_QUEUE_PROP_TYPE(_PROPID_Q_ADS_PATH);


    /**
     * Constructor of <code>MQ_QUEUE_PROP_TYPE</code>.
     *
     * @param type Queue property type
     */
    private MQ_QUEUE_PROP_TYPE(int type) {
        //Superconstructor invocation
        super();

        //Store the property
        this.type = type;
    }

    /**
     * Método que devuelve la propiedad
     *
     * @return int Returns the property value
     */
    public int getType() {
        //Returns the property value
        return this.type;
    }

}