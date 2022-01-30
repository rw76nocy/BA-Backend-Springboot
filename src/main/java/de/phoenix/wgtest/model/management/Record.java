package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table( name = "Record")
public class Record {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn( name = "category_id", nullable = false)
    private Category category;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    public Record() {

    }

    public Record(Date date, String description, Category category, Child child) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.child = child;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
