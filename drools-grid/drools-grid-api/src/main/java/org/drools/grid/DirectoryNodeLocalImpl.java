/*
 *  Copyright 2010 salaboy.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.drools.grid;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;

/**
 *
 * @author salaboy
 */
public class DirectoryNodeLocalImpl implements DirectoryNodeService {

    private Map<String, String> executorsMap = new HashMap<String, String>();
    private Map<String, String> kbasesMap = new HashMap<String, String>();
    private Map<String, KnowledgeBase> kbasesInstancesMap = new HashMap<String, KnowledgeBase>();

    public DirectoryNodeLocalImpl() {
    }

    public String getId() throws ConnectorException {
        return "Local:Directory:";
    }

    @Override
    public void register(String executorId, String resourceId) throws ConnectorException, RemoteException {
        executorsMap.put(executorId, resourceId);

    }

    @Override
    public void register(String executorId, GenericNodeConnector resourceConnector) throws ConnectorException, RemoteException {

        executorsMap.put(executorId, resourceConnector.getId());


    }

    @Override
    public GenericNodeConnector lookup(String executorId) throws ConnectorException, RemoteException {
        String nodeConnectorId = (String) executorsMap.get(executorId);

        return GenericConnectorFactory.newNodeConnector(nodeConnectorId);
    }

    @Override
    public void registerKBase(String kbaseId, KnowledgeBase kbase) throws ConnectorException, RemoteException {
        this.kbasesMap.put(kbaseId, "local");
        this.kbasesInstancesMap.put(kbaseId, kbase);
    }

    @Override
    public void registerKBase(String kbaseId, String resourceId) throws ConnectorException, RemoteException {
        this.kbasesMap.put(kbaseId, resourceId);
    }

    @Override
    public KnowledgeBase lookupKBase(String kbaseId) throws ConnectorException, RemoteException {
        String resourceId = this.kbasesMap.get(kbaseId); //based on the id I should create a kbase client
        if (resourceId.equals("local")) {
            return this.kbasesInstancesMap.get(kbaseId);
        }

        return KnowledgeBaseClientFactory.newKnowledgeBaseClient(resourceId);

    }

    @Override
    public Map<String, String> getExecutorsMap() throws ConnectorException, RemoteException {
        return this.executorsMap;
    }

    @Override
    public String lookupId(String resourceId) {
        return this.executorsMap.get(resourceId);
    }

  

    public DirectoryNodeService getDirectoryNodeService() throws ConnectorException {
        return this;
    }

   

   

    @Override
    public void unregister(String executorId) throws ConnectorException, RemoteException {
        executorsMap.remove(executorId);
    }

    @Override
    public Map<String, String> getKBasesMap() throws ConnectorException, RemoteException {
        return kbasesMap;
    }

    @Override
    public void unregisterKBase(String kbaseId) throws ConnectorException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() throws ConnectorException, RemoteException {
        //Do nothing ??
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.LOCAL;
    }

    
}