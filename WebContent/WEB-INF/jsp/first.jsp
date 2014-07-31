<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="css/first.css">

<html>
<head>
    <title>Spring 3.2.1 MVC Example: Hello World - Crunchify.com</title>
</head>
<body>

<c:forEach items="${model.records}" var="record">
<p class="first">LISTING FOREACH IN JSP</p>
  <c:out value="count: ${record.get(\"count\")}"/> <i>$<c:out value="count: ${record.get(\"count\")}"/></i><br><br>
</c:forEach>


    ${model.message}
    

	 	<script src="js/bootbox/bootbox.js" ></script>
	 	<script src="js/first.js" ></script>
	    <script>
		    bootbox.alert("Hello world!", function() {
		        console.log("Alert Callback");
		    });
	    
			Main.third();
	    </script>
</body>
</html>