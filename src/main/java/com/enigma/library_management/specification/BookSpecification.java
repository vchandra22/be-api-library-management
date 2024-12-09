package com.enigma.library_management.specification;

import com.enigma.library_management.dto.request.SearchBookRequest;
import com.enigma.library_management.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {
    public static Specification<Book> getSpecification(SearchBookRequest searchBookRequest) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.hasText(searchBookRequest.getTitle())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + searchBookRequest.getTitle().toLowerCase() + "%" ));
                }

                if (StringUtils.hasText(searchBookRequest.getAuthor())) {
                    try {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + searchBookRequest.getAuthor().toLowerCase() + "%" ));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid search parameter: " + searchBookRequest.getAuthor());
                    }
                }

                if (!predicates.isEmpty()) {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
