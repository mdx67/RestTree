package com.mdx.rest.tree.service.node;

import com.mdx.rest.tree.exception.BusinessException;
import com.mdx.rest.tree.exception.node.NodeNotFoundException;
import com.mdx.rest.tree.model.node.Node;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Matheus Xavier
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {

    @Mock
    private EntityManager manager;

    public NodeService nodeService = new NodeService();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(nodeService, "manager", manager);
    }

    @Test
    public void testSaveSuccess() {
        Node node = new Node("A", "A", null, "A");

        when(manager.createNamedQuery("Node.findFirstNode")).thenReturn(mock(Query.class));

        nodeService.save(node);

        verify(manager, times(1)).persist(node);
    }

    @Test(expected = BusinessException.class)
    public void testSaveError() {
        Node node = new Node("B", "B", null, "B");

        when(manager.createNamedQuery("Node.findFirstNode")).thenReturn(mock(Query.class));

        nodeService.save(new Node("A", "A", null, "A"));

        when(nodeService.save(node)).thenThrow(new BusinessException("Error"));

        nodeService.save(node);
    }

    @Test
    public void testUpdate() {
        Node node = new Node(1L, "A", "A", null, "A");

        when(manager.createNamedQuery("Node.findFirstNode")).thenReturn(mock(Query.class));
        when(nodeService.getMainNode()).thenReturn(node);
        
        nodeService.update(node);

        verify(manager, times(1)).merge(node);
    }

    @Test
    public void testDelete() {
        Node node = new Node(1L, "A", "A", null, "A");

        nodeService.delete(node);

        verify(manager, times(1)).remove(node);
    }

    @Test
    public void testFindByIdSuccess() {
        Node nodeA = new Node(1L, "A", "A", null, "A");

        when(manager.find(Node.class, 1L)).thenReturn(nodeA);

        Node node = nodeService.findById(1L);

        assertEquals(node, nodeA);

        verify(manager, times(1)).find(Node.class, 1L);
    }

    @Test(expected = NodeNotFoundException.class)
    public void testFindByIdError() {
        when(manager.find(Node.class, 1L)).thenReturn(null);

        nodeService.findById(1L);
    }
}
