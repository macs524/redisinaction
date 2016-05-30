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
                    <div class="panel-heading">搜索结果如下：</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <p>
                                关键词：${keywords}

                            </p>
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
                                <c:forEach items="${pageableResult.itemList}" var="item">
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
                                            <a href="<c:url value='/article/view?id=${articleId}' />">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <p>
                                <a href="<c:url value='/article/list' />">返回列表</a>
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
