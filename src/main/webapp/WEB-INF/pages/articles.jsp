<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Articles</title>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script type="application/javascript" src="../js/jquery-1.11.3.min.js">
        </script>
        <script type="application/javascript" src="../js/bootstrap.min.js">
        </script>
    </head>
<body>
<div id="page-wrapper">
    <!-- here is the content -->
    <div class="row">
        <!-- /.col-lg-12 -->

        <!-- table info begin -->
        <!-- /.row -->
        <div class="row" style="margin-top: 50px">
            <div class="col-lg-1"></div>
            <div class="col-lg-10">

                <div class="panel panel-default">
                    <div class="panel-heading">文章列表</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div>
                            <form class="form-horizontal  container-fluid one-line-form"
                                  id="searchForm" name="searchForm" method="post"
                                  action="<c:url value="/article/searchArticle" />">
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label" for="keywords">关键词</label>

                                        <div class="col-md-4">
                                            <input type="text" class="form-control" id="keywords"
                                                   name="keywords"/>
                                        </div>
                                        <div class="col-md-2">
                                            <input type="submit" class="btn btn-primary" value="搜索"
                                                   style="margin-right: 10px" />
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
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
                                        <td>
                                            <fmt:formatDate value="${item.createdTime}" type="both"/>
                                        </td>
                                        <td>
                                            <a href="<c:url value='/article/detail?id=${articleId}' />">View</a> |
                                            <a href="<c:url value='/article/deleteNews?id=${articleId}' />">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <p>
                                <a href="<c:url value='/article/addNew' />">添加新文章</a>
                            </p>
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

    </div>
</div>
</body>
</html>
