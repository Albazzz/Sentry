<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="app.title" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;700&display=swap">
    <!-- Custom CSS files -->
    <link rel="stylesheet" href="<c:url value='/css/indexstyle.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/stylechat.css'/>">
</head>
<body>
<div class="page-wrapper">
    <%@ include file="Home/nav.jsp" %>
    <%@ include file="chatBoxjsp/chatBox.jsp" %>
    <section class="hero">
        <div class="container">
            <div class="hero-text">
                <h1><fmt:message key="hero.title" /></h1>
                <p><fmt:message key="hero.description" /></p>
                <!-- Call-to-action buttons -->
                <a href="<c:url value='/CoursesServlet'/>" class="btn-primary"><fmt:message key="hero.startNow" /></a>
                <a href="<c:url value='/introduce.jsp'/>" class="btn-secondary"><fmt:message key="hero.learnMore" /></a>
            </div>
            <!-- Hero image -->
            <div class="hero-image">
                <img src="<c:url value='/image/homepage.jpg'/>" alt="<fmt:message key='hero.imageAlt' />">
            </div>
        </div>
    </section>
    <section class="featured-courses">
        <div class="container">
            <h2><fmt:message key="courses.featuredTitle" /></h2>
            <div class="course-grid">
                <c:forEach var="course" items="${suggestedCourses}">
                    <div class="course-card suggested">
                        <h4>${course.title}</h4> <!-- Giả sử title là động, có thể i18n nếu cần -->
                        <p>${course.description}</p> <!-- Tương tự cho description -->
                        <div class="course-meta text-muted"><fmt:message key="courses.suggestedLabel" /></div>
                        <a href="${pageContext.request.contextPath}/CourseDetailServlet?id=${course.courseID}" class="btn btn-outline-primary mt-2">
                            <i class="fa-solid fa-arrow-right"></i> <fmt:message key="courses.viewDetails" />
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <%@ include file="Home/footer.jsp" %>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="<c:url value='/Script/cherry-blossom.js'/>"></script>
</body>
</html>