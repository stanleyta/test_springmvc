<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="css/first.css">

<html>
<head>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" ></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	     
    <title>Spring MVC Example: Hello World</title>
</head>
<body>

<c:forEach items="${model.records}" var="record">
<p class="first">LISTING FOREACH IN JSP</p>
  <c:out value="all count: ${model.records.size()}"/> <i>$<c:out value="_id: ${record.get(\"_id\")}"/></i><br><br>
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