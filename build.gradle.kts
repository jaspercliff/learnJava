plugins {
    id("java-library")
}

group = "com.jasper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    // 1. 自动为子模块应用 Java 插件
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        // 引入日志捆绑包
        implementation(rootProject.libs.bundles.logging)
        // 引用 TOML 中的依赖组
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation(rootProject.libs.bundles.testing)

// ===========  lombok
        compileOnly(rootProject.libs.lombok)
        testCompileOnly(rootProject.libs.lombok)
        // 引入注解处理器
        annotationProcessor(rootProject.libs.lombok)
        testAnnotationProcessor(rootProject.libs.lombok)
// =========== base
        // 除了 base 模块自己，其他所有模块都自动导入base
        if (project.path != ":base") {
            api(project(":base"))
        }
    }

    // 3. 统一开启 JUnit 5 测试引擎支持
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.test {
    useJUnitPlatform()
}
