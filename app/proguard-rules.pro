## 서버 통신 관련 클래스와 인터페이스를 유지합니다.
#-keep class com.example.data.di.** { *; }
#-keep interface com.example.data.service.** { *; }
#
## Keep for the reflective cast done in EntryPoints.
## See b/183070411#comment4 for more info.
#-keep,allowobfuscation,allowshrinking @dagger.hilt.android.EarlyEntryPoint class *
#-keep class dagger.hilt.** { *; }
#
#-keepclassmembers class * {
#    @dagger.hilt.android.qualifiers.ApplicationContext *;
#    @dagger.hilt.android.lifecycle.HiltViewModel *;
#}

# data class
-keep class com.example.data.model.** { *; }