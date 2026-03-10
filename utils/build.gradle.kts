plugins {
    id("java")
}
group = "com.jasper"
version = "1.0-SNAPSHOT"



subprojects {
    // 2. 统一配置所有子模块的 JUnit 5 依赖
    dependencies {
        // 引用 TOML 中的依赖组
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation(rootProject.libs.bundles.testing)
    }
}