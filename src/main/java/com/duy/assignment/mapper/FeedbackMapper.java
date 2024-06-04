package com.duy.assignment.mapper;

import com.duy.assignment.dto.FeedbackDTO;
import com.duy.assignment.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "product.productId", target = "productId")
    FeedbackDTO toDTO (Feedback feedback);

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "productId", target = "product.productId")
    Feedback toEntity (FeedbackDTO feedbackDTO);

    List<FeedbackDTO> toDTOs (List<Feedback> feedbacks);
}
