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
 * Class that containts a message enumeration for a MSMQ queue. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public class MSMQMessageEnumerator implements Enumeration {

    /**
     * Internal variables
     */
        //Properties
        private MSMQQueue queue;        //Reference of the queue to enumerate its message
        private MSMQMessage message;    //Recovery message
        private boolean isRecovered;    //It indicates if message has been recovered by user
        private boolean dereferenced;   //It indicates if queue is derefereced
        private boolean current;        //It indicates if the next operation is read first or next message

    /**
     * Constructor of MSMQMessageEnumerator
     *
     * @param queue Reference of the queue to enumerate its message
     */
    protected MSMQMessageEnumerator(MSMQQueue queue) {
        //Superconstructor invocation
        super();

        //Store the properties
        this.queue = queue;
        this.message = null;
        this.isRecovered = true;
        this.dereferenced=false;
        this.current=true;
    }

    /**
     * Internal méthod to deferenced the queue enumerator  
     */
    protected void dereference(){

        //Dereference the associated queue
        this.queue = null;
        this.message = null;
        this.isRecovered = true;
        this.dereferenced = true;
        this.current=true;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#hasMoreElements()
     * @throws MSMQException Si se produce una excepcion en la recuperación de la informacion
     * @throws MSMQEmptyQueueException Si no hay mensajes en la cola de mensajeria
     */
    public boolean hasMoreElements() {

        //It´s deferenced?
        if( this.dereferenced )
            throw new MSMQDereferenceException(); //Extends of "NullPointerException" for it can be thrown as a "RuntimeException" 

        //Read (peek) message from queue
        try{
            //User has recover message from "nextElement" method
            if( this.isRecovered ){
                //Extract message
                this.message = this.queue.peekMessage(this.current, false);

                //Reset user recovery message
                this.isRecovered = false;
                this.current=false;
            }

        }catch (MSMQEmptyQueueException msmqEQEx) {
        	return false;   //No more messages

        }catch (MSMQException msmqEx) {
            //This error its asumed as same as MSMQEmptyQueueException
            if( msmqEx.getHResult() == MSMQConstants.MQ_ERROR_IO_TIMEOUT )
                return false;   //No more messages

            //Rethrown the exception as a RuntimeException (It´s only way of throw an exception in "MSMQMessageEnumerator" using "hasMoreElements" or "nextElement" method inherited from "Enumeration" class)
            throw new RuntimeException( msmqEx );
        }

        //Yes, more messages
        return true;
    }

    /**
     * Method that initialize enumeration references and back to first message of the queue
     */
    public void reset(){

        //Inicializo los mensaje
        this.message = null;
        this.isRecovered = true;
        this.current=false;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#nextElement()
     */
    public Object nextElement() {

        //It´s deferenced?
        if( this.dereferenced )
            throw new MSMQDereferenceException();  //Extends of "NullPointerException" for it can be thrown as a "RuntimeException" 

        //Returns recovered message 
        this.isRecovered = true;    //User has recovered the message
        return this.message;
    }

}

