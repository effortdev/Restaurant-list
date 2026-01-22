# Restaurant-list

🛠 Docker 배포 및 트러블슈팅 기록
1. Docker 환경 구축 개요
목적: 개발 환경 컨테이너화를 통해 어떤 환경에서도 동일하게 프로젝트를 실행하고, Redis 등 추가 인프라 확장 기반 마련.

사용 이미지:

Build: gradle:8.5-jdk17

Runtime: eclipse-temurin:17-jdk-jammy (안정적인 Java 실행 환경)

2. 주요 트러블슈팅 (Troubleshooting)
❌ 이슈 1: Dockerfile 인식 불가 (the Dockerfile cannot be empty)
현상: 빌드 시 도커파일이 비어있다는 에러와 함께 빌드 실패.

원인: Windows 환경에서 파일 저장 시 인코딩 문제 또는 파일명 뒤에 숨겨진 .txt 확장자가 붙어 도커가 내용을 읽지 못함.

해결: Dockerfile 확장자를 제거하고 VS Code 등을 통해 UTF-8 형식으로 재저장하여 500B 이상의 정상 파일로 인식 성공.

❌ 이슈 2: 베이스 이미지 로드 실패 (not found)
현상: openjdk:17-jdk-slim 이미지를 찾을 수 없다는 에러 발생.

원인: 최근 Docker Hub의 라이브러리 정책 변경 및 네트워크 이슈로 특정 이미지 메타데이터 로드 실패.

해결: 더 널리 쓰이고 안정적인 eclipse-temurin:17-jdk-jammy 이미지로 교체하여 해결.

❌ 이슈 3: JAR 파일 접근 오류 (Unable to access jarfile /app.jar)
현상: 컨테이너는 실행되었으나 내부에서 .jar 파일을 찾지 못해 무한 재시작됨.

원인: WORKDIR /app 설정으로 인해 파일은 /app/app.jar에 복사되었으나, 실행 시 최상위 루트인 /app.jar를 호출하여 경로 불일치 발생.

해결: ENTRYPOINT의 경로에서 /를 제거하거나 ./app.jar로 수정하여 현재 작업 디렉토리 내의 파일을 실행하도록 수정.

-------------------------

🚀 CI/CD Pipeline (GitHub Actions & Docker)
본 프로젝트는 GitHub Actions와 Docker를 활용하여 빌드 및 이미지 빌드 자동화를 구축하였습니다.

🛠 자동화 흐름 (Workflow)
Trigger: main 브랜치에 코드가 push되면 GitHub Actions 워크플로우가 자동으로 실행됩니다.

Environment: Ubuntu 가상 환경에서 Java 17(Temurin) 및 Gradle 환경을 세팅합니다.

Build & Security:

GitHub Repository Secrets에 저장된 네이버 API 키(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET)를 환경 변수로 안전하게 주입합니다.

소스 코드에 키를 노출하지 않고 Gradle 빌드를 수행하여 실행 가능한 .jar 파일을 생성합니다.

Dockerizing:

Docker Hub에 로그인한 후, Dockerfile을 기반으로 프로젝트를 컨테이너 이미지로 빌드합니다.

Push: 빌드된 최신 이미지를 ktjone/restaurant-search:latest 태스크로 Docker Hub에 업로드합니다.

🔐 보안 관리 (Security)
Secret Masking: 네이버 API Client ID/Secret 및 Docker Hub 토큰과 같은 민감 정보는 GitHub Secrets를 통해 관리하여 외부 유출을 차단하였습니다.

Environment Variables: application.yml에서 ${VARIABLE_NAME} 형식을 사용하여 환경에 따라 유연하게 설정값이 주입되도록 설계하였습니다.