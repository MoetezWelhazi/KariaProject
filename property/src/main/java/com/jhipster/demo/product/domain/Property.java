package com.jhipster.demo.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jhipster.demo.product.domain.enumeration.PropertyState;
import com.jhipster.demo.product.domain.enumeration.Visibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "location")
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PropertyState state;

    @Column(name = "coordinates_lat")
    private Double coordinatesLat;

    @Column(name = "coordinates_lng")
    private Double coordinatesLng;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private Visibility visibility;

    @NotNull
    @Column(name = "image_1", nullable = false)
    private String image1;

    @NotNull
    @Column(name = "image_2", nullable = false)
    private String image2;

    @Column(name = "image_3")
    private String image3;

    @Column(name = "image_4")
    private String image4;

    @Column(name = "image_5")
    private String image5;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "propertyId" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_property__tags",
        joinColumns = @JoinColumn(name = "property_id"),
        inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "properties" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Property id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Property ownerId(String ownerId) {
        this.setOwnerId(ownerId);
        return this;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return this.name;
    }

    public Property name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Property description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return this.address;
    }

    public Property address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return this.location;
    }

    public Property location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyState getState() {
        return this.state;
    }

    public Property state(PropertyState state) {
        this.setState(state);
        return this;
    }

    public void setState(PropertyState state) {
        this.state = state;
    }

    public Double getCoordinatesLat() {
        return this.coordinatesLat;
    }

    public Property coordinatesLat(Double coordinatesLat) {
        this.setCoordinatesLat(coordinatesLat);
        return this;
    }

    public void setCoordinatesLat(Double coordinatesLat) {
        this.coordinatesLat = coordinatesLat;
    }

    public Double getCoordinatesLng() {
        return this.coordinatesLng;
    }

    public Property coordinatesLng(Double coordinatesLng) {
        this.setCoordinatesLng(coordinatesLng);
        return this;
    }

    public void setCoordinatesLng(Double coordinatesLng) {
        this.coordinatesLng = coordinatesLng;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public Property visibility(Visibility visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getImage1() {
        return this.image1;
    }

    public Property image1(String image1) {
        this.setImage1(image1);
        return this;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return this.image2;
    }

    public Property image2(String image2) {
        this.setImage2(image2);
        return this;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return this.image3;
    }

    public Property image3(String image3) {
        this.setImage3(image3);
        return this;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return this.image4;
    }

    public Property image4(String image4) {
        this.setImage4(image4);
        return this;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return this.image5;
    }

    public Property image5(String image5) {
        this.setImage5(image5);
        return this;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setPropertyId(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setPropertyId(this));
        }
        this.reviews = reviews;
    }

    public Property reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public Property addReview(Review review) {
        this.reviews.add(review);
        review.setPropertyId(this);
        return this;
    }

    public Property removeReview(Review review) {
        this.reviews.remove(review);
        review.setPropertyId(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Property tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Property addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Property removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return getId() != null && getId().equals(((Property) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", ownerId='" + getOwnerId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", location='" + getLocation() + "'" +
            ", state='" + getState() + "'" +
            ", coordinatesLat=" + getCoordinatesLat() +
            ", coordinatesLng=" + getCoordinatesLng() +
            ", visibility='" + getVisibility() + "'" +
            ", image1='" + getImage1() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image4='" + getImage4() + "'" +
            ", image5='" + getImage5() + "'" +
            "}";
    }
}
