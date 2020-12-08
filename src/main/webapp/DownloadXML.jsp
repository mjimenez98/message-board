<%@page import="java.io.*"%>
<%@ page import="models.Post" %>
<%@ page import="db.DBPost" %>
<%@ page import="java.time.LocalDateTime" %>
<%
    int pid = (int) session.getAttribute("pid");
    Post post = DBPost.getPost(pid);
    String postTitle = post.getTitle();
    String user = post.getUsername();
    String message = post.getMessage();

    response.setContentType("text/xml");
    response.setHeader("Content-Disposition", "attachment; filename=\"post.xml\"");
    response.setHeader("Expires", String.valueOf(LocalDateTime.now().plusDays(1)));

    try {
        OutputStream outputStream = response.getOutputStream();
        String xmlHeading = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        outputStream.write(xmlHeading.getBytes());

            String mStr = "<posts>\n" + "\t<title>"+ postTitle +
                    "</title>\n" + "\t<user>"+ user +
                    "</user>\n" + "\t<message>"+ message +
                    "</message>\n" + "</posts>\n";
            outputStream.write(mStr.getBytes());


        outputStream.flush();
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>