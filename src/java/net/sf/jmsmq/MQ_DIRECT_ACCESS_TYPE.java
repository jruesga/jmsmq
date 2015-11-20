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
 * Class that contains the direct acces type enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 * 
 * @author  Jorge Ruesga 
 */
public final class MQ_DIRECT_ACCESS_TYPE {

    /**
     * Internal variables
     */
        //Properties
        private int type;       //Direct access type

        //Direct access constants
            /**
             * Direct access by host name
             */
            public static final int _MQ_DIRECT_ACCESS_OS = 0x00000000;

            /**
             * Direct access by host ip
             */
            public static final int _MQ_DIRECT_ACCESS_TCP = 0x00000001;

        //Public classes
            /**
             * Direct access by host name class
             */
            public static final MQ_DIRECT_ACCESS_TYPE MQ_DIRECT_ACCESS_OS = new MQ_DIRECT_ACCESS_TYPE(_MQ_DIRECT_ACCESS_OS);

            /**
             * Direct access by host ip class
             */
            public static final MQ_DIRECT_ACCESS_TYPE MQ_DIRECT_ACCESS_TCP = new MQ_DIRECT_ACCESS_TYPE(_MQ_DIRECT_ACCESS_TCP);

    /**
     * Constructor of <code>MQ_DIRECT_ACCESS_TYPE</code>.
     *
     * @param type Direct access type
     */
    private MQ_DIRECT_ACCESS_TYPE(int type) {
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