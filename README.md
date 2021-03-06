## repeat-java-qna 저장소 생성하기 
- 현재 java-qna 저장소가 1개 fork되어 있어서 새로운 원격저장소에 fork를 할 수 없는 상황. 우선 guswns1659/java-qna 의 master 브랜치만 클론하고 origin 주소를 guswns1659/java-qna -> guswns1659/repeat-java-qna로 변경할 예정.
- 먼저 기존 origin 삭제하고 새롭게 추가.
 
### 리모트 관련 git 명령 
- git remote remove <origin 이름>
- git remote add <origin 이름> <주소>

## Auto-reload 설정 
- Setting -> build, ~~ -> Compiler -> 'Build project automatically' 체크 
- registry 검색 -> compiler.automake.allow.when.app.running 체크. 
- [구글 확장 프로그램 LiveReload 설치](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei?hl=ko)
- build.gradle에 의존성 추가 : developmentOnly 'org.springframework.boot:spring-boot-devtools'
- application property 수정 
    - spring.devtools.livereload.enabled=false
    - spring.devtools.restart.enabled=true
    - spring.thymeleaf.cache=false
- Edit configuration에 들어가서 On 'Update' action과 On frame deactivation 설정 

## build.gradle 속 글씨가 흐릿하게 보일 때 
- gradle 파일을 지우고 인텔리J를 타시 켠다. build.gradle 들어가서 인텔리j의 추천을 받아 gradle setup을 다시 한다. 
- dependency를 실행한다. 그러면 `Deprecated Gradle features were used in this build, making it incompatible with Gradle 5.0`가 뜬다면 이 명령어로 deprecated feature를 찾는다. `./gradlew --warning-mode=all`. 이렇게 입력하면 build.gradle의 몇번째 라인의 의존성이 시간이 지났는지 알 수 있다. 

```java
Starting a Gradle Daemon, 3 incompatible and 1 stopped Daemons could not be reused, use --status for details

> Configure project :
The compile configuration has been deprecated for dependency declaration. This will fail with an error in Gradle 7.0. Please use the implementation configuration instead.
        at build_26rrzlka6ebx28eebf9skfwdv$_run_closure3.doCall(/home/hyunjun/문서/repeat-java-qna/build.gradle:25)
        (Run with --stacktrace to get the full stack trace of this deprecation warning.)
```

## Springboot, gradle 서버 시작 클래스(QnaApplication)이 `Class 'com.example.NodeDriverKt' not found in module 'cordapp-example'` 이런 에러 발생할 때 
[스택오버플로우 참고 : https://stackoverflow.com/questions/49717001/error-class-com-example-nodedriverkt-not-found-in-module-cordapp-example](https://stackoverflow.com/questions/49717001/error-class-com-example-nodedriverkt-not-found-in-module-cordapp-example)
- 원인) Caching issue' of IntelliJ IDE라고 한다. 
- 방법1) File -> Invalidate Caches / Restrart 클릭 
- 방법2) 문제가 계속된다면 gradle 폴더를 지우고 프로젝트를 끈다. 다시 켜면 gradle setup이 자동으 로된다. 
