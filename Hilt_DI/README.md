## 🍀 Dependency Injection, 의존성 주입   
* 외부에서 객체를 생성하고 전달해줌으로써 객체간의 의존성을 제거하고 결합도를 낮춤

### 💚bulde.gradle
// Project
```kotlin
buildscript {
   dependencies {
      classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
   }  
```
// Module(app)
```kotlin
plugins {
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}
dependencies {
    implementation "com.google.dagger:hilt-android:2.28-alpha"
    kapt "com.google.dagger:hilt-android-compiler:2.28-alpha"

    implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03'
    kapt 'androidx.hilt:hilt-compiler:1.0.0-beta01'
}
```

### 💚 Hilt Application (App.kt)
* `@HiltAndroidApp` : 컴파일 타임 시 표준 컴포넌트 빌딩에 필요한 클래스들을 초기화
```kotlin
@HiltAndroidApp
class App: Application() {
}
```
* manifests.xml 에 `android:name=".App"` 추가


### ✨ Hilt 에서 제공하는 표준 컴포넌트 hierarchy
<br>
<img src="https://user-images.githubusercontent.com/72931738/111063294-18068f80-84f1-11eb-8730-edd2e7b4279c.png" width="400" height="400" />
* 현재는 ApplicationComponent가 사라짐 -> SingletonComponent로 대체

### 💚 Hilt Modules (ActivityModule.kt, ApplicationModule.kt)
* `@InstallIn` : 표준 컴포넌트에 모듈 install    
  -> 어떤 컴포넌트에 install 할지 정해주어야 함
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {

    @ActivityHash
    @Provides
    fun provideHash() = hashCode().toString()
}
```

### 💚 AndroidEntryPoint
* `@AndroidEntryPoint` : 객체를 주입할 대상에게 어노테이션을 추가하여 injection 수행    
  -> Activity, Fragment, View, Service, BroadcastReceiver에 어노테이션을 추가할 수 있음   
```kotlin
  @AndroidEntryPoint
  class MainFragment : Fragment() {
  
  @Inject
    lateinit var applicationHash: String
  ...
```


### ✨ Inject 받는 변수가 동일한 type일 때  
* 어떤 객체를 주입해야 할 지 알 수 없어서 Exception 발생 
  -> 어노테이션 개별 설정하여 구별 필요    
1. Module에 어노테이션 설정
```kotlin
// ActivityModule.kt
@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {

    @ActivityHash
    @Provides
    fun provideHash() = hashCode().toString()
}
```
```kotlin
// ApplicationModule.kt
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule{

    @AppHash
    @Provides
    fun provideHash() = hashCode().toString()
}
```
2. create annotation (@Qualifier)
* `@Qualifier` 을 이용하여 사용할 의존 객체를 선택할 수 있도록 함
* `@Retention` 을 이용하여 어느 시점 까지 어노테이션의 메모리를 가져갈 지 설정    
```kotlin
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityHash
```
3. 사용
```kotlin
 @AppHash
 @Inject
 lateinit var applicationHash: String

 @ActivityHash
 @Inject
 lateinit var activityHash: String
```

  
### 💚 ViewModel Injection
* 기존 : `@ViewModelInject` 사용하여 constructor injection 실행   
  -> 현재 사라짐
```kotlin
class MainViewModel @ViewModelInject constructor( ... ) {
  ...
}
```
* 현재 : `@HiltViewModel` 사용하여 constructor injection 실행
```kotlin
@HiltViewModel
class MainViewModel @Inject constructor( ... ) {
  ...
}
```

 
