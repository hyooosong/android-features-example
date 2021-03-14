## 🍀 Jetpack?
* Android 개발을 빠르게 도와주는 컴포넌트 라이브러리

## 🍀 Navigation
* UI 전환을 쉽게 구현하도록 도와주는 라이브러리

### 🍀 Navigation의 세 가지 구성요소
* Navigation Graph (xml)  
  모든 navigation 관련 정보가 하나의 중심 위치에 모여있는 XML 리소스

* NavHostFragment  
  Navigation Graph를 표시하는 빈 컨테이너

* NavController  
  Navigation Graph를 바탕으로 navigation 간 이동(액션) 담당

<hr>

### ✔ Jetpack Navigation
<img src="https://user-images.githubusercontent.com/72931738/111061564-572fe300-84e7-11eb-8728-fbbed6a0c2f6.gif" width="300" height="500" />

<br>

### 💚 build.gradle(app)
```kotlin
dependencies {
...

implementation 'androidx.navigation:navigation-fragment-ktx:2.3.3'
implementation 'androidx.navigation:navigation-ui-ktx:2.3.3'
}
```

### 💚 nav_graph.xml
* New Android Resource File 생성   
-> File name : nav_graph / resource type : navigation
* Fragment 추가 및 연결
<img src="https://user-images.githubusercontent.com/72931738/111061503-0c15d000-84e7-11eb-8611-48a5ae6a6ea8.png" width="400" height="300" />   

### 💚 activity_main.xml 에 NavHostFragment 추가
```kotlin
<fragment
    ...
    android:name="androidx.navigation.fragment.NavHostFragment"
    app:defaultNavHost="true"
    app:navGraph="@navigation/nav_graph"
    ... 
 />
```

### 💚 FirstFragment.kt
* NavController을 이용하여 nav_graph.xml 에서 연결한 action을 수행하도록 작성
```kotlin
 findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
```
<br>
<hr>
<br>

### ✨번외 ) Fragment 간 데이터 주고받기
* result를 수신하는 Fragment에 ResultListener 설정
```kotlin
 setFragmentResultListener("requestKey") { requestKey, bundle ->
            val data = bundle.getString("data", "")
            Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show()
        }
```
* 위 Fragment와 동일한 `requestKey` 를 사용하여 result 생성 
```kotlin
setFragmentResult("requestKey", bundleOf("data" to "Hello"))
```
