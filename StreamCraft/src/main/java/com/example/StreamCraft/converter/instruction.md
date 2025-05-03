📦 converter 패키지 설명서

이 디렉토리는 StreamCraft 프로젝트에서 사용자 등록 및 인증 관련 데이터 변환 처리를 담당합니다.
Service 또는 Security 계층에서 사용하는 엔티티/DTO 변환, 회원가입 프로세스 내 유효성 검사 및 사용자 초기화 로직을 포함하고 있습니다.

✅ 주요 클래스

🔹 UserConverter

사용자 회원가입 처리 로직을 캡슐화한 핵심 유틸리티 클래스입니다.

1. Form 로그인 방식 회원가입 (FormLoginUserRegister)

UserRegisterDto를 UserEntity로 변환하고, 비밀번호 암호화 및 이미지 업로드를 수행합니다.

닉네임, 이메일, 사용자명 중복 체크를 포함합니다.

가입 완료 시 CommonEntity를 함께 저장하여 공통 사용자 관리 기능 지원.

2. OAuth2 사용자 등록 (Oauth2UserRegister)

OAuth2 방식으로 로그인한 사용자에 대해 공통 정보를 CommonEntity로 저장합니다.

내부적으로 사용자 정보, 제공자(provider), 권한(role), 닉네임 등을 저장합니다.

📌 의존 객체

PasswordEncoder: 비밀번호 해싱

UserRepository: 기본 사용자 저장소

Oauth2UserRepository: OAuth2 사용자 저장소

CommonRepository: 공통 사용자 정보 저장소

S3Service: 프로필 이미지 업로드 처리

TransferModelMapper: DTO ↔ Entity 변환기

🧭 역할 요약

책임

설명

🔐 유효성 검사

사용자명, 이메일, 닉네임 중복 검사 수행

🔄 객체 변환

DTO → Entity 변환, ModelMapper 사용

☁️ 파일 처리

프로필 이미지 S3 업로드 및 저장 처리

🗂️ 공통 등록

모든 사용자 등록 시 CommonEntity로 이중 저장

💡 이 패키지는 비즈니스 로직(Service)에서 "사용자 저장 흐름"을 단순화하기 위한 중간 계층으로 사용되며, 확장성과 유지보수를 고려해 구성되어 있습니다.

