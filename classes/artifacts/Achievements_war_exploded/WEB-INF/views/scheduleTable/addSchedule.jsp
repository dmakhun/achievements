<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Upload File Request Page</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
    File to upload: <input type="file" name="file"><br/>
    <input type="submit" value="Upload"> Press here to upload the file!
</form>

${status}

</body>
</html>