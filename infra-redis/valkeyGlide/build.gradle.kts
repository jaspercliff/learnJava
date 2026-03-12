plugins {
    // 自动探测当前系统
    alias(libs.plugins.osdetector)
}
dependencies {
    implementation(rootProject.libs.valkey.glide) {
        artifact {
            // 底层涉及 Native 代码（C++/Rust） 的库
            // 指定是什么系统
            classifier = osdetector.classifier
        }
    }
}
