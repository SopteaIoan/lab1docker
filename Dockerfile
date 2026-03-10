FROM eclipse-temurin:21
RUN apt-get update && apt-get install -y git
WORKDIR /app
CMD ["bash"]