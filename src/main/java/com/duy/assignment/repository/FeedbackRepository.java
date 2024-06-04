package com.duy.assignment.repository;

import com.duy.assignment.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    int countByProduct_ProductId(int productId);
    List<Feedback> findFeedbacksByProduct_ProductId(int productId);
}
