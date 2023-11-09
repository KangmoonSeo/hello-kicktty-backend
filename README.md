# hello-kicktty-web-server
> 팀 헬로킥티

### 킥보드 군집화를 유도하는 실시간 주차 구역 제안 서비스 - 웹 서버

## API 명세서

1. 킥보드 리스트 조회
    - Method: `GET`
    - End Point: `/kickboards?lat=:float&lng=:float`
    - RequestBody: 
    - ResponseBody:
        ```
        {
            ”kickboards”:[
                {
                  “id”:int, 
                  “lat”:double, 
                  “lng”:double,
                  “cluster_number”:int, 
                  “danger”:bool
                }, 
                …
              ]
        }
        ```
    - 비고:쿼리 넣지 않으면 all kickboard 출력
2. 킥보드 정보 조회
    - Method: `GET`
    - End Point: `/kickboards/:id`
    - RequestBody: 
    - ResponseBody:
        ```
        {
          “id”:int, 
          “lat”:double,
          “lng”:double,
          “cluster_number”:int,
          “danger”:bool
        }
        ```
    - 비고:
3. 킥보드 주차
    - Method: `POST`
    - End Point: `/kickboards`
    - RequestBody: 
        ```
        {
          “id”:int,
          “lat”:double,
          “lng”:double
        }
        ```
    - ResponseBody:
        ```
        {
          “id”:int, 
          “lat”:double,
          “lng”:double,
          “cluster_number”:int,
          “danger”:bool
        }
        ```
    - 비고
4. 킥보드 대여
    - Method: `DELETE`
    - End Point: `/kickboards/:id`
    - RequestBody: 
    - ResponseBody:
    - 비고
