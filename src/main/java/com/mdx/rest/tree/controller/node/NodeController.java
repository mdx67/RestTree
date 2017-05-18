package com.mdx.rest.tree.controller.node;

import com.mdx.rest.tree.dto.node.NodeDTO;
import com.mdx.rest.tree.dto.node.NodeDTOWithChildren;
import com.mdx.rest.tree.dto.node.NodeDTOWithoutChildren;
import com.mdx.rest.tree.exception.BusinessException;
import com.mdx.rest.tree.exception.node.NodeNotFoundException;
import com.mdx.rest.tree.excetion.rest.BadRequestException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mdx.rest.tree.model.node.Node;
import com.mdx.rest.tree.service.node.NodeService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Matheus Xavier
 */
@Controller
@RequestMapping("/node")
@Transactional
public class NodeController {

    @Autowired
    public NodeService nodeService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody NodeDTO nodeDTO) throws BadRequestException {
        try {
            Node node = buildEntity(nodeDTO);

            nodeService.save(node);

            return new ResponseEntity(node.getId(), HttpStatus.CREATED);
        } catch (BusinessException e) {
            e.printStackTrace();

            throw new BadRequestException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NodeDTO nodeDTO) throws BadRequestException {
        try {
            if (nodeDTO.getId() == null || nodeService.findById(nodeDTO.getId()) == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Node node = buildEntity(nodeDTO);

            nodeService.update(node);

            return new ResponseEntity(node.getId(), HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();

            throw new BadRequestException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) throws BadRequestException {
        try {
            nodeService.deleteRecursively(nodeService.findById(id));

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BadRequestException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{parentId}")
    public ResponseEntity getByParentId(@PathVariable("parentId") Long parentId) throws BadRequestException {
        try {
            return new ResponseEntity(buildNodeWithoutChildren(nodeService.listOneLevelOfNodes(parentId)), HttpStatus.OK);
        } catch (NodeNotFoundException e) {
            e.printStackTrace();

            throw new BadRequestException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity get() {
        Node node = nodeService.getAllTree();

        NodeDTO nodeDTO = null;

        if (node != null) {
            nodeDTO = buildNodeWithChildren(node);
        }

        return new ResponseEntity(nodeDTO, HttpStatus.OK);
    }

    private List<NodeDTOWithoutChildren> buildNodeWithoutChildren(List<Node> nodes) {
        List<NodeDTOWithoutChildren> nodesWithoutChildren = new ArrayList<>();

        nodes.forEach(node -> {
            ModelMapper modelMapper = new ModelMapper();

            NodeDTOWithoutChildren nodeWithoutChildren = modelMapper.map(node, NodeDTOWithoutChildren.class);

            nodesWithoutChildren.add(nodeWithoutChildren);
        });

        return nodesWithoutChildren;
    }
    
    private NodeDTOWithChildren buildNodeWithChildren(Node node) {
        ModelMapper modelMapper = new ModelMapper();

        NodeDTOWithChildren nodeWithChildren = modelMapper.map(node, NodeDTOWithChildren.class);

        if (node.getParent() != null) {
            nodeWithChildren.setParentId(node.getParent().getId());
        }

        return nodeWithChildren;
    }

    private Node buildEntity(NodeDTO nodeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Node node = modelMapper.map(nodeDTO, Node.class);

        if (nodeDTO.getParentId() != null) {
            Node parent = nodeService.findById(nodeDTO.getParentId());

            if (parent != null) {
                node.setParent(parent);
            }
        }

        return node;
    }
}
