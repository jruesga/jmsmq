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
/* PACKAGE           */
/* ***************** */
package net.sf.jmsmq;

/* ***************** */
/* IMPORTS           */
/* ***************** */

/**
 * Class that contains the message trasaction type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public final class MQ_TRANSACTION_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Message transaction type

        //Message transaction type constants
            /**
             * No transactional message
             */
            public static final int _MQ_NO_TRANSACTION = 0x00000000;

            /**
             * MTS transactional message
             */
            public static final int _MQ_MTS_TRANSACTION = 0x00000001;

            /**
             * XA transactional message
             */
            public static final int _MQ_XA_TRANSACTION = 0x00000002;

            /**
             * Single transactional message
             */
            public static final int _MQ_SINGLE_MESSAGE = 0x00000003;

        //Public classes
            /**
             * No transactional message class
             */
            public static final MQ_TRANSACTION_TYPE MQ_NO_TRANSACTION = new MQ_TRANSACTION_TYPE(_MQ_NO_TRANSACTION);

            /**
             * MTS transactional message class
             */
            public static final MQ_TRANSACTION_TYPE MQ_MTS_TRANSACTION = new MQ_TRANSACTION_TYPE(_MQ_MTS_TRANSACTION);

            /**
             * XA transactional message class
             */
            public static final MQ_TRANSACTION_TYPE MQ_XA_TRANSACTION = new MQ_TRANSACTION_TYPE(_MQ_XA_TRANSACTION);

            /**
             * Single transactional message class
             */
            public static final MQ_TRANSACTION_TYPE MQ_SINGLE_MESSAGE = new MQ_TRANSACTION_TYPE(_MQ_SINGLE_MESSAGE);

    /**
     * Constructor of <code>MQ_TRANSACTION_TYPE</code>.
     *
     * @param type Message transaction type
     */
    private MQ_TRANSACTION_TYPE(int type) {
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