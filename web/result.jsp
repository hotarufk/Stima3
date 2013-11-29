<%-- 
    Document   : result
    Created on : Nov 28, 2013, 7:51:49 PM
    Author     : 7 ultimate
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="twitterAPI.Twitter_testing" %>
<%@page import="twitterAPI.searchHandler" %>
<%@page import="tweetParser.TweetParser" %>

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
        
        <%--
            Twitter_testing tt = new Twitter_testing();
            tt.setup();
        --%>
        <div class="jumbotron">
            <h1>Showing results</h1>
            <p>
                Showing tweets that already grouped to their sentiment groups.<br>
                <%
                    Twitter_testing tt = new Twitter_testing();
                    tt.setup();
                    
                    int positives = 0;
                    int negatives = 0;
                    int neutrals = 0;
                    
                    String bufferSearch = request.getParameter("inputSearch");
                    String bufferPositive = request.getParameter("inputPositive");
                    String bufferNegative = request.getParameter("inputNegative");
                    
                    boolean option1 = true; // Switch buat pake algoritma apa
                    
                    if(request.getParameter("optionsRadios").equals("option1")) {
                        out.print("This grouping was using Boyer-Moore Algorithm.");
                        option1 = true;
                    } else {
                        out.print("This grouping was using Knuth-Morris-Pratt (KMP) Algorithm.");
                        option1 = false;
                    }
                    
                    String[] searchToken = bufferSearch.split(" ");
                    String regex = "";
                    for(int i = 0; i < searchToken.length; i++) {
                        if(i != 0) {
                            regex += "%";
                        }
                        regex += searchToken[i];
                    }
                    
                    tt.getSearchTweets(regex);
                    
                    String[] positiveToken = bufferPositive.split("; ");
                    String[] negativeToken = bufferNegative.split("; ");
                    
                    List<String> keyWords = new ArrayList<String>();
                    for(int i = 0; i < searchToken.length; i++) {
                        keyWords.add(searchToken[i]);
                    }
                    List<String> posWords = new ArrayList<String>();
                    for(int i = 0; i < positiveToken.length; i++) {
                        posWords.add(positiveToken[i]);
                    }
                    List<String> negWords = new ArrayList<String>();
                    for(int i = 0; i < negativeToken.length; i++) {
                        negWords.add(negativeToken[i]);
                    }
                %>
            </p>
        </div>
        <div class="container">
            <br>
            <h1>Results</h1>
            <br>
            <%
                ArrayList<String> results = new ArrayList<String>();
                for(int i = 0; i < tt.shholder.size(); i++) {
                    TweetParser tp = new TweetParser();
                    tp.Reset(keyWords, posWords, negWords, !option1);
                    
                    results.add(tp.SearchKey(tt.shholder.get(i).tweet));
                    
                    if(tp.SearchSentiment(tt.shholder.get(i).tweet) > 0) {
                        tt.shholder.get(i).jenistweet = 1; // positive sentiment
                        positives++;
                    } else if(tp.SearchSentiment(tt.shholder.get(i).tweet) < 0) {
                        tt.shholder.get(i).jenistweet = -1; // negative sentiment
                        negatives++;
                    } else {
                        tt.shholder.get(i).jenistweet = 0; // neutral sentiment
                        neutrals++;
                    }
                }
            %>
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                        Positive Sentiment
                      </a>
                    </h4>
                  </div>
                  <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
                      <%
                            for(int i = 0; i < tt.shholder.size(); i++) {
                                if(tt.shholder.get(i).jenistweet > 0) {
                                    out.print(tt.shholder.get(i).nama + "<br>" + results.get(i) + "<br><br>");
                                }
                            }
                      %>
                    </div>
                  </div>
                </div>
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                        Negative Sentiment
                      </a>
                    </h4>
                  </div>
                  <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                      <%
                            for(int i = 0; i < tt.shholder.size(); i++) {
                                if(tt.shholder.get(i).jenistweet < 0) {
                                    out.print(tt.shholder.get(i).nama + "<br>" + results.get(i) + "<br><br>");
                                }
                            }
                      %>
                    </div>
                  </div>
                </div>
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                        Neutral Sentiment
                      </a>
                    </h4>
                  </div>
                  <div id="collapseThree" class="panel-collapse collapse">
                    <div class="panel-body">
                      <%
                            for(int i = 0; i < tt.shholder.size(); i++) {
                                if(tt.shholder.get(i).jenistweet == 0) {
                                    out.print(tt.shholder.get(i).nama + "<br>" + results.get(i) + "<br><br>");
                                }
                            }
                      %>
                    </div>
                  </div>
                </div>
              </div>
              <h2>We've searched <% out.print(positives+negatives+neutrals); %> tweets.</h2>
              <h3>You've got : <%out.print(positives);%> positive sentiment tweet(s), <%out.print(negatives);%> negative sentiment tweet(s), and <%out.print(neutrals);%> neutrals sentiment tweet(s).</h3>
              <form action="index.jsp">
                  <button type="submit" class="btn btn-primary btn-lg">Make another search!</button>
              </form>
        </div>
        <br>
        <ol class="breadcrumb">
            <li><%--<a href="index.jsp">Back to Home</a>--%></li>
        </ol>
    </body>
</html>
