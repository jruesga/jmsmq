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
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Factory of connections to MSMQ servers. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>.
 * 
 * This class use JNI interfaces over the "jmsmq.dll" library. This library has to have visibility from the environment "PATH" variable
 * of the execution of the application that use this project
 *
 * @author  Jorge Ruesga
 */
public class MSMQQueueFactory implements Serializable {

    /**
     * Internal variables
     */
        //Properties
        private String msmqServer;      //MSMQ Server

        //Internal JNI comunication properties
        private String __internal_format_name__;
        private String[] __internal_enum_formats_name__;
        private String  __internal_lookup_queue__;

        private static final long serialVersionUID = -1219962587011417073L;				//SerialVersionUID


    /**
     * Static loading native JNI MSMQ library
     */
    static {
        //Load native library
        System.loadLibrary("jmsmq"); //$NON-NLS-1$
    }

    /**
     * Constructor of <code>MSMQQueueFactory</code>.
     *
     * @param msmqServer MSMQ Server host
     * @throws UnknownHostException Throw if an exception occurs when trying to locate MSMQ host
     */
    public MSMQQueueFactory( String msmqServer ) throws UnknownHostException {
        
        //Superconstructor invocation
        super();

        //Recovery a valid host name to use with MSMQ. Using ip host can generated problems when recovers
        //queue information. This fix the problem using always the name of the machine, so always use DIRECT_OS access
        InetAddress msmqInetAddress = (
										msmqServer.equals(".") ||  //$NON-NLS-1$
										msmqServer.toLowerCase().equals("localhost") ||  //$NON-NLS-1$
										msmqServer.equals("127.0.0.1") //$NON-NLS-1$
									  )
									  ?
									  InetAddress.getLocalHost():
									  InetAddress.getByName( msmqServer );

        //Store server reference
        this.msmqServer = msmqInetAddress.getHostName();
    }

    /**
     * Methdo that create a queue in the MSMQ server references by this factory
     *
     * @param eQueueType Queue type
     * @param queueName Queue name
     * @param eXAct Queue transactional type
     * @return MSMQQueue Returns, if success, the reference of the created queue
     * @throws MSMQException Throw if an exception occurs when creates the queue
     * @throws MSMQMaxLenPropertyException Throw if a name overflows the max size for a queue name
     */
    public MSMQQueue createQueue( MQ_QUEUE_TYPE eQueueType, String queueName, MQ_XACT_TYPE eXAct ) throws MSMQException, MSMQMaxLenPropertyException {

    	//Is a valid name?
        if(queueName == null && queueName.length() > 0)
            throw new MSMQException( MSMQConstants.MQ_ERROR_INVALID_PARAMETER );

        //Overflows the max size for a queue name?
    	if( queueName.length() > MSMQConstants.MQ_MAX_Q_NAME_LEN )
    		throw new MSMQMaxLenPropertyException( "MQ_MAX_Q_NAME_LEN", MSMQConstants.MQ_MAX_Q_NAME_LEN ); //$NON-NLS-1$


        //Generate the path of the queue to create (xe: msmqServerName\private$\queueName)
        String queuePathName = ""; //$NON-NLS-1$
            //Server name
        	queuePathName+=this.msmqServer;
            //Queue type
            if( eQueueType.equals( MQ_QUEUE_TYPE.MQ_TYPE_PRIVATE ) )
            	queuePathName+=MSMQConstants.MQ_TYPE_PRIVATE_DEF;
            else
            	queuePathName+=MSMQConstants.MQ_TYPE_PUBLIC_DEF;
            //Queue name
            queuePathName+=queueName;

        //Native invocation
        int hResult = jniMQCreateQueue(queuePathName,eXAct.getType());

        //Invocation success?
        if( hResult != MSMQConstants.MQ_OK )
            throw new MSMQException(hResult);

        //Recovery the queue format name of the queue created
        String queueFormatName = buildQueueFormatName(this.msmqServer,eQueueType,queueName);

        //Returns the reference of the created queue 
        return new MSMQQueue(queueFormatName);
    }

    /**
     * Method that delete the queue in the MSMQ server references by this factory
     *
     * @param queue Referece to queue to delete
     * @throws MSMQException Throw if an exception occurs when deletes the queue
     */
    public void deleteQueue( MSMQQueue queue ) throws MSMQException {

        //Delete queue (only permit invoke this method from this function)
        queue.deleteQueue();

        //Destroy reference
        queue = null;
    }

    /**
     * Method that search a queue into the MSMQ server references by this factory
     *
     * @param eQueueType Queue type
     * @param queueName Queue name
     * @return MSMQQueue Referece to the founded queue
     * @throws MSMQException Throw if an exception occurs when searchs the queue
     */
    public synchronized MSMQQueue lookupQueue( MQ_QUEUE_TYPE eQueueType, String queueName ) throws MSMQException {

        //Recovery the queue format name of the queue to search
        String queueFormatName = buildQueueFormatName(this.msmqServer,eQueueType,queueName);

        //Native invocation
        this.__internal_lookup_queue__=null;   //Before invoke, ensure to destroy the comunation variable between JNI an Java
        int hResult = jniMQLookupQueue(queueFormatName);

        //JRC -- If the result of this operation is MQ_ERROR_UNSUPPORTED_FORMATNAME_OPERATION then
        //is probably that the application is trying to access to a remote MSMQ server, configured on
        //workgroup mode, so isn´t possible to access to the configuration parameters of the queue. In this
        //case, the method fix the problem discovering the queue using a "unknown valid queue reference" and
        //trying to read a message with its peek method

        //Analize invocation result
        if( hResult != MSMQConstants.MQ_OK && hResult != MSMQConstants.MQ_ERROR_UNSUPPORTED_FORMATNAME_OPERATION )
            throw new MSMQException(hResult);

        //Invocation success?
        if( hResult == MSMQConstants.MQ_OK ){
	        //Recovery the queue format name from founded queue 
	        queueFormatName = buildQueueFormatName(this.__internal_lookup_queue__);
            
            //Returns queue reference 
            return new MSMQQueue(queueFormatName);
        }

        //MSMQ Remote Server configured on Workgroup mode?
        	//Create a "unknown valid queue reference" for discover the existence of the queue
        	MSMQQueue testQueue = null;
        	try {
                //Create a "unknown valid queue reference" for testing
    			testQueue = new MSMQQueue(queueFormatName);
    			MSMQMessage msg = testQueue.peekMessage();	
                testQueue.closeQueue(true);
    
                //The queue exists an is valid reference. Try to recovery queue transactional type
    			testQueue.setQueueXAct( (msg.isTransactionalMessage())?MQ_XACT_TYPE.MQ_XACT_TRANSACTIONAL:MQ_XACT_TYPE.MQ_XACT_NONE );
    
    		} catch (MSMQEmptyQueueException e) {
    			//Ignore exception. The queue exists an is valid reference.
                //Try to recovery queue trasaction type if is possible
                try{
                    testQueue.getQueueXActType();
                }catch (Throwable _throw) {;}
    			
    		} catch (MSMQException e) {
    			//The queue not exists or it´s imposible to discover its existence
    			throw e;
    		}
            finally{
                //Reset queue state
                try{
                    testQueue.closeQueue(true);
                }catch (Throwable _throw) {;}
            }
    
    		//Returns "unknown valid queue reference" as a valid reference
    		return testQueue;  
    }

    /**
     * Method that recovery a private queue enumerator from the MSMQ server references by this factory
     *
     * @return MSMQQueueEnumerator A private queue enumerator reference
     * @throws MSMQException Throw if an exception occurs when creates the private queue enumerator
     */
    public synchronized MSMQQueueEnumerator enumPrivateQueues() throws MSMQException {

        try{
            //Native invocation
            int hResult = jniMQEnumPrivateQueue();

            //Invocation success?
            if( hResult != MSMQConstants.MQ_OK )
                throw new MSMQException(hResult);

            //Create an array from the internal queue format name array recovery from JNI
            MSMQQueue[] queues = null;
            if( this.__internal_enum_formats_name__ != null && this.__internal_enum_formats_name__.length > 0 ){
                //Create MSMQQueue reference for all queue format names 
                queues = new MSMQQueue[this.__internal_enum_formats_name__.length];
                for( int i=0; i<=queues.length-1; i++ )
                    queues[i] = new MSMQQueue(this.__internal_enum_formats_name__[i]);
            }

            //Returns enumerator for array of private queues
            return new MSMQQueueEnumerator(queues);

        }finally{
            //Ensure to clean internal queue format name for next invocation (Thread safe with synchronized method)
            this.__internal_enum_formats_name__=null;
        }

    }

    /**
     * Internal method for communication between JNI an Java that add a new queue format name for queue enumerator
     */
    private void __addEnumFormatName__(){

        try{
            int nFormatsName = 1;
            if(this.__internal_enum_formats_name__ != null)
                nFormatsName=this.__internal_enum_formats_name__.length+1;

            //Guardo la enumeracion interna
            String[] vEnumFormatNames = new String[nFormatsName];
            if(this.__internal_enum_formats_name__ != null)
                System.arraycopy( this.__internal_enum_formats_name__, 0, vEnumFormatNames, 0, this.__internal_enum_formats_name__.length);

            //Creo el formato de nombre
            String msmqServer = this.__internal_format_name__.substring(0, this.__internal_format_name__.indexOf("\\") ); //$NON-NLS-1$
            if( getDirectAccessType(msmqServer).equals( MQ_DIRECT_ACCESS_TYPE.MQ_DIRECT_ACCESS_OS ) )
                this.__internal_format_name__=MSMQConstants.MQ_DIRECT_OS_DEF + this.__internal_format_name__;
            else
                this.__internal_format_name__=MSMQConstants.MQ_DIRECT_TCP_DEF + this.__internal_format_name__;

            //Guardo el nuevo elemento
            vEnumFormatNames[vEnumFormatNames.length-1] = this.__internal_format_name__;
            this.__internal_enum_formats_name__ = vEnumFormatNames;

        }catch (Throwable _throw) {
            //Controlo la excepcion, para que no provoque errores en C
            ;
        }
        finally{
            //Limpio la cadena
            this.__internal_format_name__=null;
        }
    }

    /**
     * Method that returns the direct access type for a MSMQ server
     *
     * @param msmqServer MSMQ server name
     * @return MQ_DIRECT_ACCESS_TYPE Direct access type
     */
    private MQ_DIRECT_ACCESS_TYPE getDirectAccessType( String msmqServer ){

        //Validate if msmqServer is an ip format
        return (msmqServer.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}"))?MQ_DIRECT_ACCESS_TYPE.MQ_DIRECT_ACCESS_TCP:MQ_DIRECT_ACCESS_TYPE.MQ_DIRECT_ACCESS_OS; //$NON-NLS-1$
    }

    /**
     * Method that returns the MSMQ server name references by this factory
     *
     * @return String Returns MSMQ server name
     */
    public String getMSMQServer(){

    	//Returns MSMQ server name
    	return this.msmqServer;
    }
    
    /**
     * Method that build the format name of a queue
     *
     * @param queueFullName Full queue name (xe: msmqServerName\private$\queueName)
     * @return Returns the queue format name build
     */
    private String buildQueueFormatName(String queueFullName){
        
        //Queue format name to build (xe: DIRECT_OS:msmqServerName\private$\queueName)
        String queueFormatName = ""; //$NON-NLS-1$    
            //Extract MSMQ server name
            String msmqServer = queueFullName.substring(0, queueFullName.indexOf("\\")); //$NON-NLS-1$
            //Search for direct access
            if( getDirectAccessType(msmqServer).equals( MQ_DIRECT_ACCESS_TYPE.MQ_DIRECT_ACCESS_OS ) )
                queueFormatName+=MSMQConstants.MQ_DIRECT_OS_DEF;
            else
                queueFormatName+=MSMQConstants.MQ_DIRECT_TCP_DEF;
            //Add full queue name
            queueFormatName+=queueFullName;
            
        //Returns the queue format name
        return queueFormatName;
    }
    
    /**
     * Method that build the format name of a queue
     *
     * @param msmqServer MSMQ Server
     * @param eQueueType Queue type
     * @param queueName Queue name
     * @return Returns the queue format name build
     */
    private String buildQueueFormatName(String msmqServer, MQ_QUEUE_TYPE eQueueType, String queueName){
        
        //Queue format name to build (xe: DIRECT_OS:msmqServerName\private$\queueName)
        String queueFormatName = ""; //$NON-NLS-1$            
            //MSMQ Server name
            queueFormatName+=msmqServer;
            //Queue type
            if( eQueueType.equals( MQ_QUEUE_TYPE.MQ_TYPE_PRIVATE ) )
                queueFormatName+=MSMQConstants.MQ_TYPE_PRIVATE_DEF;
            else
                queueFormatName+=MSMQConstants.MQ_TYPE_PUBLIC_DEF;
            //Queue name
            queueFormatName+=queueName;
            
        //Returns the queue format name
        return buildQueueFormatName(queueFormatName);
    }


// ** -------------------------------------------------------------- ** \\
//                            NATIVOS METHODS                           \\
// ** -------------------------------------------------------------- ** \\

    /**
     * Native method for create queues for <b>Microsoft® Message Queuing Server</b>
     *
     * @param queuePathName Queue path name
     * @param eTransactional Queue transactional type
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQCreateQueue(String queuePathName, int eTransactional);

    /**
     * Native method for lookup a queue for <b>Microsoft® Message Queuing Server</b>
     *
     * @param queueFormatName [OUT] If success, stores the queue format name found
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQLookupQueue(String queueFormatName);

    /**
     * Native method for enumerate private queues for <b>Microsoft® Message Queuing Server</b>
     *
     * @return int Information or error code returns for the JNI function (HRESULT)
     */
    private native int jniMQEnumPrivateQueue();
}

