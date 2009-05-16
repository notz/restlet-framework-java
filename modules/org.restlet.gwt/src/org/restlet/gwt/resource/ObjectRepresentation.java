/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.gwt.resource;

import java.io.Serializable;

import org.restlet.gwt.data.MediaType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

/**
 * Representation based on a serializable Java object. This internally reuses
 * the GWT-RPC serialization logic.
 * 
 * @author Jerome Louvel
 * @param <T>
 *            The class to serialize, see {@link Serializable}
 */
public class ObjectRepresentation<T extends Serializable> extends
        StringRepresentation {

    /** The wrapped object. */
    private T object;

    /** The target object class. */
    private Class<? extends RemoteService> rpcServiceClass;

    /**
     * Constructor for deserialization.
     * 
     * @param serializedObject
     *            The object serialization text.
     * @param rpcServiceClass
     *            The RPC service class to get the serializer from.
     */
    public ObjectRepresentation(String serializedObject,
            Class<? extends RemoteService> rpcServiceClass) {
        super(serializedObject, MediaType.APPLICATION_JAVA_OBJECT_GWT);
        this.rpcServiceClass = rpcServiceClass;
        this.object = null;
    }

    /**
     * Constructor for serialization.
     * 
     * @param object
     *            The object to serialize.
     * @param rpcServiceClass
     *            The RPC service class to get the serializer from.
     */
    public ObjectRepresentation(T object,
            Class<? extends RemoteService> rpcServiceClass) {
        super(null, MediaType.APPLICATION_JAVA_OBJECT_GWT);
        this.object = object;
        this.rpcServiceClass = rpcServiceClass;
    }

    /**
     * The wrapped object. Triggers the deserialization if necessary.
     * 
     * @return The wrapped object.
     */
    @SuppressWarnings("unchecked")
    public T getObject() {
        if ((this.object == null) && (getText() != null)) {
            try {
                // Create a stream reader
                SerializationStreamReader streamReader = getSerializationStreamFactory()
                        .createStreamReader(getText());

                // Deserialize the object
                this.object = (T) streamReader.readObject();
            } catch (Exception e) {
                this.object = null;
                e.printStackTrace();
            }

        }

        return object;
    }

    /**
     * Returns the serialization stream factory.
     * 
     * @return The serialization stream factory.
     */
    private SerializationStreamFactory getSerializationStreamFactory() {
        // Create the serialization stream factory
        return (SerializationStreamFactory) GWT.create(rpcServiceClass);
    }

    @Override
    public String getText() {
        if ((this.object != null) && (super.getText() == null)) {
            try {
                // Create a stream writer
                SerializationStreamWriter objectWriter = getSerializationStreamFactory()
                        .createStreamWriter();

                // Serialize the object
                objectWriter.writeObject(this.object);
                setText(objectWriter.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return super.getText();
    }

    /**
     * Sets the wrapped object.
     * 
     * @param object
     *            The wrapped object.
     */
    public void setObject(T object) {
        this.object = object;
    }

}
