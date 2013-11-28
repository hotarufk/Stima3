<%-- 
    Document   : header
    Created on : Nov 28, 2013, 5:43:43 PM
    Author     : 7 ultimate
--%>

<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>

<%
    String from = request.getParameter("from");
    if(from != null) {
%>
<div class="jumbotron">
    <h1>About this project</h1>
    <p>
        How to use this application and project team profile.
    </p>
</div>
<%
    } else {
%>
<div class="jumbotron">
    <h1>Welcome to My Sentiment Analytics</h1>
    <p>
        Group all your desired tweets to their sentiment groups.<br>
        You also can choose which algorithm to process all those tweets.
    </p>
</div>
<%      
    }
%>
