# 1. 进入项目目录
cd /home/jasper/code/java/person/learnJava/basic/newFeature/9/moduleDemo


# 3. 编译 producer 模块
#-d mods/producer 指定编译后的 class 文件输出目录
#source: 保留源文件名（报错能看到是哪个 .java）。 排错
#lines: 保留行号（报错能看到第几行）
javac -g:source,lines -d mods/producer \
  producer/src/main/java/module-info.java \
  producer/src/main/java/com/jasper/api/OpenUtil.java \
  producer/src/main/java/com/jasper/internel/CloseUtil.java

# 4. 编译 consumer 模块(依赖 producer)
#--module-path mods
#指定模块搜索路径
#consumer 依赖 producer,需要在这里找到已编译的 producer 模块
javac -g:source,lines --module-path mods -d mods/consumer \
  consumer/src/main/java/module-info.java \
  consumer/src/main/java/com/jasper/Main.java

# 5. 使用 jlink 创建自定义运行时镜像
#一个是你的业务模块（mods），一个是 JDK 自己的模块库（jmods）
#jlink 会从 consumer 开始，把 producer 及其依赖的所有 JDK 模块
#（如 java.base）全部找出来。没被依赖的（如 java.sql, java.desktop）全部丢弃
jlink --module-path mods:\$JAVA_HOME/jmods \
  --add-modules consumer \
  --output output/myapp \
  --launcher run=consumer/com.jasper.Main\
  --strip-native-commands\
  --compress zip-9 \
  --no-header-files \
  --no-man-pages

# 分析编译好的 consumer 模块
jdeps --module-path mods -m consumer

# 6. 运行应用
./output/myapp/bin/run

cd /home/jasper/code/java/person/learnJava/basic/newFeature/9/moduleDemo/output

du -sh myapp



#--strip-debug：
#JDK 的类文件里包含了大量的本地变量表、行号表等调试信息。对于运行环境来说，这些是“废话”。
# 删掉它们后，.class 文件的体积会显著缩小。
#当你用 javac 编译代码时，默认会把很多方便程序员调试的信息塞进 .class 文件里 jstack 查看
#
#--compress (深度压缩)：
#它把所有模块重新打包，去除了冗余的数据块。
#
#--no-header-files & --no-man-pages：
#虽然这两个减得不多，但它们去掉了所有 C 语言相关的头文件和帮助文档，让镜像变得纯粹——只为了跑 Java 而存在
# 没有使用jni