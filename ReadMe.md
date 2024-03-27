# `특강신청서비스`
## 설명
- 특강을 신청할 수 있는 서비스 개발
- RDBMS를 이용
- 각 기능 및 제약 사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.

# 요구사항 분석
![service-flow](./src/main/java/com/tdd/speciallectureapply/document/diagram/2.service-flow.svg)
- 특강선택 신청 API
  - `사용자아이디`와 `선택날짜`로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
  - ~~특강은 `4월 20일 토요일 1시` 에 열리며, 선착순 30명만 신청 가능합니다.~~
  - 신청자는 `날짜별로` 특강을 신청합니다.
  - 신청자는 해당날짜에 특강을 선택해서 특강을 신청할 수 있습니다.
  - 오늘이 지난 특강은 신청이 불가합니다.
  - 선택한날짜에 특강이없으면 신청이 불가합니다.
  - 신청자가 30명 초과라면 신청이 불가합니다.
  - 신청자가 30명 이하라면 특강신청정보에 저장됩니다.
  - 특강신청정보에 특강날짜에 같은 유저아이디가 존재하면 신청이 불가합니다.
  - 특강신청에 문제가 없으면 특강정보에 현재 특강신청수가 1이 더해져 특강정보를 업데이트합니다.

   
- 특강 신청 완료 여부 조회 API
  - 사용자아이디와 특강날짜 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
  - 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환합니다.

- 특강목록 조회 API
  - 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.

# ERD
![erd-image](./src/main/java/com/tdd/speciallectureapply/document/diagram/img.png)
- 특강신청정보
  - 특강신청날짜의 유저아이디는 고유하다
- 특강정보
  - 최대정원의 기본값은 30이다.
  - 신청자수의 기본값은 0이다.

- 정확하게 30 명의 사용자에게만 특강을 제공할 방법을 고민해 봅니다.