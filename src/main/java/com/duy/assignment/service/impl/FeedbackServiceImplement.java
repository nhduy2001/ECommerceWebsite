package com.duy.assignment.service.impl;

import com.duy.assignment.dto.FeedbackDTO;
import com.duy.assignment.entity.Feedback;
import com.duy.assignment.entity.Product;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.FeedbackMapper;
import com.duy.assignment.repository.FeedbackRepository;
import com.duy.assignment.repository.ProductRepository;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImplement implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FeedbackServiceImplement(FeedbackRepository feedbackRepository,
                                    FeedbackMapper feedbackMapper,
                                    UserRepository userRepository,
                                    ProductRepository productRepository) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        Optional<Product> existProduct = productRepository.findById(feedbackDTO.getProductId());
        Optional<User> existUser = userRepository.findUserByUsername(feedbackDTO.getUsername());
        if (existProduct.isEmpty() || existUser.isEmpty()) {
            throw new RuntimeException("Cannot find User or Product");
        }

        double newAverageRating = ((existProduct.get().getAverageRating()
                * feedbackRepository.countByProduct_ProductId(feedbackDTO.getProductId())
                + feedbackDTO.getRating())
                / (feedbackRepository.countByProduct_ProductId(feedbackDTO.getProductId()) + 1)
        );

        existProduct.get().setAverageRating(newAverageRating);

        productRepository.save(existProduct.get());

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback.setProduct(existProduct.get());
        feedback.setUser(existUser.get());

        return feedbackMapper.toDTO(feedbackRepository.save(feedback));
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacks(int productId) {
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByProduct_ProductId(productId);
        return feedbackMapper.toDTOs(feedbacks);
    }
}
