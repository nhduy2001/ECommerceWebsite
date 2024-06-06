package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.FeedbackDTO;
import com.duy.assignment.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer/feedback")
public class CustomerFeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public CustomerFeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<?> addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return ResponseEntity.ok(feedbackService.addFeedback(feedbackDTO));
    }
}
