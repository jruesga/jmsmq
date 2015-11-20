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
import java.io.Serializable;


/**
 * Class that mapping a MSMQ message object. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public class MSMQMessage implements Serializable {

    /**
     * Internal variables
     */
        //Properties
        private String messageLabel;                //Message label
        private String messageBody;                 //Message body
        private boolean transactionalMessage;       //Message transactional type (JNI)

        private static final long serialVersionUID = -16379522598863875L;			//SerialVersionUID

    /**
     * Constructor of MSMQMessage
     */
    protected MSMQMessage() {
        //Overload constructor calling
        this("","");  //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * Constructor de MSMQMessage
     *
     * @param messageLabel Message label
     * @param messageBody Message body
     */
    public MSMQMessage( String messageLabel, String messageBody ) {
        //Superconstructor invocation
        super();

        //Store the properties
        this.messageLabel = (messageLabel==null)?"":messageLabel; //$NON-NLS-1$
        this.messageBody = (messageBody==null)?"":messageBody; //$NON-NLS-1$
        this.transactionalMessage = false;
    }

    /**
     * Method that returns the messageBody property
     *
     * @return String Returns messageBody propety.
     */
    public String getMessageBody() {
        //Return the property value
        return this.messageBody;
    }

    /**
     * Method that sets the messageBody property
     *
     * @param messageBody New messageBody property value.
     */
    public void setMessageBody(String messageBody) {
        //Set the new property value
    	this.messageBody = (messageBody==null)?"":messageBody; //$NON-NLS-1$
    }

    /**
     * Method that returns the messageLabel property
     *
     * @return String Returns messageLabel propety.
     */
    public String getMessageLabel() {
        //Return the property value
        return this.messageLabel;
    }

    /**
     * Method that sets the messageLabel property
     *
     * @param messageLabel New messageLabel property value.
     */
    public void setMessageLabel(String messageLabel) {
        //Set the new property value
    	this.messageLabel = (messageLabel==null)?"":messageLabel; //$NON-NLS-1$
    }

	/**
	 * Method that returns the transactionalMessage property
	 *
	 * @return boolean Returns transactionalMessage propety.
	 */
	public boolean isTransactionalMessage() {
		//Return the property value
		return this.transactionalMessage;
	}

}

