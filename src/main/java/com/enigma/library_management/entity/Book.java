package com.enigma.library_management.entity;

import com.enigma.library_management.constant.Constant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name = Constant.BOOK_TABLE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Temporal(TemporalType.DATE)
    @Column(name = "year", nullable = false)
    private Date year;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @ManyToMany
    @JoinTable(
            name = Constant.BOOK_CATEGORY_TABLE,
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
}
