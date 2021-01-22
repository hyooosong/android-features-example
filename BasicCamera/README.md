## **✔ Basic Camera Setting**
<img src="https://github.com/hyooosong/AndroidPractice/blob/master/BasicCamera/basicCamera.gif" height="500"/>

## 🍀 File
- ### MainActivity.kt
  - Camera Setting
- ### activity_main.xml
  - Camera View
- ### res/xml/file_path.xml
  - Photo 저장 경로 설정
- ### AndroidManifest.xml
  - 권한 설정, provider 설정

## 💚 AndroidManifest.kt
```kotlin
// 카메라 권한 설정
<uses-permission android:name="android.permission.CAMERA" />  

// 저장 권한 설정 (이미지나 동영상을 기기의 외부 장치에 저장할경우)
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

// 카메라기능이 무조건적으로 필요하지 않을 경우
<uses-feature android:name="android.hardware.camera" android:required="false" />
```

## 💚 MainActivity.kt
- `takePictureIntent()` : 카메라 실행 함수
- `val photoFile: File? = createImageFile()` : 찍은 사진을 이미지 파일로 만들기
- `startActivityForResult` : 이미지 파일이 성공적으로 만들어지면 onActivityResult로 보냄
- `provider` : Android 7.0 Nougat 이후 버전은 provider를 추가해야 보안상 문제 없음

```kotlin
fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch (e: IOException){
                    Log.e("errorPhoto", e.toString())
                    null
                }

                photoFile?.also {
                    val photoUri: Uri = FileProvider.getUriForFile(
                            this,
                            "camera.basic", //본인 사용 패키지명 입력
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
```
<br>
   
### ❓ provider 설정
- AndroidManifest에 추가
- meta-data의 `@xml/file_paths` 생성
```kotlin
<application
   <provider
       android:name="androidx.core.content.FileProvider"
       android:authorities="camera.basic" //본인 사용 패키지명 입력
       android:exported="false"
       android:grantUriPermissions="true">
         <meta-data
         android:name="android.support.FILE_PROVIDER_PATHS"
         android:resource="@xml/file_paths" />
    </provider>
</application>
```

### ❓ res/xml/file_paths.xml
```kotlin
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    // 원하는 저장경로 (ex. storage, 패키지명 ..)
    <external-path name="storage/emulated/0" path="."/> 
</paths>
```

<br>
<br>

- `timestamp` : 파일 이름의 중복을 방지하기 위해 Date type으로 저장
```kotlin
@Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getOutputDirectory()
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }  
    }
```

<br>
<br>

- `OnActivityResult` : Data(이미지) 받아서 imageView에 뿌려주기
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val photoPathUri = currentPhotoPath.toUri().toString()
            binding.imageViewCamera.setImageUri(photoPathUri)
            galleryAddPic()
        }
    }
```

<br>
<br>

-`galleryAddPic()` : 이미지 갤러리에 저장
```kotlin
private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val file = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(file)
            sendBroadcast(mediaScanIntent)
        }
    }
```
