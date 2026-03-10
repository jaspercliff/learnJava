plugins {
    id("java")
}

group = "com.jasper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    // 1. 自动为子模块应用 Java 插件
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    // 2. 统一配置所有子模块的 JUnit 5 依赖
    dependencies {
        // 引用 TOML 中的依赖组
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation(rootProject.libs.bundles.testing)

//===========  lombok
        compileOnly(rootProject.libs.lombok)
        testCompileOnly(rootProject.libs.lombok)
        // 引入注解处理器
        annotationProcessor(rootProject.libs.lombok)
        testAnnotationProcessor(rootProject.libs.lombok)
        // 除了 common 模块自己，其他所有模块都自动导入 common
        if (project.path != ":common") {
            implementation(project(":common"))
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