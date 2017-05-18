package com.mdx.rest.tree.dto.node;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus Xavier
 */
public class NodeDTOWithChildren extends NodeDTO {
    
    private List<NodeDTOWithChildren> children = new ArrayList<>();
    
    public List<NodeDTOWithChildren> getChildren() {
        return children;
    }

    public void setChildren(List<NodeDTOWithChildren> children) {
        this.children = children;
    }
}
