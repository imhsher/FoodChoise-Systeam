# 使用官方的 OpenJDK 17 镜像作为基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将应用程序的 JAR 文件复制到容器中
COPY fc-api/target/fc-api.jar .

# 设置启动命令
CMD ["java", "-jar", "fc-api.jar"]
