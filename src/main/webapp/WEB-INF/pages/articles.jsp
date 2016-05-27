<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Articles</title>
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet" type="text/css"/>
        <script type="application/javascript" src="../js/jquery-1.11.3.min.js">
        </script>
        <script type="application/javascript" src="../js/bootstrap.min.js">
        </script>
    </head>
<body>
<div id="page-wrapper">
    <!-- here is the content -->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">文章列表</h1>
        </div>
        <!-- /.col-lg-12 -->

        <!-- table info begin -->
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-1"></div>
            <div class="col-lg-10">

                <div class="panel panel-default">
                    <div class="panel-heading">文章列表</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                <tr>
                                    <th>标题</th>
                                    <th>作者</th>
                                    <th>内容</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="item">
                                    <c:set var="articleId" value="${item.id}"/>
                                    <tr>
                                        <td>
                                                ${item.title}
                                        </td>
                                        <td>
                                                ${item.author}
                                        </td>
                                        <td>${item.content}</td>
                                        <td>${item.createdTime}</td>
                                        <td><a href="<c:url value='/article/vote?article=${articleId}' />">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->

        </div>
        <!-- /.row -->
        <!-- table info end -->
        <p>
            <a href="<c:url value='/article/addNew' />">添加新文章</a>
        </p>
    </div>
</div>
</body>
</html>
