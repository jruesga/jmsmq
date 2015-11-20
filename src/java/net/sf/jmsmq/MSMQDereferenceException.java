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
 * Class thrown where a MSMQ element has been dereferenced. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author Jorge Ruesga
 */
public class MSMQDereferenceException extends NullPointerException {        //Extends of "NullPointerException" for it can be thrown as a "RuntimeException" 

    /**
     * Internal variables
     */
        //Properties
        private static final long serialVersionUID = 2142057388685082848L;      //SerialVersionUID

    /**
     * Constructor of <code>MSMQDereferenceException</code>
     */
    public MSMQDereferenceException() {
        //Superconstructor invocation
        super();
    }

}

