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
 * Class that contains the open queue share mode type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 * 
 * @author  Jorge Ruesga 
 */
public final class MQ_DENY_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Share mode type

        //Open queue share mode constants
            /**
             * None deny type
             */
            public static final int _MQ_DENY_NONE = 0x00000000;

            /**
             * Share receive deny type
             */
            public static final int _MQ_DENY_RECEIVE_SHARE = 0x00000001;

        //Public constants classes
            /**
             * None deny type class
             */
            public static final MQ_DENY_TYPE MQ_DENY_NONE = new MQ_DENY_TYPE(_MQ_DENY_NONE);

            /**
             * Share receive deny type class
             */
            public static final MQ_DENY_TYPE MQ_DENY_RECEIVE_SHARE = new MQ_DENY_TYPE(_MQ_DENY_RECEIVE_SHARE);

    /**
     * Constructor of <code>MQ_DENY_TYPE</code>.
     *
     * @param type Open queue share mode type
     */
    private MQ_DENY_TYPE(int type) {
        //Superconstructor invocation
        super();

        //Store the property
        this.type = type;
    }

    /**
     * Method that returns the open queue share mode property value
     *
     * @return int Returns the property value
     */
    public int getType() {
        //Returns the property value
        return this.type;
    }

}