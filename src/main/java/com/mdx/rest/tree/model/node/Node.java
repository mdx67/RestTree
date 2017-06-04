package com.mdx.rest.tree.model.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author Matheus Xavier
 */
@NamedQueries({
    @NamedQuery(name = "Node.findFirstNode", query = "select n from Node n where n.parent is null"),
    @NamedQuery(name = "Node.listByParent", query = "select n from Node n where n.parent = :parent"),
    @NamedQuery(name = "Node.listIdByParent", query = "select n.id from Node n where n.parent = :parent")
})
@Entity
public class Node implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String description;
    @OneToOne
    private Node parent;
    private String detail;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Node> children;
    @Transient
    private Boolean hasChildren;

    public Node() {
    }

    public Node(Long id) {
        this.id = id;
    }

    public Node(Long id, String code, String description, Node parent, String detail) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.parent = parent;
        this.detail = detail;
    }

    public Node(String code, String description, Node parent, String detail) {
        this.code = code;
        this.description = description;
        this.parent = parent;
        this.detail = detail;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getHasChildren() {
        return hasChildren != null ? hasChildren : !this.getChildren().isEmpty();
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.id, other.id);
    }
}
