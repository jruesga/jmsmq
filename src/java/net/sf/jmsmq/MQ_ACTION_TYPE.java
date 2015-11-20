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
 * Class that contains the queue action type enumeration for reading message. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 * 
 * @author  Jorge Ruesga 
 */
public final class MQ_ACTION_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Action type

        //Actions type constants
            /**
             * Extract current message action
             */
            public static final int _MQ_ACTION_RECEIVE = 0x00000000;

            /**
             * Read current message action
             */
            public static final int _MQ_ACTION_PEEK_CURRENT = 0x80000000;

            /**
             * Read next message action
             */
            public static final int _MQ_ACTION_PEEK_NEXT = 0x80000001;

        //Public constants classes
            /**
             * Extract current message action class
             */
            public static final MQ_ACTION_TYPE MQ_ACTION_RECEIVE = new MQ_ACTION_TYPE(_MQ_ACTION_RECEIVE);

            /**
             * Read current message action class
             */
            public static final MQ_ACTION_TYPE MQ_ACTION_PEEK_CURRENT = new MQ_ACTION_TYPE(_MQ_ACTION_PEEK_CURRENT);

            /**
             * Read next message action class
             */
            public static final MQ_ACTION_TYPE MQ_ACTION_PEEK_NEXT = new MQ_ACTION_TYPE(_MQ_ACTION_PEEK_NEXT);


    /**
     * Constructor of <code>MQ_ACTION_TYPE</code>.
     *
     * @param type Read action type
     */
    private MQ_ACTION_TYPE(int type) {
        //Superconstructor invocation
        super();

        //Store the property
        this.type = type;
    }

    /**
     * Method that returns the read action property value
     *
     * @return int Returns the property value
     */
    public int getType() {
        //Returns the property value
        return this.type;
    }

}