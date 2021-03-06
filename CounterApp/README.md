## 🍀 MVVM?
비즈니스 로직과 프레젠테이션 로직을 UI로부터 분리하는 것

## 🍀 MVVM의 구조
- Model : 어플리케이션에서 사용되는 데이터와 그 데이터 처리

- View : 사용자에게 보여지는 UI

- ViewModel : View를 표현하기 위해 만든 View를 위한 Model

    → View를 나타내기 위한 Model과 데이터 처리 담당
    
    <br>

<img src="https://user-images.githubusercontent.com/72931738/109811631-6e144100-7c6e-11eb-924e-dbe9e6291acc.gif" width="300" height="500" />   

<br>

## 💚 build.gradle (app)
### DataBinding
```kotlin
 buildFeatures {
        dataBinding = true
    }
```

### ViewModel & LiveData
```kotlin
dependencies {
    def lifecycle_version = "2.2.0"
    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.fragment:fragment-ktx:1.3.0"
}
```
```kotlin
plugins {
    id 'kotlin-kapt'
}
```


## 💚 MainViewModel.kt
```kotlin
class MainViewModel : ViewModel() {
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = _count

    init {
        _count.value = 0
    }

    fun plus() {
        _count.value = _count.value?.plus(1)
    }

    fun sub() {
        _count.value = _count.value?.minus(1)
    }
}
```
* init 함수 부분이 없다면 _count 값 null로 에러 발생

## 💚 activity_main.xml
```kotlin
<layout>
  <data>
        <variable
            name="mainViewModel"
            type="com.example.counterapp.MainViewModel" />
  </data>
  ...
  
<TextView
android:text="@{Integer.toString(mainViewModel.count)}" />
  ...
  
<Button  
android:onClick="@{()->mainViewModel.plus()}" />
  ...
  
<Button  
android:onClick="@{()->mainViewModel.sub()}" />

  ...
</layout>  
```

## 💚 MainActivity.kt
```kotlin
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    ...
    
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.lifecycleOwner = this
    binding.mainViewModel = mainViewModel
   
```
