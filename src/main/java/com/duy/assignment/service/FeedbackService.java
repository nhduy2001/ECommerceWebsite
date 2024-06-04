package com.duy.assignment.service;

import com.duy.assignment.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO addFeedback(FeedbackDTO feedbackDTO);

    List<FeedbackDTO> getAllFeedbacks(int productId);
}
