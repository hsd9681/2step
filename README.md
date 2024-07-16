#  2보 전진을 위한 1보 후퇴

## 💁🏻💁🏻‍♂️ 팀원구성
|                  홍성도                   |                   김경민                    |                      이은샘                       |                     홍준빈                      |
|:--------------------------------------:|:----------------------------------------:|:----------------------------------------------:|:--------------------------------------------:|
| [@hsd9681](https://github.com/hsd9681) | [@gminnimk](https://github.com/gminnimk) | [@eunsaemsaem](https://github.com/eunsaemsaem) | [@Hongjunbin](https://github.com/Hongjunbin) |
|              프론트, 카드 CRUD              |               컬럼 CD, 댓글 CR               |                 보드 CRUD, 보드 초대                 |                    사용자 인증                    |

<br>

## 📁 프로젝트 소개
### < Trello >

프로젝트 설명 : 업무를 시각적으로 표현하여 팀의 작업과 프로세스를 효율적으로 관리할 수 있도록 도와줍니다.
<br>
프로젝트 기간 : 2024.07.10 - 2024.07.16

🛠️ Tech Stack
* <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white">
* <img src="https://img.shields.io/badge/spring security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white">
* <img src="https://img.shields.io/badge/postman-FF6C37?style=flat-square&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=white">
* <img src="https://img.shields.io/badge/figma-F24E1E?style=flat-square&logo=figma&logoColor=white"> <img src="https://img.shields.io/badge/intellij idea-000000?style=flat-square&logo=intellijidea&logoColor=white">
* 버전: JDK 17

<details>
<summary> 구성요소 </summary>

- 보드 : 프로젝트 별로 분리하기 위해 사용합니다.
- 컬럼 : 진행 상태 별로 분리하기 위해 사용합니다.
- 카드
    1. 기능과 담당자 별로 분리하기 위해 사용합니다.
    2. 마감 일자를 지정하고 기능 별로 관련 내용을 모아 놓을 수 있어 카드만 보면 명확하게 알 수 있습니다.
</details>

<details>
<summary> 기능 명세서 </summary>

*(✔ 필수 기능 / ➕ 추가 기능)*

- **사용자 인증 기능**

  ✔ 로그인

  ✔ 로그아웃

  ✔ 회원가입
  <br><br>
- **프로필 관리 기능**

  ✔ 비밀번호 수정 (최근 사용한 세 개의 비밀번호와 다르게 설정)

  ✔ 프로필 수정
  <br><br>
- **보드 관리 기능**

  ✔ 보드 목록 조회

  ✔ 보드 생성

  ✔ 보드 수정

  ✔ 보드 삭제

  ✔ 보드 초대
  <br><br>
- **컬럼 관리 기능**

  ✔ 컬럼 생성

  ✔ 컬럼 삭제

  ✔ 컬럼 순서 이동
  <br><br>
- **카드 관리 기능**

  ✔ 카드 목록 조회

  ✔ 카드 생성

  ✔ 카드 수정

  ✔ 카드 삭제
  <br><br>
- **카드 상세 기능**

  ✔ 댓글 작성

  ✔ 댓글 조회
  <br><br>
- **최적화 (쿼리 최적화, 인덱싱)**
</details>

> #### ERD
> <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbVU3dK%2FbtsIAVO5lKk%2FpFIxE2TWwHCPCNoXaalf71%2Fimg.png" width="500px">

> #### 소감
> - 홍성도 <br>
>   이번 프로젝트를 하면서 처음으로 백엔드 개발과 프론트 개발을 같이하게 되었는데 처음 설계를 어떻게 하느냐에 따라 앞으로의 개발이 어떻게 진행이 되는지 영향을 준다는 것 을 느끼게 되었고,  백엔드에서 기능 코드를 구현하면서 많이 힘들었지만 좋은 결과를 얻을 수 있어서 너무 좋았습니다
> - 이은샘 <br>
>   평소에도 업무 관리와 관련한 툴에 관심이 많아서 더 재밌게 할 수 있었던 것 같고, 기존과는 조금 다른 CRUD를 구현해야 해서 생각을 좀 더 많이 하게 된 프로젝트인 것 같습니다. 프론트를 함께 구현해보니 쉽지 않았지만, 팀원들과 서로 질문하고 소통하며 어려운 문제들을 잘 해결할 수 있었다고 생각합니다. 최적화 부분에 많은 시간을 들이지 못해 아쉬운 부분도 있지만, 기간 내에 최선을 다했기 때문에 뿌듯한 결과물을 얻을 수 있었습니다.
> - 김경민 <br>
>   트렐로라는 프로젝트 협업 도구를 주제로 실제 서비스를 만드는 과정을 통해서 실무적 경험을 더 다양하게 쌓을 수 있어서 좋았습니다! 보드와 칼럼 그리고 카드, 댓글 각 기능에 대해 연관관계나 프로세스가 어떻게 구현되고 작동하는지에 대한 원리를 알 수 있었던 새로운 시간이었고 비록 매우 짧은 시간 안에 프로젝트를 완성해야 해서 생각했던 것보다 완성도를 스스로 높이지 못하였지만 지난 팀 프로젝트에서 맡아보지 못했던 기능들을 할 수 있어서 재밌었던 경험이었습니다! 팀원분들도 맡은 바를 소홀히 하시지 않고 끝까지 책임감을 가지고 하셔서 매우 든든하였고 협력과 의사소통에도 트러블이 없어서 작업하는 기간 동안 만족스러웠습니다! 남은 프로젝트 기간이나 부트 캠프가 끝나고서도 모두 잘 될 일만 있으실 겁니다!
> - 홍준빈 <br>
>   칸반 보드라는 신선한 주제에 대한 프로젝트를 진행 해보니 한 층 더 복잡해진 CRUD를 할 수 있어 좋았고, 테이블 간 관계 또한 한 층 더 어려워진 것을 느낄 수 있어서 배움이 많았습니다. 또한, 프론트엔드를 직접 구현해서 백엔드와 연결을 해보니 많은 어려움이 있었고, 프론트엔드 설계의 중요함을 깨닫게 된 순간이었습니다. 열심히 하시는 팀원분들 덕분에 지치지 않고 같이 끝까지 해낼 수 있어서 좋았습니다.
