package com.ll.feelko.domain.experience.application;

import com.ll.feelko.domain.experience.dao.AnswerRepository;
import com.ll.feelko.domain.experience.dao.ExperienceRepository;
import com.ll.feelko.domain.experience.entity.Answer;
import com.ll.feelko.domain.experience.entity.Experience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service

public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ExperienceRepository experienceRepository;

    public void createAnswer(Long experienceId, String content) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 체험을 찾을 수 없습니다."));

        Answer answer = new Answer();
        answer.setContent(content);
        answer.setExperience(experience); // 체험을 답변에 설정
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
}
