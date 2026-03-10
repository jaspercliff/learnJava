plugins {
    id("application")
}

application {
    mainClass.set("com.jasper.caffeineDemo.Loading")
}

dependencies {
    implementation(rootProject.libs.caffeine)
}
