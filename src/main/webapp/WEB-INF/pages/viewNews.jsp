<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>查看文章</title>
    <link href="../css/bootstrap.min.css?v=2.5" rel="stylesheet" type="text/css"/>
    <script type="application/javascript" src="../js/jquery-1.11.3.min.js">
        </script>
    <script type="application/javascript" src="../js/bootstrap.min.js">
        </script>

    <style type="text/css">
        .col-md-9 , .col-md-5{
            padding-top: 7px;
        }
    </style>
</head>
<body>

<div id="page-wrapper">
    <!-- here is the content -->
    <div class="row">
        <!-- /.col-lg-12 -->

        <!-- table info begin -->
        <!-- /.row -->
        <div class="row" style="margin-top: 50px">
            <div class="col-lg-1" ></div>
            <div class="col-lg-10">


                <div class="panel panel-default">
                    <div class="panel-heading">查看文章</div>
                    <div class="panel-body">
                    <c:choose>
                        <c:when test="${article != null}">
                            <form class="form-horizontal  container-fluid one-line-form">
                                <!-- row one -->
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">文章ID</label>
                                        <div class="col-md-5">
                                            ${article.id}
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">文章标题</label>
                                        <div class="col-md-5">
                                                ${article.title}
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">作者</label>
                                        <div class="col-md-5">
                                            ${article.author}
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">文章内容</label>
                                        <div class="col-md-9">
                                            ${article.content}
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">创建时间</label>
                                        <div class="col-md-9">
                                            <fmt:formatDate value="${article.createdTime}" type="both"/>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <div class="form-group">无相关文章</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <!-- /.panel-heading -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
</div>
<!-- /#page-wrapper -->
</body>
</html>
