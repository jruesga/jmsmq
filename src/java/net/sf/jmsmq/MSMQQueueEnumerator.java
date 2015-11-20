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
import java.util.Enumeration;


/**
 * Class that containts a MSMQ queue enumeration. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public class MSMQQueueEnumerator implements Enumeration {

    /**
     * Internal variables
     */
        //Properties
        private MSMQQueue[] queues;         //Found queues
        private int actualQueue;            //Actual queue reference

    /**
     * Constructor of MSMQQueueEnumerator
     *
     * @param queues Found queues to enumerate
     */
    protected MSMQQueueEnumerator( MSMQQueue[] queues ) {
        //Superconstructor invocation
        super();

        //Store the properties
        this.actualQueue=0;
        this.queues = queues;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#hasMoreElements()
     */
    public boolean hasMoreElements() {

        //Exists more queues?
        if( this.queues != null && this.queues.length > this.actualQueue )
            return true;
        return false;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#nextElement()
     */
    public Object nextElement() {

        //Actual queue
        MSMQQueue queue = this.queues[this.actualQueue];
        this.actualQueue++;

        //return actual queue
        return queue;
    }

}

