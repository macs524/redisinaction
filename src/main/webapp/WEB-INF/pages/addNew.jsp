<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>添加文章</title>
    <link href="../css/bootstrap.min.css?v=2.5" rel="stylesheet" type="text/css"/>
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
            <div class="col-lg-1" ></div>
            <div class="col-lg-10">

                <div class="panel panel-default">
                    <div class="panel-heading">添加文章</div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form class="form-horizontal  container-fluid one-line-form"
                              id="addForm" name="addForm" method="post" action="addNew">
                            <!-- row one -->
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="title">文章标题</label>

                                    <div class="col-md-5">
                                        <input type="text" class="form-control" id="title" name="title"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="author">作者</label>

                                    <div class="col-md-5">
                                        <input type="text" class="form-control" id="author" name="author"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">文章内容</label>

                                    <div class="col-md-9">
                                        <textarea class="form-control" id="content" name="content" rows="4" cols="16"></textarea>
                                    </div>
                                </div>
                            </div>

                            <!-- valid term end -->
                            <div class="row" style="margin-bottom: 10px">
                                <div class="col-md-3">&nbsp;</div>
                                <span id="operationMsg" style="color: red">&nbsp;</span>
                            </div>

                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">&nbsp;</label>

                                    <div class="col-md-9 ">
                                        <input type="button" class="btn btn-primary" value="提交"
                                               style="margin-right: 10px" onclick="addNew()"/>
                                        <input type="reset" class="btn btn-default" value="重置"/>
                                    </div>
                                </div>
                            </div>
                        </form>
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

<script type="application/javascript">
    function addNew() {

        var title = $("#title").val();
        var content = $("#content").val();
        var author = $("#author").val();

        if(title == ""){
            $("#operationMsg").text("标题不能为空!");
            $("#title").focus();
            return;
        }
        if(content == ""){
            $("#operationMsg").text("内容不能为空!");
            $("#content").focus();
            return;
        }
        if(author == ""){
            $("#operationMsg").text("作者不能为空!");
            $("#author").focus();
            return;
        }

        $("#addForm").submit();

    }
</script>

</body>
</html>
