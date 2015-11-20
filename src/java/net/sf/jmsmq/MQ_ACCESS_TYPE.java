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
 * Class that contains the queue access type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 * 
 * @author  Jorge Ruesga 
 */
public final class MQ_ACCESS_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Access type value

        //Access type constants
            /**
             * Receive read access
             */
            public static final int _MQ_ACCESS_RECEIVE = 0x00000001;

            /**
             * Peek read access
             */
            public static final int _MQ_ACCESS_PEEK = 0x00000020;

            /**
             * Send acces
             */
            public static final int _MQ_ACCESS_SEND = 0x00000002;

            /**
             * Admin access
             */
            public static final int _MQ_ACCESS_ADMIN = 0x00000080;

        //Public constants classes
            /**
             * Receive read access class
             */
            public static final MQ_ACCESS_TYPE MQ_ACCESS_RECEIVE = new MQ_ACCESS_TYPE(_MQ_ACCESS_RECEIVE);

            /**
             * Peek read access class
             */
            public static final MQ_ACCESS_TYPE MQ_ACCESS_PEEK = new MQ_ACCESS_TYPE(_MQ_ACCESS_PEEK);

            /**
             * Send access class
             */
            public static final MQ_ACCESS_TYPE MQ_ACCESS_SEND = new MQ_ACCESS_TYPE(_MQ_ACCESS_SEND);

            /**
             * Admin access class
             */
            public static final MQ_ACCESS_TYPE MQ_ACCESS_ADMIN = new MQ_ACCESS_TYPE(_MQ_ACCESS_ADMIN);

    /**
     * Constructor of <code>MQ_ACCESS_TYPE</code>.
     *
     * @param type Queue access type
     */
    private MQ_ACCESS_TYPE(int type) {
        //Superconstructor invocation
        super();

        //Store the property
        this.type = type;
    }

    /**
     * Method that returns the queue access property value
     *
     * @return int Returns the property value
     */
    public int getType() {
        //Returns the property value 
        return this.type;
    }

}