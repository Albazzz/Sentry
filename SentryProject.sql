-- 1. TẠO DATABASE
-- =========================
IF DB_ID('Sentry') IS NOT NULL
    DROP DATABASE Sentry;
GO
CREATE DATABASE Sentry;
GO
USE Sentry

-- =========================
-- 2. TẠO BẢNG CHÍNH
-- =========================

-- Bảng Roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY,
    RoleName NVARCHAR(50) UNIQUE NOT NULL
);

-- Bảng PremiumPlans
CREATE TABLE PremiumPlans (
    PlanID INT PRIMARY KEY IDENTITY,
    PlanName NVARCHAR(100),
    Price DECIMAL(10, 2),
    DurationInMonths INT,
    Description NVARCHAR(255)
);

-- Bảng Users
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY,
    RoleID INT FOREIGN KEY REFERENCES Roles(RoleID),
    Email NVARCHAR(255) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255),
    GoogleID NVARCHAR(255),
    FullName NVARCHAR(100),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    IsLocked BIT DEFAULT 0,
    BirthDate DATE,
    PhoneNumber NVARCHAR(20),
    JapaneseLevel NVARCHAR(50),
    Address NVARCHAR(255),
    Country NVARCHAR(100),
    Avatar NVARCHAR(MAX),
    Gender NVARCHAR(10) CONSTRAINT DF_Users_Gender DEFAULT N'Khác',
    IsTeacherPending BIT DEFAULT 0,
    CertificatePath NVARCHAR(500)
);

-- Bảng Payments
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    PlanID INT NOT NULL FOREIGN KEY REFERENCES PremiumPlans(PlanID),
    Amount FLOAT NOT NULL,
    PaymentDate DATETIME NULL,
    TransactionNo NVARCHAR(100) NULL,
    OrderInfo NVARCHAR(255),
    ResponseCode NVARCHAR(20) NULL,
    TransactionStatus NVARCHAR(50),
    OrderCode BIGINT,
    CheckoutUrl NVARCHAR(500),
    Status NVARCHAR(50),
    CreatedAt DATETIME DEFAULT GETDATE()
);

-- Bảng UserPremium
CREATE TABLE UserPremium (
    UserID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    PlanID INT NOT NULL FOREIGN KEY REFERENCES PremiumPlans(PlanID),
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    PRIMARY KEY (UserID, PlanID, StartDate)
);

-- Bảng Courses
CREATE TABLE Courses (
    CourseID INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(255),
    Description NVARCHAR(MAX),
    IsHidden BIT DEFAULT 0,
    IsSuggested BIT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE(),
    CreatedBy INT NULL FOREIGN KEY REFERENCES Users(UserID),
    imageUrl NVARCHAR(MAX) NULL
);

-- Bảng Enrollment
CREATE TABLE Enrollment (
    EnrollmentID INT PRIMARY KEY IDENTITY,
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    EnrolledAt DATETIME DEFAULT GETDATE()
);

-- Bảng Lessons
CREATE TABLE Lessons (
    LessonID INT PRIMARY KEY IDENTITY,
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    Title NVARCHAR(255),
    Description NVARCHAR(1000),
    IsHidden BIT DEFAULT 0,
    OrderIndex INT DEFAULT 0
);

-- Bảng LessonMaterials
CREATE TABLE LessonMaterials (
    MaterialID INT PRIMARY KEY IDENTITY(1,1),
    LessonID INT NOT NULL FOREIGN KEY REFERENCES Lessons(LessonID),
    MaterialType NVARCHAR(50) NOT NULL,
    FileType NVARCHAR(50) NOT NULL,
    Title NVARCHAR(255),
    FilePath NVARCHAR(MAX),
    IsHidden BIT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE()
);

-- Bảng Vocabulary
CREATE TABLE Vocabulary (
    VocabID INT PRIMARY KEY IDENTITY,
    Word NVARCHAR(100),
    Meaning NVARCHAR(255),
    Reading NVARCHAR(100),
    Example NVARCHAR(MAX),
    LessonID INT FOREIGN KEY REFERENCES Lessons(LessonID),
    imagePath VARCHAR(255) DEFAULT NULL
);

-- Bảng UserVocabulary
CREATE TABLE UserVocabulary (
    UserVocabID INT PRIMARY KEY IDENTITY,
    UserID INT,
    Word NVARCHAR(100),
    Meaning NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE()
);

-- Bảng Kanji
CREATE TABLE Kanji (
    KanjiID INT PRIMARY KEY IDENTITY,
    Character NVARCHAR(10),
    Onyomi NVARCHAR(100),
    Kunyomi NVARCHAR(100),
    Meaning NVARCHAR(255),
    StrokeCount INT,
    LessonID INT FOREIGN KEY REFERENCES Lessons(LessonID)
);

-- Bảng Flashcards
CREATE TABLE Flashcards (
    FlashcardID INT PRIMARY KEY IDENTITY,
    UserID INT,
    Title NVARCHAR(100),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    IsPublic BIT DEFAULT 0,
    Description NVARCHAR(500),
    CoverImage NVARCHAR(500),
    CourseID INT NULL FOREIGN KEY REFERENCES Courses(CourseID)
);

-- Bảng FlashcardItems
CREATE TABLE FlashcardItems (
    FlashcardItemID INT PRIMARY KEY IDENTITY,
    FlashcardID INT FOREIGN KEY REFERENCES Flashcards(FlashcardID),
    VocabID INT NULL FOREIGN KEY REFERENCES Vocabulary(VocabID),
    UserVocabID INT NULL FOREIGN KEY REFERENCES UserVocabulary(UserVocabID),
    Note NVARCHAR(255),
    FrontContent NVARCHAR(500),
    BackContent NVARCHAR(500),
    FrontImage NVARCHAR(500),
    BackImage NVARCHAR(500),
    OrderIndex INT DEFAULT 0
);

-- Bảng Quizzes
CREATE TABLE Quizzes (
    QuizID INT PRIMARY KEY IDENTITY,
    LessonID INT FOREIGN KEY REFERENCES Lessons(LessonID),
    Title NVARCHAR(255)
);

-- Bảng Questions
CREATE TABLE Questions (
    QuestionID INT PRIMARY KEY IDENTITY,
    QuizID INT NULL FOREIGN KEY REFERENCES Quizzes(QuizID),
    QuestionText NVARCHAR(MAX),
    TimeLimit INT DEFAULT 30
);

-- Bảng Answers
CREATE TABLE Answers (
    AnswerID INT PRIMARY KEY IDENTITY,
    QuestionID INT FOREIGN KEY REFERENCES Questions(QuestionID),
    AnswerText NVARCHAR(MAX),
    IsCorrect BIT,
    AnswerNumber INT CHECK (AnswerNumber BETWEEN 1 AND 4)
);

-- Bảng QuizResults
CREATE TABLE QuizResults (
    ResultID INT PRIMARY KEY IDENTITY,
    UserID INT,
    QuizID INT FOREIGN KEY REFERENCES Quizzes(QuizID),
    Score INT,
    TakenAt DATETIME DEFAULT GETDATE()
);

-- Bảng Conversations
CREATE TABLE Conversations (
    ConversationID INT PRIMARY KEY IDENTITY(1,1),
    User1ID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    User2ID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    CreatedAt DATETIME DEFAULT GETDATE()
);

-- Bảng Messages
CREATE TABLE Messages (
    MessageID INT PRIMARY KEY IDENTITY(1,1),
    ConversationID INT NOT NULL FOREIGN KEY REFERENCES Conversations(ConversationID),
    SenderID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    Content NVARCHAR(MAX) NOT NULL,
    Type NVARCHAR(50) NOT NULL,
    IsRead BIT DEFAULT 0,
    IsRecall BIT DEFAULT 0,
    SentAt DATETIME DEFAULT GETDATE()
);

-- Bảng Blocks
CREATE TABLE Blocks (
    BlockerID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    BlockedID INT NOT NULL FOREIGN KEY REFERENCES Users(UserID),
    CreatedAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (BlockerID, BlockedID)
);

-- Bảng LessonVocabulary
CREATE TABLE LessonVocabulary (
    LessonID INT NOT NULL FOREIGN KEY REFERENCES Lessons(LessonID),
    VocabID INT NOT NULL FOREIGN KEY REFERENCES Vocabulary(VocabID),
    PRIMARY KEY (LessonID, VocabID)
);

-- Bảng Feedbacks
CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY IDENTITY,
    UserID INT,
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    Content NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    Rating INT CHECK (Rating BETWEEN 1 AND 5) NOT NULL DEFAULT 5
);

-- Bảng FeedbackVotes
CREATE TABLE FeedbackVotes (
    VoteID INT PRIMARY KEY IDENTITY,
    FeedbackID INT FOREIGN KEY REFERENCES Feedbacks(FeedbackID),
    UserID INT,
    VoteType INT CHECK (VoteType IN (1, -1)) -- 1: like, -1: dislike
);

-- Bảng CourseRatings
CREATE TABLE CourseRatings (
    RatingID INT PRIMARY KEY IDENTITY,
    UserID INT,
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(MAX),
    RatedAt DATETIME DEFAULT GETDATE()
);

-- Bảng Progress
CREATE TABLE Progress (
    ProgressID INT PRIMARY KEY IDENTITY,
    UserID INT,
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    LessonID INT FOREIGN KEY REFERENCES Lessons(LessonID),
    CompletionPercent INT CHECK (CompletionPercent BETWEEN 0 AND 100),
    LastAccessed DATETIME DEFAULT GETDATE()
);