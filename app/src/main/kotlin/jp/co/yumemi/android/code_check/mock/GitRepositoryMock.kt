package jp.co.yumemi.android.code_check.mock

import jp.co.yumemi.android.code_check.data.repository.Owner
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.toLocalDateTime

// Ownerクラスのモックデータ
val mockOwner = Owner(
    login = "user123",
    id = 123456789L,
    avatarUrl = "https://example.com/avatar.jpg",
    url = "https://example.com/user"
)

// Repositoryクラスのモックデータ
val mockRepository = Repository(
    id = 123456789L,
    name = "Sample Repository",
    description = "This is a sample repository for testing purposes.",
    owner = mockOwner,
    url = "https://github.com/user123/sample",
    createdAt = System.currentTimeMillis().toLocalDateTime(),
    updatedAt = System.currentTimeMillis().toLocalDateTime(),
    starCount = 100,
    watchersCount = 50,
    language = "Kotlin",
    forksCount = 20,
    openIssuesCount = 5,
    licenceName = "MIT",
    topics = listOf("Android", "Compose", "Open Source")
)