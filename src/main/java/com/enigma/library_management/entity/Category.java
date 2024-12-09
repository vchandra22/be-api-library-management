package com.enigma.library_management.entity;

import com.enigma.library_management.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = Constant.CATEGORY_TABLE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Book> books = new ArrayList<>();
}
