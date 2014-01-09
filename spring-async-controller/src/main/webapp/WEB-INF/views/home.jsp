<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>forms | mvc-showcase</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet"  type="text/css" />		
	<link href="<c:url value="/resources/jqueryui/1.8/themes/base/jquery.ui.all.css" />" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div id="formsContent">
		<h2>Forms</h2>
        
		<form id="form" action="/async/callable/view/DD001" method="post" class="cleanform">
		  	<fieldset>
		  		<legend>async invoke service</legend>
		  		<label>
		  			Name <input name="bar" value='{"name":"airlee","sid":"DD001"}' />
		 		</label>
			<div id="message" ></div>
		  	</fieldset>
			<p><button type="submit">Submit</button></p>
		</form>
	</div>
    <div id="async">
     <ul>
      <li>
			<a id="callableResponseBodyLink" class="textLink"
				href="<c:url value="/async/callable/response-body" />">GET /async/callable/response-body</a>
		</li>
		<li>
			<a id="callableExceptionLink" class="textLink"
				href="<c:url value="/async/callable/exception" />">GET /async/callable/exception</a>
		</li>
		<li>
			<a id="callableUnhandledExceptionLink" class="textLink"
				href="<c:url value="/async/callable/exception?handled=false" />">GET /async/callable/exception?handled=false</a>
				(500 Error expected)
		</li>
      </ul>
	 </div>		
<script type="text/javascript" src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqueryform/2.8/jquery.form.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqueryui/1.8/jquery.ui.core.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqueryui/1.8/jquery.ui.widget.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqueryui/1.8/jquery.ui.tabs.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/json2.js" />"></script>
<script type="text/javascript">
	MvcUtil = {};
	MvcUtil.showSuccessResponse = function (text, element) {
		MvcUtil.showResponse("success", text, element);
	};
	MvcUtil.showErrorResponse = function showErrorResponse(text, element) {
		MvcUtil.showResponse("error", text, element);
	};
	MvcUtil.showResponse = function(type, text, element) {
		var responseElementId = element.attr("id") + "Response";
		var responseElement = $("#" + responseElementId);
		if (responseElement.length == 0) {
			responseElement = $('<span id="' + responseElementId + '" class="' + type + '" style="display:none">' + text + '</span>').insertAfter(element);
		} else {
			responseElement.replaceWith('<span id="' + responseElementId + '" class="' + type + '" style="display:none">' + text + '</span>');
			responseElement = $("#" + responseElementId);
		}
		responseElement.fadeIn("slow");
	};
	MvcUtil.xmlencode = function(xml) {
		//for IE 
		var text;
		if (window.ActiveXObject) {
		    text = xml.xml;
		 }
		// for Mozilla, Firefox, Opera, etc.
		else {
		   text = (new XMLSerializer()).serializeToString(xml);
		}			
		    return text.replace(/\&/g,'&'+'amp;').replace(/</g,'&'+'lt;')
	        .replace(/>/g,'&'+'gt;').replace(/\'/g,'&'+'apos;').replace(/\"/g,'&'+'quot;');
	};
		
	
			$(document).ready(function() {
				var msg = $("#message");
				$("#form").submit(function() {  
					$.ajax({ type: "POST", url: $(this).attr("action"), data: $(this).serialize(), contentType: "application/x-www-form-urlencoded", dataType: "text", success: function(text) { MvcUtil.showSuccessResponse(text, msg); }, error: function(xhr) { MvcUtil.showErrorResponse(xhr.responseText, msg); }});
					return false;
					
				});	
				
				$("a.textLink").click(function(){
					var link = $(this);
					$.ajax({ url: link.attr("href"), dataType: "text", success: function(text) { MvcUtil.showSuccessResponse(text, link); }, error: function(xhr) { MvcUtil.showErrorResponse(xhr.responseText, link); }});
					return false;
				});
			});
		</script>
	
</body>
</html>
