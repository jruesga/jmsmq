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
 * Class that contains the queue trasaction type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public final class MQ_XACT_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Queue transaction type

        //Queue transaction type constants
            /**
             * No transactional queue
             */
            public static final int _MQ_XACT_NONE = 0x00000000;

            /**
             * Transactional queue
             */
            public static final int _MQ_XACT_TRANSACTIONAL = 0x00000001;

        //Public classes
            /**
             * No transactional queue class
             */
            public static final MQ_XACT_TYPE MQ_XACT_NONE = new MQ_XACT_TYPE(_MQ_XACT_NONE);

            /**
             * Transactional queue class
             */
            public static final MQ_XACT_TYPE MQ_XACT_TRANSACTIONAL = new MQ_XACT_TYPE(_MQ_XACT_TRANSACTIONAL);

    /**
     * Constructor of <code>MQ_XACT_TYPE</code>.
     *
     * @param type Queue trasaction type
     */
    private MQ_XACT_TYPE(int type) {
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