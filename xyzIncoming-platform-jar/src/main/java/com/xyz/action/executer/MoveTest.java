package com.xyz.action.executer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.model.ContentModel;
import org.springframework.extensions.surf.util.I18NUtil;

/**
 * Move action executor.
 * <p>
 * Moves the actioned upon node to a specified location.
 *
 * @author Roy Wetherall
 */
public class MoveTest extends ActionExecuterAbstractBase
{
    public static final String NAME = "MoveTest";
    public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

    private ServiceRegistry serviceRegistry;
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * FileFolder service
     */
    private FileFolderService fileFolderService;
//    private ServiceRegistry serviceRegistry;

    /**
     * The node service
     */
    private NodeService nodeService;
//    private SearchService searchService;
//    private NodeLocatorService nodeLocatorService;
//
//    public void setNodeLocatorService(NodeLocatorService nodeLocatorService){
//        this.nodeLocatorService = nodeLocatorService;
//    }

    public void setFileFolderService(FileFolderService fileFolderService)
    {
        this.fileFolderService = fileFolderService;
    }

//    public void setSearchService(SearchService searchService){
//        this.searchService = searchService;
//    }
    /**
     * Sets the node service
     */
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList)
    {
        paramList.add(new ParameterDefinitionImpl(PARAM_DESTINATION_FOLDER, DataTypeDefinition.NODE_REF, false, getParamDisplayLabel(PARAM_DESTINATION_FOLDER)));
    }

    /**
     * @see org.alfresco.repo.action.executer.ActionExecuter#execute(Action, NodeRef)
     */
    public void executeImpl(Action testAction, NodeRef actionedUponNodeRef)
    {

        if (this.nodeService.exists(actionedUponNodeRef))
        {

            Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
            QName testQ = QName.createQName("{http://www.acme.org/model/content/1.0}programName");

            String testName = (String)properties.get(testQ);

//            StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE,"SpaceStore");
            StoreRef storeRef = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE;
            NodeRef rootRef = serviceRegistry.getNodeService().getRootNode(storeRef);
//            ResultSet results = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE,  "PATH:\"" + "/app:company_home/cm:Incoming/cm:" + "\"");

            if(testName.equals("Marketing")){
//                ResultSet results01 = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE,  "PATH:\"" + "/app:company_home/cm:Incoming/cm:Marketing" + "\"");
                List<NodeRef> refs = serviceRegistry.getSearchService().selectNodes(rootRef, "/app:company_home/cm:Incoming/cm:Marketing",
                        null, serviceRegistry.getNamespaceService(), false);
                NodeRef nodeRef01 = refs.get(0);
                testAction.setParameterValue(MoveTest.PARAM_DESTINATION_FOLDER, nodeRef01);
            }
            else if(testName.equals("Finance")){
                List<NodeRef> refs = serviceRegistry.getSearchService().selectNodes(rootRef, "/app:company_home/cm:Incoming/cm:Finance",
                        null, serviceRegistry.getNamespaceService(), false);
                NodeRef nodeRef01 = refs.get(0);
                testAction.setParameterValue(MoveTest.PARAM_DESTINATION_FOLDER, nodeRef01);
            }
            else if(testName.equals("Sales")){
                List<NodeRef> refs = serviceRegistry.getSearchService().selectNodes(rootRef, "/app:company_home/cm:Incoming/cm:Sales",
                        null, serviceRegistry.getNamespaceService(), false);
                NodeRef nodeRef01 = refs.get(0);
                testAction.setParameterValue(MoveTest.PARAM_DESTINATION_FOLDER, nodeRef01);
            }
            else{
                return;
            }

            NodeRef destinationParent = (NodeRef) testAction.getParameterValue(PARAM_DESTINATION_FOLDER);



            try
            {
                fileFolderService.move(actionedUponNodeRef, destinationParent, null);
            }
            catch (FileNotFoundException e)
            {
                // Do nothing
            }
        }
    }

}

