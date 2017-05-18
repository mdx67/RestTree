package com.mdx.rest.tree.dto.node;

/**
 *
 * @author Matheus Xavier
 */
public class NodeDTOWithoutChildren extends NodeDTO {
    
    private Boolean hasChildren;
    
    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
