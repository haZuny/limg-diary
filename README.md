# Limg-Diary
오늘을 그려주는 웹 플랫폼🎈

# 배포
[배포 주소](https://hazuny.github.io/limg-diary)
- EC2 기간 종료 후, 서버 중단 예정

# 구동 영상
https://github.com/user-attachments/assets/ed0131fb-6e72-4633-9822-46202fff60f9

# 기능
림그일기는 당신의 하루를 그림으로 그려드립니다.
오늘 하루 당신이 겪고 느낀 일들을 기록하시면, 그에 어울리는 그림을 그려드립니다.
자세한 기능은 아래와 같습니다.

- 회원가입 및 로그인
  - 개인 정보 변경
- 일기 작성 및 그림 생성
  - 그림 생성시, 원하는 화풍을 선택 가능
  - 하루 평가를 기록
  - 태그 기능 지원
- 한달치 일기들을 요약, 생성된 그림 확인
- 이전 일기 확인 및 검색 가능
  - 날짜 검색
  - 키워드 검색
  - 태그 검색
- 업적 뱃지 제공: 특정 업적을 달성해면 뱃지 지급
  - 취득한 뱃지 및 취득 정보 확인
  - 업적 달성률 제공

# 개발 도구
- Backend
  - Spring Boot 3.3.2
  - Java JDK 21
  - DB
    - H2(개발 및 테스트)
    - MySQL(배포)
  - Deploy
    - AWS-EC2
- Frontend
  - React
  - JavaScript
  - Deploy
    - Github Page
- Open API
  - Karlo_Kakao Developers (그림 생성)
  - DeepL (번역) 

# 개발자
- [bingo1007](https://github.com/bingo1007)_Backend
- [haZuny](https://github.com/haZuny)_Backend, Frontend

# 느낀점
- 백엔드 개발자로서 처음으로 해본 프로젝트이기 때문에, 프로젝트를 진행하면서 처음으로 마주친 문제들이 많이 있었다.
- 해시태그 부분을 모델링하면서 N:M 관계를 모델링 할때, 중간에 연결 도메인을 만드는 방식을 처음으로 적용시켜 보았다.
- 프론트엔드와 협업할 때 발생할 수 있는 CORS 문제를 이해했다.
- 백엔드 서버를 배포하면서, Https 프로토콜을 설정해야 하는 필요성을 인지했다. 이번 프로젝트를 진행할 때는 다른 블로그를 찾아보며 어영부영 해결했지만, 프로젝트 완료 후 해당 부분을 더 공부해야할 필요성을 느꼈다.
