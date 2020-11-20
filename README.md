## 모바일 프로그래밍 TEAM 7
2020-2 국민대학교 소프트웨어학부 모바일 프로그래밍 팀 프로젝트
## 프로젝트 개요
월 별로 하나의 버킷 리스트를 등록하고 본인의 포도 색을 하나씩 채우며 버킷 리스트를 관리하기 위함.<br>
본래 목적은 다른 사람들의 버킷리스트 정보 공유를 위한 취지였으나 프로젝트 진행에 한계가 있다고 생각하여 본인만의 버킷리스트 관리를 위한 어플로 규모 축소.
## 개발 도구
### 프로그램 개발 환경 : Android Studio
### 개발 언어 : Kotlin
### 데이터베이스 관리 : Firebase
### 협업 플랫폼 : Github, Notion, KakaoTalk
<hr>

## 주요 기능
- Firebase Authentication 을 이용한 로그인 & 회원가입 기능
- 회원가입 시 입력받은 정보들을 Realtime Database를 이용하여 관리
- 하단 메뉴 바를 이용한 액티비티 전환
- Firebase RealTime Databse를 이용한 유저의 월별 버킷 리스트 내용 저장 및 삭제
- 메인 화면에서 포도 버튼 클릭 시 월별로 저장되어 있는 버킷 리스트 내용들을 가져와 상세 내용을 보여주는 다이얼로그 화면 출력
- 회원정보 수정
## 데이터베이스 저장 구조
<img width="565" alt="datastructure" src="https://user-images.githubusercontent.com/28581838/99689220-12acb180-2aca-11eb-9fc3-34b0ff9aac14.png">

## 팀원 소개

```

이희지

STUDENT NUMBER : 20163150
E-MAIL : heeji228@gmail.com

```

```

임성원

STUDENT NUMBER : 20171686
E-MAIL : sungwontoto@kookmin.ac.kr

```


```

차영호

STUDENT NUMBER : 20171707
E-MAIL : cyh6099@kookmin.ac.kr

```

## 디자인 협업 방법 : Figma Tool
<img widt="1024px" height="768px" alt="fig" src="https://user-images.githubusercontent.com/28581838/99693489-c31cb480-2ace-11eb-97e5-cb829e4a646d.png">

## 어플 사용 방법
- 회원정보 입력을 통한 회원가입 및 로그인
- 버킷 리스트 추가 페이지에서 월별 버킷 리스트를 등록
- 월별 버킷리스트 달성 시 체크박스를 통한 달성 여부 체크
- 월별 버킷리스트 달성 시 해당 월에 해당하는 포도 색을 하나씩 채워 나감
- 월별 포도 버튼 클릭 시 본인이 등록한 버킷 리스트 세부 내용 화면에 출력
<hr>

## 임성원 기능 구현 내용
### LoadingActivity
- Handler().postDelay() 함수를 이용하여 시간 지연 후 액티비티 전환
- activity_loading.xml 화면을 2초동안 보여주고 로그인 액티비티로 전환하는 Splash 기능 구현

### Bottom Navigation Menu
- BottomNavigationView를 이용하여 하단 메뉴 바 구성
  - 사용을 위해서 app 수준의 build.gradle 파일에 implementation 'com.android.support:design:28.0.0' 라이브러리 추가
- menu 폴더에서 하단 메뉴 바를 구성하는 bottom_navigation.xml 파일 구현
  - 메뉴 구성 이미지는 Android Studio의 Vector asset 이미지를 이용
- MainActivity, BucketListAcitivty에 하단 메뉴 바 적용
  - 두 개의 액티비티에 해당하는 xml 파일 하단에 <bottomnavigation.BottomNavigationView> 태그를 이용하여 메뉴 바 적용
  - 버튼 클릭 시 화면 전환은 Intent 메소드를 통해서 액티비티 전환
  
### MainActivity : 상세 내용 보여주는 다이얼로그
- 포도 버튼 클릭 시 월별 버킷리스트 세부 내용을 보여주는 다이얼로그 작성
- layout 폴더의 custom_dialog.xml 파일을 작성하여 다이얼로그 형태를 구현
  - custom_dialog.xml 파일에서 버튼 등 위젯 디자인은 drawable 파일에서 shape태그를 이용하여 xml 파일 구현 (content_border, dialog_circle)
- MainActivity.kt 에서 oncreate() 함수가 실행되면 유저의 1~12월 버킷 리스트 제목을 배열에 저장
  - 포도 버튼 클릭 시 월에 해당하는 리스트 인덱스를 참조하여 값이 있는지 확인
    - null일 경우에는 리스트가 등록되지 않은 것으로 확인하고 경고 메세지 출력
    - 값이 있다면 해당 월에 해당하는 버킷 리스트가 존재한다는 의미이므로 showDialog() 함수에 월, 월에 맞는 이미지를 인자로 전달
- showDialog() 함수는 인자로 전달받은 월의 버킷 리스트 제목과 상세 내용 정보를 데이터베이스로부터 가져와 변수에 저장
  - 가져온 데이터 정보와 월에 맞는 이미지를 위에서 만든 custom_dialog.xml 파일의 각각의 위젯들에 값을 저장
  - Dialog의 SetContentView() 함수를 통해서 위에서 정의한 다이얼로그 xml 파일을 화면에 출력
  - 화면에 표시된 다이얼로그는 닫기 버튼 클릭 시 dismiss() 함수를 호출하여 다이얼로그를 종료

### MainActivity : 달성 여부에 따른 포도 색상 변경
- 월별 버킷 리스트 달성 여부에 따른 포도 색상 변경 기능
- 액티비티가 oncreate() 함수를 호출하면 유저의 데이터베이스에서 1~12월에 해당하는 버킷 리스트 달성 여부 정보를 가져옴
  - db에 저장되어 있는 achievement 가 false이면  해당 버킷 리스트를 달성하지 못했다는 뜻이므로 해당 포도의 ImageView의 이미지를 non_complete.xml 디자인으로 설정
  - achievement 가 true이면 버킷 리스트를 달성했다는 뜻이므로 ImageView의 이미지를 circle.xml 디자인으로 변경
- 달성 여부에 따른 포도 색상 변경은 해당 액티비티가 실행될 때 계속해서 데이터 정보를 참조하기 때문에 버킷 리스트 페이지에서 체크 되어있는 리스트의 체크박스를 풀면 바로 non_complete.xml 이미지로 적용됨

### SignUp.kt 회원가입 시 비밀번호 유효성 검사 기능
- 숫자,문자,특수문자를 포함하고 비밀번호 길이 제한을 지정한 정규식을 변수에 저장
  - 비밀번호 길이는 8자 이상 15자 이하로 제한
- Pattern객체의 Pattern.compile() 함수를 통해서 위에서 지정한 변수를 인자로 넣어 변수를 새로 만듦
- Matcher객체의 matcher() 함수를 이용하여 위 패턴 내용을 해석하여 일치 작업을 수행하는 새로운 변수 생성
- 회원가입 버튼 클릭 시 정보를 입력하지 않은 것이 있는지 확인하여 빈칸이 있다면 오류 메시지 출력
- 빈칸이 없을 때 비밀번호 문자열과 비밀번호 확인 문자열이 동일하지 않다면 비밀번호 매칭 오류 메시지 출력
- 이메일이 이메일 형식으로 작성되지 않았을 때 오류 메시지 출력
- 위에서 정한 비밀번호 유효성 조건을 만족하지 않았을 때 비밀번호 유효성 오류 메시지 출력

### MyInfo.kt 회원 로그아웃 기능
- 로그아웃 텍스트 클릭 시 로그아웃 확인 여부를 위한 AlertDialog 출력
  - 확인 버튼 클릭 시 파이어베이스의 내장 함수인 signOut() 함수를 이용하여 유저 로그아웃 실행
    - 로그아웃 완료 시 로그인 페이지로 액티비티 전환
  - 취소 버튼 클릭 시 아무것도 안하게 구현

<hr>

## 차영호 기능 구현 내용

### myInfo
- 내 정보를 출력하는 액티비티
- onCreate() 시 접속한 시간 기준 달을 불러온다. ex) 2020-12-11 에 접속시 12 라는 문자열을 잘라냄
  - 해당 월의 버킷리스트에 null값이라면 '등록된 리스트가 없다'는 메시지를 출력하고
  - 있다면 DB에 저장된 리스트 내용을 출력한다.
- onCreate() 시 DB에서 회원의 이름,핸드폰번호,이메일을 불러와 출력해준다.
  - 이 액티비티에서는 로그인을 하지 않을 경우 접속할 수 없기 때문에 null 체크는 하지 않는다.
- 회원정보 수정 클릭시 ModifyInfo 액티비티로 이동한다.
- back 버튼 클릭시 finish()를 통해 이전 액티비티로 돌아간다.

### ModifyInfo
- 나의 회원정보를 수정하는 액티비티
- onCreate()시 기존에 저장되어 있는 회원의 정보를 출력해준다.
  - email은 로그인을 위한 id이기 때문에 바꿀 수 없기에 출력해주지 않는다.
- 자신이 변경하고싶은 editText에 원하는 정보를 입력하여 정보를 수정할 수 있다.
  - 둘 중에 하나만 변경할 수 있게, 수정 버튼을 클릭 시 상황에 맞는 예외처리를 수행한다.
    1. 둘 다 입력하지 않고 클릭 시, 경고창을 띄우고 아무 행동도 하지 않는다.
    2. 이름 정보만 입력한 경우 updateChildren메소드를 통해 haspMap에 이름 값을 넣어준 뒤 DB에 변경된 정보만 업데이트 한다.
    3. 핸드폰 정보 같은 경우도 동일한 방식으로 진행한다.
    4. 두 정보 모두 변경할 때도 동일한 방식으로 진행한다.
- 정상적인 입력으로 수정이 완료되면 myInfo액티비티로 이동한다.
  - 정상적으로 정보가 바뀐 것을 확인할 수 있다.
- back버튼 클릭 시, myInfo액티비티로 돌아간다.

### Signup.kt
- 어플을 사용하기 위해 회원가입을 하는 액티비티.
- firebase authentication을 활용한다.
- 회원가입을 위해 필수적으로 필요한 id(email), password를 입력받고, 회원정보에 필요한 이름,핸드폰 번호를 입력받는다.
- 만일 email형식이 아닌 (@없는 경우 등등) 경우에 Toast 메시지를 통해 알려주고 회원가입 처리를 하지 않는다.
- DB에는 회원가입시 생성되는 고유 uid를 키값으로 안에 email,name,phone 정보를 저장한다.
  - uid를 키 값으로 할 시 회원간 중복을 피할 수 있다.
  - 이 과정은 회원가입 성공시에만 일어나야 하기 때문에 task.isSuccessful 안에서 함수를 호출해 동작하게 했다.
- 회원가입이 성공적으로 이루어지면 Toast메시지와 함께 MainActivity로 이동한다.

### side menu
- MainActivity 좌측 상단에 햄버거 버튼을 통해 slide형식의 menu를 만들었다.
- DrawerLayout을 통해 구현하였으며, ActionBarDrawerToggle을 활용하여 열고 닫을 수 있다.
