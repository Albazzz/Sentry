
-- 1. TẠO DATABASE
-- =========================
DROP DATABASE IF EXISTS Sentry;
CREATE DATABASE Sentry;
USE Sentry;

-- =========================
-- 2. TẠO BẢNG CHÍNH
-- =========================

-- Bảng Roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY,
    RoleName VARCHAR(50) UNIQUE NOT NULL
);

-- Bảng PremiumPlans
CREATE TABLE PremiumPlans (
    PlanID INT PRIMARY KEY AUTO_INCREMENT,
    PlanName VARCHAR(100),
    Price DECIMAL(10, 2),
    DurationInMonths INT,
    Description VARCHAR(255)
);

-- Bảng Users
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    RoleID INT,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255),
    FullName VARCHAR(100),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsActive TINYINT(1) DEFAULT 1,
    IsBan TINYINT(1) DEFAULT 0,
    PhoneNumber VARCHAR(20),
    JapaneseLevel VARCHAR(50),
    Avatar TEXT,
    Gender VARCHAR(10) DEFAULT 'Khác',
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- Bảng Teacher
CREATE TABLE Teacher (
    UserID INT PRIMARY KEY,
    TeacherPending TINYINT(1) DEFAULT 0,
    Certificate VARCHAR(500),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Bảng Payments
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT NOT NULL,
    PlanID INT NOT NULL,
    PaymentDate DATETIME NULL,
    ResponseCode VARCHAR(20) NULL,
    Status VARCHAR(50),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PlanID) REFERENCES PremiumPlans(PlanID)
);

-- Bảng UserPremium
CREATE TABLE UserPremium (
    UserID INT NOT NULL,
    PlanID INT NOT NULL,
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PlanID) REFERENCES PremiumPlans(PlanID)
);

-- Bảng Courses
CREATE TABLE Courses (
    CourseID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255),
    Description TEXT,
    IsHidden TINYINT(1) DEFAULT 0,
    IsSuggested TINYINT(1) DEFAULT 0,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    CreatedBy INT NULL,
    imageUrl TEXT NULL,
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID)
);

-- Bảng Enrollment
CREATE TABLE Enrollment (
    EnrollmentID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    CourseID INT,
    EnrolledAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- Bảng Lessons
CREATE TABLE Lessons (
    LessonID INT PRIMARY KEY AUTO_INCREMENT,
    CourseID INT,
    Title VARCHAR(255),
    Description VARCHAR(1000),
    IsHidden TINYINT(1) DEFAULT 0,
    StudyStatus INT DEFAULT 0,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- Bảng LessonMaterials
CREATE TABLE LessonMaterials (
    MaterialID INT PRIMARY KEY AUTO_INCREMENT,
    LessonID INT NOT NULL,
    MaterialType VARCHAR(50) NOT NULL,
    FilePath TEXT,
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng Vocabulary
CREATE TABLE Vocabulary (
    VocabID INT PRIMARY KEY AUTO_INCREMENT,
    Word VARCHAR(100),
    Meaning VARCHAR(255),
    Reading VARCHAR(100),
    Example TEXT,
    LessonID INT,
    imagePath VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng Kanji
CREATE TABLE Kanji (
    KanjiID INT PRIMARY KEY AUTO_INCREMENT,
    `Character` VARCHAR(10),
    Onyomi VARCHAR(100),
    Kunyomi VARCHAR(100),
    Meaning VARCHAR(255),
    LessonID INT,
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng Flashcards
CREATE TABLE Flashcards (
    FlashcardID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Title VARCHAR(100),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsPublic TINYINT(1) DEFAULT 0,
    Description VARCHAR(500),
    CoverImage VARCHAR(500),
    LessonID INT NULL,
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng FlashcardItems
CREATE TABLE FlashcardItems (
    FlashcardItemID INT PRIMARY KEY AUTO_INCREMENT,
    FlashcardID INT,
    VocabID INT NULL,
    Note VARCHAR(255),
    FrontContent VARCHAR(500),
    BackContent VARCHAR(500),
    FrontImage VARCHAR(500),
    BackImage VARCHAR(500),
    OrderIndex INT DEFAULT 0,
    FOREIGN KEY (FlashcardID) REFERENCES Flashcards(FlashcardID),
    FOREIGN KEY (VocabID) REFERENCES Vocabulary(VocabID)
);

-- Bảng Quizzes
CREATE TABLE Quizzes (
    QuizID INT PRIMARY KEY AUTO_INCREMENT,
    LessonID INT,
    Title VARCHAR(255),
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng Questions
CREATE TABLE Questions (
    QuestionID INT PRIMARY KEY AUTO_INCREMENT,
    QuizID INT NULL,
    QuestionText TEXT,
    TimeLimit INT DEFAULT 30,
    FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID)
);

-- Bảng Answers
CREATE TABLE Answers (
    AnswerID INT PRIMARY KEY AUTO_INCREMENT,
    QuestionID INT,
    AnswerText TEXT,
    IsCorrect TINYINT(1),
    AnswerNumber INT CHECK (AnswerNumber BETWEEN 1 AND 4),
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID)
);

-- Bảng UserAnswer
CREATE TABLE UserAnswer (
    ResultID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    QuestionID INT,
    UserAnswer TEXT,
    TakenAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID)
);

-- Bảng Conversations
CREATE TABLE Conversations (
    ConversationID INT PRIMARY KEY AUTO_INCREMENT,
    User1ID INT NOT NULL,
    User2ID INT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (User1ID) REFERENCES Users(UserID),
    FOREIGN KEY (User2ID) REFERENCES Users(UserID)
);

-- Bảng Messages
CREATE TABLE Messages (
    MessageID INT PRIMARY KEY AUTO_INCREMENT,
    ConversationID INT NOT NULL,
    SenderID INT NOT NULL,
    Content TEXT NOT NULL,
    Type VARCHAR(50) NOT NULL,
    IsRead TINYINT(1) DEFAULT 0,
    IsRecall TINYINT(1) DEFAULT 0,
    SentAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ConversationID) REFERENCES Conversations(ConversationID),
    FOREIGN KEY (SenderID) REFERENCES Users(UserID)
);

-- Bảng Blocks
CREATE TABLE Blocks (
    BlockerID INT NOT NULL,
    BlockedID INT NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (BlockerID, BlockedID),
    FOREIGN KEY (BlockerID) REFERENCES Users(UserID),
    FOREIGN KEY (BlockedID) REFERENCES Users(UserID)
);

-- Bảng LessonVocabulary
CREATE TABLE LessonVocabulary (
    LessonID INT NOT NULL,
    VocabID INT NOT NULL,
    PRIMARY KEY (LessonID, VocabID),
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID),
    FOREIGN KEY (VocabID) REFERENCES Vocabulary(VocabID)
);

-- Bảng Feedbacks
CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    CourseID INT,
    Content TEXT,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    Rating INT CHECK (Rating BETWEEN 1 AND 5) NOT NULL DEFAULT 5,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- Bảng FeedbackVotes
CREATE TABLE FeedbackVotes (
    VoteID INT PRIMARY KEY AUTO_INCREMENT,
    FeedbackID INT,
    UserID INT,
    VoteType INT CHECK (VoteType IN (1, -1)),
    FOREIGN KEY (FeedbackID) REFERENCES Feedbacks(FeedbackID)
);

-- Bảng CourseRatings
CREATE TABLE CourseRatings (
    RatingID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    CourseID INT,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment TEXT,
    RatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- Bảng Progress
CREATE TABLE Progress (
    ProgressID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    CourseID INT,
    LessonID INT,
    CompletionPercent INT CHECK (CompletionPercent BETWEEN 0 AND 100),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- Bảng UserToken
CREATE TABLE UserToken (
    AccessTokenID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    RefreshID VARCHAR(255),
    DeviceID VARCHAR(255),
    IPAddress VARCHAR(50),
    ExpiresAt DATETIME,
    IsRevoked TINYINT(1) DEFAULT 0,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


-- Bảng GoogleToken
CREATE TABLE GoogleToken (
    GGID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
USE Sentry;

-- ==========================================
-- 1. INSERT DỮ LIỆU MẪU CHO Roles
-- ==========================================
INSERT INTO Roles (RoleID, RoleName) VALUES
(1, 'FreeUser'),
(2, 'PremiumUser'),
(3, 'Teacher'),
(4, 'Admin');

-- ==========================================
-- 2. INSERT DỮ LIỆU MẪU CHO PremiumPlans
-- ==========================================
INSERT INTO PremiumPlans (PlanName, Price, DurationInMonths, Description)
VALUES
('Basic', 99.000, 1, 'Gói cơ bản 1 tháng'),
('Pro', 249.000, 3, 'Gói 3 tháng với nhiều ưu đãi'),
('Ultimate', 899.000, 12, 'Gói cao cấp 1 năm, full tính năng');

-- ==========================================
-- 3. INSERT DỮ LIỆU MẪU CHO Users
-- ==========================================
INSERT INTO Users (RoleID, Email, Password, FullName, PhoneNumber, JapaneseLevel, Gender)
VALUES
(4, 'huyphw2@gmail.com', 'admin123', 'Huy Phan', '0909009009', 'N2', 'Nam'), -- Admin
(1, 'user1@gmail.com', 'user123', 'Nguyễn Văn A', '0901111111', 'N5', 'Nam'),
(1, 'user2@gmail.com', 'user123', 'Trần Thị B', '0902222222', 'N4', 'Nữ'),
(3, 'teacher1@gmail.com', 'teach123', 'Cô Sakura', '0903333333', 'N1', 'Nữ');

-- ==========================================
-- 4. INSERT DỮ LIỆU MẪU CHO Teacher
-- ==========================================
INSERT INTO Teacher (UserID, TeacherPending, Certificate)
VALUES
(4, 0, 'JLPT N1 Certified, 5 years teaching experience');

-- ==========================================
-- 5. INSERT DỮ LIỆU MẪU CHO Courses
-- ==========================================
INSERT INTO Courses (Title, Description, CreatedBy, imageUrl)
VALUES
('Tiếng Nhật Cơ Bản N5', 'Khóa học nhập môn tiếng Nhật cho người mới bắt đầu', 4, '/images/course1.jpg'),
('Ngữ pháp N4', 'Tổng hợp ngữ pháp N4 quan trọng', 4, '/images/course2.jpg'),
('Từ vựng JLPT N3', 'Mở rộng vốn từ vựng tiếng Nhật N3', 4, '/images/course3.jpg');

-- ==========================================
-- 6. INSERT DỮ LIỆU MẪU CHO Lessons
-- ==========================================
INSERT INTO Lessons (CourseID, Title, Description)
VALUES
(1, 'Bài 1: Giới thiệu bản thân', 'Học cách giới thiệu bản thân bằng tiếng Nhật'),
(1, 'Bài 2: Chào hỏi và giao tiếp cơ bản', 'Học các mẫu câu chào hỏi trong giao tiếp hàng ngày'),
(2, 'Bài 1: 〜ながら', 'Cấu trúc chỉ hai hành động diễn ra đồng thời'),
(3, 'Bài 1: Từ vựng chủ đề Gia đình', 'Các từ vựng cơ bản về gia đình trong tiếng Nhật');

-- ==========================================
-- 7. INSERT DỮ LIỆU MẪU CHO Vocabulary
-- ==========================================
INSERT INTO Vocabulary (Word, Meaning, Reading, Example, LessonID)
VALUES
('わたし', 'Tôi', 'watashi', 'わたしは　フイ　です。', 1),
('せんせい', 'Giáo viên', 'sensei', 'あの　ひとは　せんせい　です。', 1),
('おはよう', 'Chào buổi sáng', 'ohayou', 'おはようございます。', 2),
('かぞく', 'Gia đình', 'kazoku', 'わたしの　かぞくは　4にん　です。', 4);

-- ==========================================
-- 8. INSERT DỮ LIỆU MẪU CHO Quizzes
-- ==========================================
INSERT INTO Quizzes (LessonID, Title)
VALUES
(1, 'Quiz: Giới thiệu bản thân'),
(2, 'Quiz: Chào hỏi cơ bản');

-- ==========================================
-- 9. INSERT DỮ LIỆU MẪU CHO Questions & Answers
-- ==========================================
INSERT INTO Questions (QuizID, QuestionText, TimeLimit)
VALUES
(1, 'Từ "わたし" có nghĩa là gì?', 30),
(2, 'Câu chào "おはようございます" dùng khi nào?', 30);

INSERT INTO Answers (QuestionID, AnswerText, IsCorrect, AnswerNumber)
VALUES
(1, 'Tôi', 1, 1),
(1, 'Bạn', 0, 2),
(1, 'Anh ấy', 0, 3),
(1, 'Chúng ta', 0, 4),
(2, 'Buổi sáng', 1, 1),
(2, 'Buổi trưa', 0, 2),
(2, 'Buổi tối', 0, 3),
(2, 'Khi đi ngủ', 0, 4);

-- ==========================================
-- 10. INSERT DỮ LIỆU MẪU CHO Feedbacks
-- ==========================================
INSERT INTO Feedbacks (UserID, CourseID, Content, Rating)
VALUES
(2, 1, 'Khóa học rất dễ hiểu và phù hợp với người mới.', 5),
(3, 2, 'Giảng viên giải thích cặn kẽ, dễ học.', 4);

-- ==========================================
-- 11. INSERT DỮ LIỆU MẪU CHO Enrollment
-- ==========================================
INSERT INTO Enrollment (UserID, CourseID)
VALUES
(2, 1),
(3, 2);

-- ==========================================
-- 12. INSERT DỮ LIỆU MẪU CHO Premium
-- ==========================================
INSERT INTO Payments (UserID, PlanID, PaymentDate, Status)
VALUES
(1, 2, NOW(), 'Success');

INSERT INTO UserPremium (UserID, PlanID, StartDate, EndDate)
VALUES
(1, 2, NOW(), DATE_ADD(NOW(), INTERVAL 3 MONTH));
