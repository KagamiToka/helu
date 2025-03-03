<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Product</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div align="center">
        <h1>Create Product</h1>
        <form style="width: 500px; margin-top: 30px;" align="left" action="user/getBean.jsp" method="POST">
            
            <div class="form-group">
                <label for="name">Name of Product:</label>
                <input type="text" class="form-control" name="name" placeholder="Enter product name" required>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" class="form-control" name="price" placeholder="Enter product price" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" name="description" rows="3" required placeholder="Enter product description"></textarea>
            </div>

            <div class="form-group">
                <label for="stock">Stock:</label>
                <input type="number" class="form-control" name="stock" placeholder="Enter stock quantity" required>
            </div>

            <div class="form-group">
                <label for="importDate">Import Date:</label>
                <input type="text" class="form-control" name="importDate" placeholder="Enter import date" required>
            </div>

            <button class="btn btn-primary mb-2" style="float: left;">
                <a href="products" style="color: white;">Back</a>
            </button>
            <button type="submit" class="btn btn-primary mb-2" style="float: right;">Create</button>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

        </form>
    </div>
</body>
</html>
