package com.mdx.rest.tree.controller.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdx.rest.tree.dto.node.NodeDTO;
import com.mdx.rest.tree.model.node.Node;
import com.mdx.rest.tree.service.node.NodeService;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;

/**
 *
 * @author Matheus Xavier
 */
@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext*.xml")
public class NodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NodeService nodeService;

    @InjectMocks
    private NodeController nodeController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(nodeController).build();
    }

    @Test
    public void testGetAllTreeSuccess() throws Exception {
        Node nodeA = new Node(1L, "A", "A", null, "A");

        when(nodeService.getAllTree()).thenReturn(nodeA);

        mockMvc.perform(get("/node"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("A")))
                .andExpect(jsonPath("$.description", is("A")))
                .andExpect(jsonPath("$.detail", is("A")))
                .andExpect(jsonPath("$.parentId", is(Matchers.nullValue())));

        verify(nodeService, times(1)).getAllTree();
        verifyNoMoreInteractions(nodeService);
    }

//    @Test
//    public void testGetByParentIdSuccess() throws Exception {
//        Node nodeA = new Node(1L, "A", "A", null, "A");
//        Node nodeB = new Node(2L, "B", "B", nodeA, "B");
//        Node nodeC = new Node(3L, "C", "C", nodeA, "C");
//
//        List<Node> nodes = Arrays.asList(nodeB, nodeC);
//
//        when(nodeService.listOneLevelOfNodes(nodeA.getId())).thenReturn(nodes);
//
//        mockMvc.perform(get("/node/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is("")));
////                .andExpect(jsonPath("$[0].id", is(2)))
////                .andExpect(jsonPath("$[0].code", is("B")))
////                .andExpect(jsonPath("$[0].description", is("B")))
////                .andExpect(jsonPath("$[0].detail", is("B")))
////                .andExpect(jsonPath("$[0].hasChildren", is(false)))
////                .andExpect(jsonPath("$[0].parentId", is(1)))
////                .andExpect(jsonPath("$[1].id", is(3)))
////                .andExpect(jsonPath("$[1].code", is("C")))
////                .andExpect(jsonPath("$[1].description", is("C")))
////                .andExpect(jsonPath("$[1].detail", is("C")))
////                .andExpect(jsonPath("$[1].hasChildren", is(false)))
////                .andExpect(jsonPath("$[1].parentId", is(1)));
//
//        verify(nodeService, times(1)).listOneLevelOfNodes(nodeA.getId());
//        verifyNoMoreInteractions(nodeService);
//    }
    
    @Test
    public void testSaveNodeSuccess() throws Exception {
        Node node = new Node(1L, "A", "A", null, "A");
        NodeDTO nodeDTO = new NodeDTO("A", "A", "A", null);

        when(nodeService.save(node)).thenReturn(node);

        mockMvc.perform(post("/node")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(nodeDTO)))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void testUpdateUserSuccess() throws Exception {
        Node node = new Node(1L, "A", "A", null, "A");

        when(nodeService.findById(1L)).thenReturn(node);

        NodeDTO nodeDTO = new NodeDTO(1L, "AB", "AB", "AB", null);

        when(nodeService.update(node)).thenReturn(node);

        mockMvc.perform(put("/node")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(nodeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        Node node = new Node(1L, "A", "A", null, "A");

        when(nodeService.findById(1L)).thenReturn(node);

        NodeDTO nodeDTO = new NodeDTO(1L, "AB", "AB", "AB", null);

        doNothing().when(nodeService).delete(node);

        mockMvc.perform(delete("/node/{id}", node.getId())
                .content(asJsonString(nodeDTO)))
                .andExpect(status().isOk());

        verify(nodeService, times(1)).findById(node.getId());
        verify(nodeService, times(1)).delete(node);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
