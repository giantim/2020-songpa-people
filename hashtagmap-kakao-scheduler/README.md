## 기능 목록
* 스케줄링
  * Zone을 DB에서 읽는다.
  * Zone은 임의로 지정한다.
  * Zone에 주기/활성화 상태가 정해져 있다.
    * 주기: 1주일에 한 번
    * 활성화 상태: Admin에서 수동으로 지정한다.
    * 데이터를 수집할 때 모든 카테고리에 해당하는 가게 데이터를 갱신한다.
  * Zone에는 지역 정보가 있다.
    * 서울시 ㅇㅇ구
* 데이터 갱신
  * update (created at)
  * (추가: 개업) kakao id로 존재여부 확인 후 insert