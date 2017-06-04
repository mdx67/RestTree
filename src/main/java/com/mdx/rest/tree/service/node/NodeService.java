package com.mdx.rest.tree.service.node;

import com.mdx.rest.tree.model.node.Node;
import com.mdx.rest.tree.exception.BusinessException;
import com.mdx.rest.tree.exception.node.NodeNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matheus Xavier
 */
@Service
public class NodeService {

    private static final Logger LOG = Logger.getLogger(NodeService.class.getName());

    @PersistenceContext
    public EntityManager manager;

    public Node save(Node node) throws BusinessException {
        if (node.getParent() == null && existsMainNode()) {
            throw new BusinessException("Não é possível adicionar outro nó principal.");
        }

        manager.persist(node);

        return node;
    }

    public Node update(Node node) {
        if (node.getParent() == null && !node.equals(getMainNode())) {
            throw new BusinessException("Não é possível adicionar outro nó principal.");
        }
        
        return manager.merge(node);
    }

    public void delete(Node node) {
        manager.remove(node);
    }

    public Node findById(Long id) throws NodeNotFoundException {
        Node node = manager.find(Node.class, id);

        if (node == null) {
            throw new NodeNotFoundException("Nó não encontrado.");
        }

        return node;
    }

    public List<Node> listChildren(Long nodeId) {
        return listChildren(findById(nodeId));
    }

    public List<Node> listChildren(Node node) {
        return manager.createNamedQuery("Node.listByParent").setParameter("parent", node).getResultList();
    }

    public List<Node> listOneLevelOfNodes(Long parentId) {
        List<Node> nodes = listChildren(parentId);

        nodes.stream().forEach((n) -> {
            n.setHasChildren(!manager.createNamedQuery("Node.listIdByParent").setParameter("parent", n).setMaxResults(1).getResultList().isEmpty());
        });

        return nodes;
    }

    public Node getAllTree() {
        Node firstNode = getMainNode();

        return firstNode;
    }
    
    private Boolean existsMainNode() {
        return getMainNode() != null;
    }

    public Node getMainNode() {
        Node result = null;

        try {
            result = (Node) manager.createNamedQuery("Node.findFirstNode").getSingleResult();
        } catch (NoResultException e) {
            LOG.log(Level.SEVERE, "Nó principal não encontrado.");
        }

        return result;
    }
}
