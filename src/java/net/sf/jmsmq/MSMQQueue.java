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
 * Class that mapping a native implementation of a MSMQ queue object. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>.
 * 
 * This class use JNI interfaces over the "jmsmq.dll" library. This library has to have visibility from the environment "PATH" variable
 * of the execution of the application that use this project
 *
 * @author Jorge Ruesga
 */
public class MSMQQueue implements Serializable {

    /**
     * Internal variables
     */
        //Properties
        private int hQueue;      //Quueue handle
        private int hCursor;     //Cursor handle

        private String queueFormatName;                     //Queue format name
        private MQ_XACT_TYPE queueXAct;						//Queue transactional type

        //Internal JNI comunication properties
        private int __internal_queue_int_property__;
        private String __internal_queue_string_property__;
        private MSMQMessageEnumerator __internal_messageEnumerator__;

        //Default properties
        private static int MQ_TIMEOUT = 250;                //Read operation timeout
        private static boolean MQ_SEND_AS_UNICODE = true;   //Send as UNICODE code page

        private static final long serialVersionUID = 6594743192578354083L;		//SerialVersionUID

        /**
         * Static properties load
         */
        static{
            //Store MQ_TIMEOUT property
            try{
                MQ_TIMEOUT = Integer.parseInt( MSMQResources.getResource( "MQ_TIMEOUT" ) ); //$NON-NLS-1$
            }catch (Throwable _throw) {;}

            //Store MQ_SEND_AS_UNICODE property
            try{
                MQ_SEND_AS_UNICODE = new Boolean( MSMQResources.getResource( "MQ_SEND_AS_UNICODE" ) ).booleanValue(); //$NON-NLS-1$
            }catch (Throwable _throw) {;}
        }

    /**
     * Constructor of <code>MSMQQueue</code>.
     *
     * @param queueFormatName Queue format name
     */
    protected MSMQQueue( String queueFormatName ) {

        //Superconstructor invocation
        super();

        //Initialize class
        this.hQueue = 0;
        this.hCursor = 0;
        this.queueFormatName=queueFormatName;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {

        //Destroy open references
        this.closeQueue(true);

        //Super finalize
        super.finalize();
    }


    /**
	 * Method that sets the queueXAct property
	 *
	 * @param queueXAct New queueXAct property value.
	 */
	protected void setQueueXAct(MQ_XACT_TYPE queueXAct) {
		//Set the new property value
		this.queueXAct = queueXAct;
	}


	/**
     * Método que cierra todas la referencias de las colas abiertas (handles, cursores, ...)
     * Method that close all open references of the queue (handles, cursors, ...)
     *
     * @param bDereferenceEnum Dereference internal enumerator?
     */
    protected void closeQueue(boolean bDereferenceEnum){

        //Destroy open references
        try{
            closeMQCursorHandle(bDereferenceEnum);      //Close open cursor handles references
        }catch (MSMQException _msmqEx) {;}
        try{
            closeMQHandle();            //Close open queue handles references
        }catch (MSMQException _msmqEx) {;}
    }

    /**
     * Method that close the handle of the queue that is mapping this class
     *
     * @throws MSMQException Throw if a exception occurs closing the handle
     */
    private void closeMQHandle() throws MSMQException {

        //Is open?
        if( isMQHandleOpen() ){
            //Native invocation
            int hResult = jniMQCloseHandle();

            //Invocation success?
            if( hResult != MSMQConstants.MQ_OK )
                throw new MSMQException(hResult);
        }
    }

    /**
     * Method that close the handle of the cursor that is mapping this class
     *
     * @param bDereferenceEnum Dereference internal enumerator?
     * @throws MSMQException Throw if an exception occurs closing the handle
     */
    private void closeMQCursorHandle(boolean bDereferenceEnum) throws MSMQException {

        //Is open?
        if( isMQCursorHandleOpen() ){
            //Native invocation
            int hResult = jniMQCloseCursorHandle();

            //Invocation success?
            if( hResult != MSMQConstants.MQ_OK )
                throw new MSMQException(hResult);

            //Dereference enumerator?
            if( bDereferenceEnum ){
                if( this.__internal_messageEnumerator__ != null )
                    this.__internal_messageEnumerator__.dereference();
                this.__internal_messageEnumerator__ = null;
            }
        }
    }
    
    /**
     * Method that returns if the handle to this queue is open 
     *
     * @return boolean Is queue handle open?
     */
    private boolean isMQHandleOpen(){
        //Is queue handle open?
        return ( this.hQueue != 0 );
    }

    /**
     * Method that returns if the cursor to this queue is open
     *
     * @return boolean Is cursor handle open?
     */
    private boolean isMQCursorHandleOpen(){
        //Is cursor handle open?
        return ( this.hCursor != 0 );
    }

    /**
     * Method that returns the queue format name
     *
     * @return String Queue format name
     */
    public String getQueueFormatName() {
        //Return the property value
        return this.queueFormatName;
    }

    /**
     * Method that returns the queue name
     *
     * @return String Queue name
     */
    public String getQueueName() {
        //The queue is the last part of the queue format name
        return this.queueFormatName.substring( this.queueFormatName.lastIndexOf("\\")+1 ); //$NON-NLS-1$
    }

    /**
     * Method that returns the queue path name
     *
     * @return String Queue path name
     * @throws MSMQException Throw if an exception occurs retrieving the queue info
     */
    public String getQueuePathName() throws MSMQException {

        //Native invocation
        int hResult = jniMQGetStringQueueProperty( MQ_QUEUE_PROP_TYPE._PROPID_Q_PATHNAME );

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);

        //Retrieve the queue path name from JNI variable
        String queuePathName = this.__internal_queue_string_property__;

        //Returns the queue path name
        return queuePathName;
    }

    /**
     * Method that returns the queue label
     *
     * @return String Queue label
     * @throws MSMQException Throw if an exception occurs retrieving the queue info
     */
    public String getQueueLabel() throws MSMQException {

        //Native invocation
        int hResult = jniMQGetStringQueueProperty( MQ_QUEUE_PROP_TYPE._PROPID_Q_LABEL );

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);

        //Retrieve the queue label from JNI variable
        String queueLabel =
            (this.__internal_queue_string_property__!=null && this.__internal_queue_string_property__.length()==0)?null:this.__internal_queue_string_property__;

        //Returns the queue path label
        return queueLabel;
    }

    /**
     * Method that sets the queue label
     *
     * @param newQueueLabel New queue label
     * @throws MSMQException Throw if an exception occurs setting the queue info
     * @throws MSMQMaxLenPropertyException Throw if an exception new queue label exceded the max size for queue label size
     */
    public void setQueueLabel(String newQueueLabel ) throws MSMQException, MSMQMaxLenPropertyException {

    	//Verify that queue label not is null
    	if( newQueueLabel == null )
    		throw new MSMQException( MSMQConstants.MQ_ERROR_INVALID_PARAMETER );

    	//Verify that queue label not exceded the maximum
    	if( newQueueLabel.length() > MSMQConstants.MQ_MAX_Q_LABEL_LEN )
    		throw new MSMQMaxLenPropertyException( "MQ_MAX_Q_LABEL_LEN", MSMQConstants.MQ_MAX_Q_LABEL_LEN ); //$NON-NLS-1$

        //Native invocation
        int hResult = jniMQSetQueueProperty( MQ_QUEUE_PROP_TYPE._PROPID_Q_LABEL, newQueueLabel );

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);
    }

    /**
     * Method that returns the queue type
     *
     * @return String Queue type
     */
    public MQ_QUEUE_TYPE getQueueType() {

        //The queue type can retrieve from queue format name
        return ((this.queueFormatName.toLowerCase().indexOf(MSMQConstants.MQ_TYPE_PRIVATE_DEF)!=-1)?MQ_QUEUE_TYPE.MQ_TYPE_PRIVATE:MQ_QUEUE_TYPE.MQ_TYPE_PUBLIC);
    }

    /**
     * Method that returns the transactional type of the queue 
     *
     * @return String Queue transactional type
     * @throws MSMQException Throw if an exception occurs setting the queue info
     */
    public MQ_XACT_TYPE getQueueXActType() throws MSMQException {

        //Has been recovered before?
        if( this.queueXAct == null ){
            //Native invocation
            int hResult = jniMQGetIntQueueProperty( MQ_QUEUE_PROP_TYPE._PROPID_Q_TRANSACTION );

            //Invocation success?
            if( hResult != MSMQConstants.MQ_OK )
                throw new MSMQException(hResult);

            //Retrieve the queue transaction type from JNI variable
            int xact = this.__internal_queue_int_property__;

            //Returns the queue transaction type
            return ((xact==MQ_XACT_TYPE._MQ_XACT_NONE)?MQ_XACT_TYPE.MQ_XACT_NONE:MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL);
        }
        
        //Returns the queue transaction type stored previously
        return this.queueXAct;
    }

    /**
     * Method that delete the queue from MSMQ server
     *
     * @throws MSMQException Throw if an exception occurs deleting the queue
     */
    protected void deleteQueue() throws MSMQException {

        //Before delete the queue, destroy open references
        this.closeQueue(true);

        //Native invocation
        int hResult = jniMQDeleteQueue();

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);
    }

    /**
     * Method that opens a handle reference
     *
     * @param eAccessMode Queue access mode
     * @param eSharedMode Deny share mode
     * @throws MSMQException Throw if an exception occurs opening the queue
     */
    private void openQueue(MQ_ACCESS_TYPE eAccessMode, MQ_DENY_TYPE eSharedMode) throws MSMQException {

        //Close any other references open
        try {
            this.closeMQHandle();
        } catch (MSMQException ex) {;}

        //Native invocation
        int hResult = jniMQOpenQueue( eAccessMode.getType(), eSharedMode.getType() );

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK ){
            //Close the handle if its open (for another other exception after open the queue)
            try {
                this.closeMQHandle();
            } catch (MSMQException ex) {;}

            //Throw exception
            throw new MSMQException(hResult);
        }
    }

    /**
     * Method that delete all message in the queue
     *
     * @throws MSMQException Throw if an exception occurs purging the queue
     */
    public void purgeQueue() throws MSMQException {

        //Close all references open
        this.closeQueue(true);

        //Open the queue for extract messages from the queue
        this.openQueue( MQ_ACCESS_TYPE.MQ_ACCESS_RECEIVE, MQ_DENY_TYPE.MQ_DENY_NONE );

        //Native invocation
        int hResult = jniMQPurgeQueue();

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);
    }
    
    /**
     * Method that extract a message of the queue
     *
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    public MSMQMessage readMessage() throws MSMQException {
        
        //Overload method calling
        return this.readMessage( MSMQQueue.MQ_TIMEOUT );
    }

    /**
     * Method that extract a message of the queue
     *
     * @param timeOut Read operation timeout
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    public MSMQMessage readMessage(long timeOut) throws MSMQException {

        //Recovery transaction type, if didn´t have perviously 
    	if(this.queueXAct == null){
	        try{
	            this.queueXAct = this.getQueueXActType();
	        }catch (Throwable _throw) {;}
        }

        //Close all open references
        this.closeQueue(true);

        //Open queue for receive access
        this.openQueue( MQ_ACCESS_TYPE.MQ_ACCESS_RECEIVE, MQ_DENY_TYPE.MQ_DENY_NONE );

        //Create message reference
        MSMQMessage message = new MSMQMessage();

        //Native invocation
        int hResult = jniMQReadQueue(
                                MQ_ACTION_TYPE._MQ_ACTION_RECEIVE,
                                (this.queueXAct==null || this.queueXAct.equals(MQ_XACT_TYPE.MQ_XACT_NONE)?MQ_TRANSACTION_TYPE._MQ_NO_TRANSACTION:MQ_TRANSACTION_TYPE._MQ_SINGLE_MESSAGE),
                                timeOut,
                                message );

        //Close all open references
        this.closeQueue(true);

        //Invocation success?
        if( hResult == MSMQConstants.MQ_ERROR_IO_TIMEOUT )		//No message in queue
            //throw MSMQEmptyQueueException
            throw new MSMQEmptyQueueException();

        else if( hResult != MSMQConstants.MQ_OK )
            //Rethrow MSMQ JNI exception
            throw new MSMQException(hResult);

        //Recovery transaction type, if didn´t have perviously
        if( this.queueXAct == null ){
            //Assume as transactional if the message owns to a part of a transactional entity
            this.queueXAct = (message.isTransactionalMessage())?MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL:MQ_XACT_TYPE.MQ_XACT_NONE;
        }

        //Returns extracted message
        return message;
    }

    /**
     * Method that create a new message enumerator of the queue
     *
     * This method returns a new message enumerator associated to the queue. The message enumerator
     * is dereference if any of the methods <code>getMessageEnumerator()</code>, <code>readMessage()</code>,
     * <code>peekMessage</code>, <code>sendMessage()</code> or <code>purgeQueue</code> are invoked.
     *
     * @return MSMQMessageEnumerator New message enumerator reference
     */
    public MSMQMessageEnumerator getMessageEnumerator() {

        //Dereference the previous enumerator
        if( this.__internal_messageEnumerator__ != null )
            this.__internal_messageEnumerator__.dereference();
        this.__internal_messageEnumerator__ = null;

        //Create and returns a new message enumerator
        this.__internal_messageEnumerator__ = new MSMQMessageEnumerator( this );
        return this.__internal_messageEnumerator__;
    }

    /**
     * Method that read the first message in the queue
     *
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    public MSMQMessage peekMessage() throws MSMQException, MSMQEmptyQueueException {

        //Read first message with default timeout
        return peekMessage(MSMQQueue.MQ_TIMEOUT);
    }
    
    /**
     * Method that read the first message in the queue
     *
     * @param timeOut Read operation timeout
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    public MSMQMessage peekMessage(long timeOut) throws MSMQException, MSMQEmptyQueueException {

        //Read first message with specified timeout
        return peekMessage(true, true, timeOut);
    }
    
    /**
     * Method that read a message from the queue. This method give support to <code>MSMQMessageEnumerator</code>
     * enabling to read sequentially message with extract them from the queue
     *
     * @param first Read first or next message
     * @param bDereferenceEnum Dereference previous enumerator
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    protected MSMQMessage peekMessage(boolean first, boolean bDereferenceEnum) throws MSMQException, MSMQEmptyQueueException {
        
        //Overload method calling
        return this.peekMessage( first, bDereferenceEnum, MSMQQueue.MQ_TIMEOUT );
    }

    /**
     * Method that read a message from the queue. This method give support to <code>MSMQMessageEnumerator</code>
     * enabling to read sequentially message with extract them from the queue
     *
     * @param first Read first or next message
     * @param bDereferenceEnum Dereference previous enumerator
     * @param timeOut Read operation timeout
     * @return MSMQMessage The message read
     * @throws MSMQException Throw if an exception occurs reading the queue
     * @throws MSMQEmptyQueueException Throw if an exception occurs if the queue has no messages
     */
    protected MSMQMessage peekMessage(boolean first, boolean bDereferenceEnum, long timeOut) throws MSMQException, MSMQEmptyQueueException {

        //Recovery transaction type, if didn´t have perviously
    	if(this.queueXAct == null){
	        try{
	            this.queueXAct = this.getQueueXActType();
	        }catch (Throwable _throw) {;}
        }

        //If the operation is read the first messahe in the queue, then close all open references and 
        //open the queue for peek access
        if( first ){
            //Close all open references
            this.closeQueue(bDereferenceEnum);

            //Open the queue for peek access
            this.openQueue( MQ_ACCESS_TYPE.MQ_ACCESS_PEEK, MQ_DENY_TYPE.MQ_DENY_NONE );
        }
        
        //Create message reference
        MSMQMessage message = new MSMQMessage();
        
        //Native invocation
        int hResult = jniMQReadQueue(
                                (first)?MQ_ACTION_TYPE._MQ_ACTION_PEEK_CURRENT:MQ_ACTION_TYPE._MQ_ACTION_PEEK_NEXT,
                                (this.queueXAct == null || this.queueXAct.equals(MQ_XACT_TYPE.MQ_XACT_NONE)?MQ_TRANSACTION_TYPE._MQ_NO_TRANSACTION:MQ_TRANSACTION_TYPE._MQ_MTS_TRANSACTION),
                                timeOut,
                                message );

        //If an error ocurrs close queue and dereference enumerator
        if(hResult != MSMQConstants.MQ_OK)
            //Close queue and all cursors associated
            this.closeQueue(bDereferenceEnum);

        //Analyze operation result
        if( hResult == MSMQConstants.MQ_ERROR_IO_TIMEOUT )		//No more message
            //throw MSMQEmptyQueueException
            throw new MSMQEmptyQueueException();

        else if( hResult != MSMQConstants.MQ_OK )
            //Rethrow MSMQ JNI exception
            throw new MSMQException(hResult);

        //Recovery transaction type, if didn´t have perviously
        if( this.queueXAct == null ){
            //Assume as transactional if the message owns to a part of a transactional entity
            this.queueXAct = (message.isTransactionalMessage())?MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL:MQ_XACT_TYPE.MQ_XACT_NONE;
        }

        //Returns readed message
        return message;
    }

    /**
     * Method that sends a message to the queue
     *
     * @param message Message to send
     * @throws MSMQException Throw if an exception occurs sending the message to the queue
     * @throws MSMQMaxLenPropertyException Throw if an exception occurs if the message label length excedeed the maximum label size
     */
    public void sendMessage( MSMQMessage message ) throws MSMQException, MSMQMaxLenPropertyException {

        //It´s a valid message?
        if(message == null || (message.getMessageLabel() == null && message.getMessageBody() == null))
            throw new MSMQException( MSMQConstants.MQ_ERROR_INVALID_PARAMETER );

        //Verify message label length
    	if( message.getMessageLabel().length() > MSMQConstants.MQ_MAX_MSG_LABEL_LEN )
    		throw new MSMQMaxLenPropertyException( "MQ_MAX_MSG_LABEL_LEN", MSMQConstants.MQ_MAX_MSG_LABEL_LEN ); //$NON-NLS-1$

        //Recovery transaction type, if didn´t have perviously
        if(this.queueXAct == null){
	        try{
	            this.queueXAct = this.getQueueXActType();
	        }catch (Throwable _throw) {;}
        }

        //JRC -- If not know the transaction type yet, it´s need a workarround to discover the queue trasaction type, because
        //message with a transactional type differents to the queue transactional type are not send correctly, even when
        //a MQ_OK result code is obtained. To fix the problem, and because not always is possible to access to queue information in remote MSMQ servers, assume as a 
        //queue transactional type that of the first one message in the queue
        if( this.queueXAct == null ){
            //Read first message
            try{
                //Peek messaege
                MSMQMessage msg = this.peekMessage();

                //Assume the queue transactional type as the message transactional type
                this.queueXAct = (msg.isTransactionalMessage())?MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL:MQ_XACT_TYPE.MQ_XACT_NONE;
            }catch (Throwable _throw) {;}
        }

        //Close all open references
        this.closeQueue(true);

        //Open the queue for send access
        this.openQueue( MQ_ACCESS_TYPE.MQ_ACCESS_SEND, MQ_DENY_TYPE.MQ_DENY_NONE );

        //Native invocation
        int hResult = jniMQSendQueue(
                            message,
                            (this.queueXAct == null || this.queueXAct.equals(MQ_XACT_TYPE.MQ_XACT_NONE)?MQ_TRANSACTION_TYPE._MQ_NO_TRANSACTION:MQ_TRANSACTION_TYPE._MQ_SINGLE_MESSAGE),
                            MSMQQueue.MQ_SEND_AS_UNICODE );

        //Close all open references
        this.closeQueue(true);

        //TODO -- JRC-- [No Thread Safe] -- Repair this workarround on a different code. Actually, the workarround can failed 
        //if another thread reads the sended message before that this class read for message existence.
        //JRC -- Another workarround is needed, if still unknown queue transactional type, the sended message has to be in the queue,
        //so trasactional types of queue and message are compatibles. Other way, the transactional types are incompatibles and it´s
        //necessary resend message with the compatible transactional type
        if( this.queueXAct == null  ){
            //Read first message
            try{
                //Peek messaege
                MSMQMessage msg = this.peekMessage();

                //Assume the queue transactional type as the message transactional type
                this.queueXAct = (msg.isTransactionalMessage())?MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL:MQ_XACT_TYPE.MQ_XACT_NONE;

            }catch (MSMQEmptyQueueException _eqEx) {
            	//Previosly, if i unknow the queue transactional type, send the message with MQ_XACT_NONE. Now resend
                //message with MQ_XACT_TRANSACTIONAL, and set queue transactional mode to this type (TODO -- JRC-- [No Thread Safe])
            	this.queueXAct = MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL;

            	//Close all open refereces
		        this.closeQueue(true);

		        //Open the queue for send access
		        this.openQueue( MQ_ACCESS_TYPE.MQ_ACCESS_SEND, MQ_DENY_TYPE.MQ_DENY_NONE );

            	//Native invocation
                hResult = jniMQSendQueue(
                                    message,
                                    (this.queueXAct == null || this.queueXAct.equals(MQ_XACT_TYPE.MQ_XACT_NONE)?MQ_TRANSACTION_TYPE._MQ_NO_TRANSACTION:MQ_TRANSACTION_TYPE._MQ_SINGLE_MESSAGE),
                                    MSMQQueue.MQ_SEND_AS_UNICODE);
            }

        }

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);
    }
    


//** -------------------------------------------------------------- ** \\
//                          NATIVOS METHODS                            \\
//** -------------------------------------------------------------- ** \\

    /**
     * Native method that close the queue handle
     *
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQCloseHandle();

    /**
     * Native method that close the cursor handle
     *
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQCloseCursorHandle();

    /**
     * Native method that gets a string queue property
     *
     * @param queueProperty Property to recovery
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQGetStringQueueProperty(int queueProperty);

    /**
     * Native method that gets a integer queue property
     *
     * @param queueProperty Property to recovery
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQGetIntQueueProperty(int queueProperty);

    /**
     * Native method that sets a string queue property
     *
     * @param queueProperty Property to set
     * @param queuePropertyValue New property value
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQSetQueueProperty(int queueProperty, String queuePropertyValue);

    /**
     * Native method that delete this queue
     *
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQDeleteQueue();

    /**
     * Native method that opens a reference to this queue
     *
     * @param eAccessMode Queue access mode
     * @param eSharedMode Queue deny share mode
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQOpenQueue(int eAccessMode, int eSharedMode);

    /**
     * Native method that delete all message in this queu
     *
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQPurgeQueue();


    /**
     * Native method that reads a message from this queue
     *
     * @param eActionType Read action type
     * @param eTransactionMode Read transactional mode
     * @param timeOut Read operation timeOut
     * @param message [OUT] If success, returns the readed message
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQReadQueue( int eActionType, int eTransactionMode, long timeOut, MSMQMessage message );

    /**
     * Native method that sends a message from this queue
     *
     * @param message Message to send
     * @param eTransactionMode Send transactional mode
     * @param asUnicode Send message with UNICODE Code Page?
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQSendQueue( MSMQMessage message, int eTransactionMode, boolean asUnicode );
}
