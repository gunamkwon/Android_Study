# File I/O



# SharedPreferences

### SharedPreferences?
  - 앱을 개발하다 보면, 간단한 설정 값이나 문자열같은 데이터들을 DB에 저장하기 부담스러운 경우가 있다.
  
  - 이러한 경우, Android Platform에서 간단한 데이터의 저장을 목적으로 제공하는 API인 **SharedPreferences**를 이용한다.
  
  - 보통 초기 설정값이나 자동로그인 여부를 저장하기 위해 사용한다.
  
  - SharedPreferences는 키-값 쌍의 데이터를 파일로 저장한다. \
    => 경로는 ```data/data/패키지명/shared_prefs/SharedPreference이름.xml``` 위치에 저장된다.
  
  - 내부저장소를 이용한다.\
    => 권한 설정이 필요없다.\
    => 앱이 삭제될 때 함께 삭제된다.

