<%-- 
    Document   : list
    Created on : Jun 24, 2017, 11:09:37 AM
    Author     : sadjehwty
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $('input[type="button"]').click(function(){
                    var form=$('form');
                    if(form[0].checkValidity()){
                        var url=form.attr('action');
                        var params={
                            title: $('input[name="title"]').val(),
                            url: $('input[name="url"]').val(),
                            username: $('input[name="username"]').val()
                        };
                        $.post(url,params,function(){location.reload();});
                    }
                });
                $('button').click(function(){
                    var url=$(this).data('url');
                    $.ajax({
                        url: url,
                        method: 'delete',
                        success: function(){
                            location.reload();
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="/ReadItLater" method="post">
            <input type="url" name="url" placeholder="URL" required="required"/>
            <input type="text" name="title" placeholder="titolo"/>
            <input type="hidden" name="username" value="${user.username}" />
            <input type="button"value="Invia"/>
        </form>
        <ul><c:forEach var="page" items="${list}">
                <li><a href="${page[0].url}" target="_blank">${page[0].title}</a> <button data-url="/ReadItLater?id=${page[0].id}">cancella</button></li>
        </c:forEach></ul>
    </body>
</html>
