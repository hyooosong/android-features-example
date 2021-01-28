## 🍀 Room?
* 안드로이드 Jetpack이 제공하는 데이터베이스 아키텍처
* Object Relational Mappin, ORM 라이브러리   
데이터베이스의 객체를 자바 or 코틀린 객체로 매핑해줍니다.

### 🍀 Room의 세 가지 주요 구성요소

* Entity   
데이터베이스에서의 테이블 역할

* Dao   
데이터베이스에 접근하는 데 필요한 메서드 포함
ex. Query, Insert, Update, Delete ...

* Database   
RoomDatabase 클래스를 상속받는 abstract class
   
<hr>
    
### 📆 Todo File 예제 (kotlin)  

0. **build-gradle 설정**  
1. **.xml 파일 작성 (View)**  
2. **Todo.kt 작성 (data class)**, @Entity @PrimaryKey  
-> Room에서 사용하기 위한 객체 파일  
3. **TodoDao.kt 작성**, @Dao  
-> Todo에 대해 어떤 동작할 지 정의하는 인터페이스 파일  
4. **AppDatabase.kt 작성**, @Database (entities=[], version=1)  
-> RoomDatabase 상속, 추상 메서드 작성  
5. **Activity 작성**  
-> AppDatabase 변수 선언 (객체 생성)  

<br>

### 💚 build.gradle (app)
``` kotlin
dependencies {
  def room_version = "2.2.6"

  implementation "androidx.room:room-runtime:$room_version"
  kapt "androidx.room:room-compiler:$room_version"

  // optional - Kotlin Extensions and Coroutines support for Room
  implementation "androidx.room:room-ktx:$room_version"

  // optional - Test helpers
  testImplementation "androidx.room:room-testing:$room_version"
}
```

### 💚 activity_main.xml
<img src="https://images.velog.io/images/hyooosong/post/8d77137f-13e1-467c-b146-d6f1c05aaef5/Screenshot_1611829455.png" width=250>

### 💚 Todo.kt
```kotlin
@Entity
data class Todo(var todoList: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
```
❓ autoGenerate = true : 자동으로 PK값 1씩 증가

### 💚 TodoDAO.kt
```kotlin
@Dao
interface TodoDAO {
    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Query("SELECT todoList FROM Todo")
    fun getTodoList(): MutableList<String>

    @Insert
    fun insert(todo: Todo)
    ...
}
```

### 💚  AppDatabase.kt
```kotlin
@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): TodoDAO
}
```

### 💚  MainActivity.kt
```kotlin
db = Room.databaseBuilder(baseContext, AppDatabase::class.java, "TodoDB")
            .allowMainThreadQueries()
            .build()
```      
```kotlin
 // insert Todo
db.toDoDao().insert(Todo(editTextTodo.get().toString()))
 // show TodoList
binding.textViewTodoList.text = db.toDoDao().getTodoList().toString()
```
