package com.ll.feelko.domain.experience.api;

import com.ll.feelko.domain.experience.application.ExperienceService;
import com.ll.feelko.domain.experience.dao.ExperienceImageJPQLRepository;
import com.ll.feelko.domain.experience.dto.ExperienceCreateDTO;
import com.ll.feelko.domain.experience.entity.Experience;
import com.ll.feelko.domain.experience.entity.ExperienceImage;
import com.ll.feelko.domain.experience.form.ExperienceCreateForm;
import com.ll.feelko.domain.wishlist.application.WishListService;
import com.ll.feelko.domain.wishlist.dto.WishListDto;
import com.ll.feelko.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final WishListService wishListService;
    private final ExperienceImageJPQLRepository jpqlRepository;

    @GetMapping("/{experienceId}")
    public String detail(@PathVariable(name = "experienceId") Long experienceId, Model model, @AuthenticationPrincipal SecurityUser user) {
        ExperienceImage images = jpqlRepository.getImages(experienceId);
        model.addAttribute("image", images);
        model.addAttribute("experience", experienceService.detail(experienceId));
        Optional<WishListDto> createWishListDto = wishListService.createWishListDtoIfLogined(user, experienceId);
        if (!createWishListDto.isEmpty()) {
            model.addAttribute("isWished", wishListService.checkWish(createWishListDto.get()));
            model.addAttribute("isLogined", true);
        } else {
            model.addAttribute("isLogined", false);
        }
        return "domain/experience/detail";
    }


    @GetMapping("/create")
    public String showCreatePage(ExperienceCreateForm form, Model model) {

        model.addAttribute("experience", form);
        model.addAttribute("Answer", form);
        return "domain/experience/create";
    }

    @PostMapping("/create")
    public String create(ExperienceCreateForm form, @AuthenticationPrincipal SecurityUser user) {

        Experience experience = experienceService.createExperience(ExperienceCreateDTO.builder()
                .memberId(user.getId())
                .title(form.getTitle())
                .location(form.getLocation())
                .descriptionText(form.getDescriptionText())
                .price(form.getPrice())
                .startDate(form.getStartDate())
                .endDate(form.getEndDate())
                .experienceClose(form.getExperienceClose())
                .headcount(form.getHeadcount())
                .answer(form.getAnswer())
                .build());

        experienceService.upload(form, experience);

        return "redirect:/experiences/" + experience.getId();
    }


    @ResponseBody
    @GetMapping("/list/{filename}")
    public Resource download(@PathVariable(name = "filename") String filename) throws MalformedURLException {
        String fullPath = experienceService.getFullPath(filename);

        return new UrlResource("file:" + fullPath);
    }

}
