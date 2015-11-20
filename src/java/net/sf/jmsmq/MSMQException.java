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
 * Class thrown when a native MSMQ exception occurs. <b><a href="http://msdn.microsoft.com/library/en-us/msmq/html/ff917e87-05d5-478f-9430-0f560675ece1.asp?frame=true">Microsoft® Message Queuing Server</a></b>
 *
 * @author Jorge Ruesga
 */
public class MSMQException extends Exception {

    /**
     * Internal variables
     */
        //Properties
		private int hresult;            //HRESLT error code
        private String message;         //Error description

        private static final long serialVersionUID = -6351652701377514953L;		//SerialVersionUID

	/**
	 * Constructor of <code>MSMQException</code>.
	 *
	 * @param hresult Return of the native function (HRESULT)
	 */
	public MSMQException(int hresult) {

		//Superconstructor invocation
		super();

		//Store the properties
		this.hresult = hresult;
        this.message = null;
	}

	/**
	 * Constructor of <code>MSMQException</code>.
	 *
	 * @param hresult Return of the native function (HRESULT)
	 * @param message Error description for the HRESULT
	 */
	public MSMQException(int hresult, String message) {

		//Superconstructor invocation
		super();

		//Store the properties
		this.hresult = hresult;
        this.message = message;
	}

	/* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        
        //Retuns the error description
        return hResult2String(this.hresult,this.message);
    }

    /* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	public String getLocalizedMessage() {

		//Retuns the error description
		return getMessage();
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {

		//Retuns the error description
		return getLocalizedMessage();
	}

    /**
     * Method that returns a composed description of a MSMQ native error
     *
     * @param hr Return of the native function (HRESULT)
     * @param message Aditional messahe
     * @return String Error description
     */
    private String hResult2String(int hr, String message) {

        //Retuns the error description
        return MSMQResources.getError( hr ) + ((message!=null)?" ("+ message + ")":"");  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Método que devuelve la propiedad hresult
     *
     * @return int Devuelve la propiedad hresult.
     */
    public int getHResult() {
        //Se devuelve el valor de la propiedad
        return this.hresult;
    }
}
