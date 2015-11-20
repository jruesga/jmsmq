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
import java.io.InputStream;
import java.util.Properties;


/**
 * Class of resources of configuration and messages for implements native implementation. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author  Jorge Ruesga
 */
public final class MSMQResources {

    /**
     * Internal variables
     */
        //Properties
        private static Properties resources;

        //Private resource contants
            //Resource file name
            private static final String MQ_RESOURCE_MSGS = "jmsmq.properties"; //$NON-NLS-1$
            //Configuration properties startwith
            private static final String MQ_CONF_RESOURCE_PROP = "jmsmq.conf."; //$NON-NLS-1$
            //HRESULT message properties startwith
            private static final String MQ_ERROR_RESOURCE_PROP = "jmsmq.hr."; //$NON-NLS-1$

        //Message to returns when no resource has found or wasn´t available
        private static final String MQ_NO_RESOURCE_MSG = "[jmsqm] No resource available for "; //$NON-NLS-1$

    /**
     * Static procedure for loading resources
     */
    static{
    	//Recovers resource file reference
        InputStream is = null;
        try{
            //Open buffer for resource property
            is = MSMQResources.class.getResourceAsStream( MQ_RESOURCE_MSGS );

            //Load resource properties object
            resources = new Properties();
            resources.load(is);

        }catch (Throwable _throw) {
            //Not available resources
            resources = null;

        }finally{
            //Close buffers
            if( is != null ){
                try{
                    is.close();
                }catch (Throwable _throw) {;}
                is = null;
            }
        }
    }

    /**
     * Método that returns a property of the loaded MSMQ resources
     *
     * @param property Property name
     * @return String Value of the recovery property
     */
    public static String getResource( String property ){

    	//Check if resource is available
    	if( resources == null )
    		return MQ_NO_RESOURCE_MSG + "property \"" + MQ_CONF_RESOURCE_PROP + property + "\"."; //$NON-NLS-1$ //$NON-NLS-2$

    	//Returns property value
    	return resources.getProperty( MQ_CONF_RESOURCE_PROP + property );
    }

    /**
     * Método that returns a error description for a native MSMQ exception
     *
     * @param hr Return of the native function (HRESULT)
     * @return String Error description
     */
    public static String getError( int hr ){

    	//Check if resource is available
    	if( resources == null )
    		return MQ_NO_RESOURCE_MSG + "[0x" + Integer.toHexString(hr).toUpperCase() + "]"; //$NON-NLS-1$ //$NON-NLS-2$

    	//Returns error description
    	if( resources.containsKey( MQ_ERROR_RESOURCE_PROP + ("0x" + Integer.toHexString(hr).toUpperCase()) ) ) //$NON-NLS-1$
    		return resources.getProperty( MQ_ERROR_RESOURCE_PROP + ("0x" + Integer.toHexString(hr).toUpperCase()) ) + " [0x" + Integer.toHexString(hr).toUpperCase() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    	//No resource
    	return MQ_NO_RESOURCE_MSG + "[0x" + Integer.toHexString(hr).toUpperCase() + "]";  //$NON-NLS-1$//$NON-NLS-2$
    }
}

