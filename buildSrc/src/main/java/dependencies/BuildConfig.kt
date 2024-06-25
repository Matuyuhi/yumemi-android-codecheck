package dependencies

import org.gradle.api.JavaVersion

object BuildConfig {

    const val namespace = "jp.co.yumemi.android.code_check"
    const val appId = "jp.co.yumemi.android.codecheck"

    const val compileSdk = 34
    const val minSdk = 23
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val JvmTarget = "17"
    val compatibility = JavaVersion.VERSION_17
}
