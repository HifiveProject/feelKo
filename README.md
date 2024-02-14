## 멤버
### Backend

|                                                  전화철                                                   |                                                  김재형                                                  |                                                   박태훈                                                   |                                                  이유진                                                  |                                                  정용화                                                   |                                                  황중석                                                   |
|:------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="daeun" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="mark" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="neozal" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="koda" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="kevin" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/117714970?v=4" alt="kevin" width="100" height="100"> |
|                                 [J-Cheol](https://github.com/J-Cheol)                                  |                               [himari7](https://github.com/himari7)                               |                                  [pth2134](https://github.com/pth2134)                                  |                                   [UJIN901](https://github.com/UJIN901)                                   |                                  [jyh1108](https://github.com/jyh1108)                                   |                                                  [snow4ram](https://github.com/snow4ram)                                                  |

## 기술스택
### Frontend
![Frontend_stack](src/main/resources/static/images/readme/Frontend_stack.png)

### Backend
![Backend_stack](src/main/resources/static/images/readme/Backend_stack.png)

### Infra

## ERD
![img.png](img.png)

## 패키지 구조
```bash
📦src
┣ 📂main
┃ ┣ 📂java
┃ ┃ ┗ 📂com
┃ ┃ ┃ ┗ 📂ll
┃ ┃ ┃ ┃ ┗ 📂feelko
┃ ┃ ┃ ┃ ┃ ┣ 📂domain
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂chat
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂chatMessage
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WriteRequestBody.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WriteResponseBody.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatMessage.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatMessageRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatMessageService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂chatRoom
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ModifyRequestBody.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRoomController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatRoomListDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRoomMemberInfoDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatRoom.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatRoomMember.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRoomMemberId.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatRoomMemberRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRoomRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ChatRoomService.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂comment
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Recommendation.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂experience
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AnswerController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AnswerService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AnswerRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceCreateDTO.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Answer.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Experience.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceImage.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂form
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceCreateForm.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂main
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MainController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MainService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MainServiceImpl.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂Request
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberProfileUpdateRequest.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberRegisterRequest.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MypageController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberServiceImpl.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MypageService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MypageServiceImpl.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberLoginDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberProfileDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberProfileUpdateDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberRegisterDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReservationDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SocialLoginDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UploadedPageDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadReservationDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Member.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VirtualAccount.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂payment
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂response
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TossPaymentResponse.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reuqest
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TossPaymentRequest.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentApiService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentApiServiceImpl.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentDetailsRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentDetailDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Payment.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentProduct.java
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂wishlist
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishListController.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishListService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishListServiceImpl.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishListRepository.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishListDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishListPageDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishListSaveDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishList.java
┃ ┃ ┃ ┃ ┃ ┣ 📂global
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂common
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BaseEntity.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentStatus.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂init
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AppConfig.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜NotProd.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂security
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomOAuth2UserService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityUser.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SpringSecurityConfig.java
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂websocket
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSocketConfig.java
┃ ┃ ┃ ┃ ┃ ┗ 📜FeelKoApplication.java
┃ ┗ 📂resources
┃ ┃ ┣ 📂static
┃ ┃ ┃ ┣ 📂css
┃ ┃ ┃ ┃ ┣ 📂common
┃ ┃ ┃ ┃ ┃ ┣ 📜experienceList.css
┃ ┃ ┃ ┃ ┃ ┣ 📜footer.css
┃ ┃ ┃ ┃ ┃ ┣ 📜header.css
┃ ┃ ┃ ┃ ┃ ┣ 📜main.css
┃ ┃ ┃ ┃ ┃ ┗ 📜mypage-leftAside.css
┃ ┃ ┃ ┃ ┣ 📂experience
┃ ┃ ┃ ┃ ┃ ┣ 📜detail.css
┃ ┃ ┃ ┃ ┃ ┣ 📜ec_styles.css
┃ ┃ ┃ ┃ ┃ ┗ 📜style.css
┃ ┃ ┃ ┃ ┗ 📂template
┃ ┃ ┃ ┃ ┃ ┗ 📜template.css
┃ ┃ ┃ ┣ 📂images
┃ ┃ ┃ ┃ ┣ 📂readme
┃ ┃ ┃ ┃ ┃ ┣ 📜Backend_stack.png
┃ ┃ ┃ ┃ ┃ ┗ 📜Frontend_stack.png
┃ ┃ ┃ ┃ ┣ 📜favicon.ico
┃ ┃ ┃ ┃ ┣ 📜feelko2.png
┃ ┃ ┃ ┃ ┣ 📜menu-icon.svg
┃ ┃ ┃ ┃ ┣ 📜기본 프로필.jpg
┃ ┃ ┃ ┃ ┣ 📜로그인 이미지.png
┃ ┃ ┃ ┃ ┗ 📜찜 이미지.png
┃ ┃ ┃ ┗ 📂js
┃ ┃ ┃ ┃ ┣ 📂common
┃ ┃ ┃ ┃ ┃ ┣ 📜footer.js
┃ ┃ ┃ ┃ ┃ ┣ 📜header.js
┃ ┃ ┃ ┃ ┃ ┗ 📜main.js
┃ ┃ ┃ ┃ ┗ 📂member
┃ ┃ ┃ ┃ ┃ ┗ 📂mypage
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜check.js
┃ ┃ ┣ 📂templates
┃ ┃ ┃ ┣ 📂domain
┃ ┃ ┃ ┃ ┣ 📂chat
┃ ┃ ┃ ┃ ┃ ┗ 📂chatRoom
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜list.html
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜make.html
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜room.html
┃ ┃ ┃ ┃ ┣ 📂experience
┃ ┃ ┃ ┃ ┃ ┣ 📜create.html
┃ ┃ ┃ ┃ ┃ ┗ 📜detail.html
┃ ┃ ┃ ┃ ┣ 📂layout
┃ ┃ ┃ ┃ ┃ ┣ 📜footer.html
┃ ┃ ┃ ┃ ┃ ┣ 📜header.html
┃ ┃ ┃ ┃ ┃ ┣ 📜layout.html
┃ ┃ ┃ ┃ ┃ ┣ 📜mypage-leftAside.html
┃ ┃ ┃ ┃ ┃ ┗ 📜search.html
┃ ┃ ┃ ┃ ┣ 📂main
┃ ┃ ┃ ┃ ┃ ┣ 📜experienceList.html
┃ ┃ ┃ ┃ ┃ ┗ 📜mainpage.html
┃ ┃ ┃ ┃ ┣ 📂member
┃ ┃ ┃ ┃ ┃ ┣ 📂mypage
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜profile-update.html
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜reservation-details-page.html
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜reservation-list.html
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜uploadList.html
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜wishlist.html
┃ ┃ ┃ ┃ ┃ ┣ 📜login.html
┃ ┃ ┃ ┃ ┃ ┣ 📜mypage.html
┃ ┃ ┃ ┃ ┃ ┗ 📜register.html
┃ ┃ ┃ ┃ ┗ 📂payment
┃ ┃ ┃ ┃ ┃ ┗ 📜payments.html
┃ ┃ ┃ ┗ 📂global
┃ ┃ ┃ ┃ ┗ 📜historyBack.html
┃ ┃ ┣ 📜application-dev.yml
┃ ┃ ┣ 📜application-secret.yml
┃ ┃ ┗ 📜application.yml
┗ 📂test
┃ ┗ 📂java
┃ ┃ ┗ 📂com
┃ ┃ ┃ ┗ 📂ll
┃ ┃ ┃ ┃ ┗ 📂feelko
┃ ┃ ┃ ┃ ┃ ┣ 📂domain
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂experience
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ExperienceRepositoryTest.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MypageControllerTest.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MemberServiceImplTest.java
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MypageServiceImplTest.java
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂payment
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PaymentApiServiceImplTest.java
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂wishlist
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂application
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishListServiceImplTest.java
┃ ┃ ┃ ┃ ┃ ┗ 📜FeelKoApplicationTests.java
```