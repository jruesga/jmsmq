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
 * Class thrown where a queue has no message. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 * 
 * @author Jorge Ruesga
 */
public class MSMQEmptyQueueException extends RuntimeException {  //It´s only way of throw an exception in "MSMQMessageEnumerator" using "hasMoreElements" or "nextElement" method inherited from "Enumeration" class

    /**
     * Internal variables
     */
        //Properties
		private static final long serialVersionUID = -8388758805682696041L;			//SerialVersionUID

	/**
     * Constructor of <code>MSMQEmptyQueueException</code>
     */
    public MSMQEmptyQueueException() {
        //Superconstructor invocation
        super();
    }

}

