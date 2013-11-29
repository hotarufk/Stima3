<%-- 
    Document   : form
    Created on : Nov 28, 2013, 5:54:24 PM
    Author     : 7 ultimate
--%>

<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>

<div id="form_input" class="container">
    <br>
    <form action="result.jsp" method="post" class="form-horizontal" role="form">
        <div class="form-group">
          <label for="inputSearch" class="col-sm-2 control-label">Search Tweets Keyword</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="inputSearch" name="inputSearch" placeholder="Search Keyword">
            <span class="help-block">Keyword that help you search your desired tweets.</span>
          </div>
        </div>
        <div class="form-group">
          <label for="inputPositive" class="col-sm-2 control-label">Positive Sentiment Keyword(s)</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="inputPositive" name="inputPositive" placeholder="Positive Sentiment">
            <span class="help-block">You can fill more than one keyword by using semicolon.</span>
          </div>
        </div>
        <div class="form-group">
          <label for="inputNegative" class="col-sm-2 control-label">Negative Sentiment Keyword(s)</label>
          <div class="col-sm-10">
              <input type="text" class="form-control" id="inputNegative" name="inputNegative" placeholder="Negative Sentiment">
            <span class="help-block">You can fill more than one keyword by using semicolon.</span>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <div class="radio">
                <label>
                  <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                  Use Boyer-Moore Algorithm
                </label>
            </div>
            <div class="radio">
                <label>
                  <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                  Use Knuth-Morris-Pratt (KMP) Algorithm
                </label>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default">Analyze</button>
          </div>
        </div>
    </form>
</div>
