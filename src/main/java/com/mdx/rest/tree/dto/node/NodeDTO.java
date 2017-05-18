package com.mdx.rest.tree.dto.node;

/**
 *
 * @author Matheus Xavier
 */
public class NodeDTO {

    private Long id;
    private String code;
    private String description;
    private String detail;
    private Long parentId;

    public NodeDTO() {
    }

    public NodeDTO(String code, String description, String detail, Long parentId) {
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.parentId = parentId;
    }

    public NodeDTO(Long id, String code, String description, String detail, Long parentId) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
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
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
