<%-- 
    Document   : about
    Created on : Nov 28, 2013, 6:40:54 PM
    Author     : 7 ultimate
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>My Sentiment Analytics</title>
        
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script src="js/bootstrap.js"></script>
        
        <%@include file="header.jsp" %>
        <div class="container">
            <h1>About</h1>
            <p>
                This is an application to group searched tweets from all user that contain the keyword by the sentiment keyword.<br>
                Sentiment keyword divided into two groups : positives and negatives.<br>
                Tweets that contain positive keyword grouped to positive sentiment group.<br>
                Otherwise, tweets that contain negative keyword grouped to negative sentiment group.<br>
                But, for tweets that don't contain any sentiment keyword or contain both of them in same ammount will grouped to neutral sentiment group.
            </p>
        </div>
        <br>
        <div class="container">
            <h1>Project Team Profile</h1>
            <%@include file="profile.jsp" %>
        </div>
        <%@include file="navigation-about.jsp" %>
    </body>
</html>
