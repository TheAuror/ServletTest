package com.mycompany.sampleproject.PresentationLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


public class BinaryTreeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        InputStream fileContent = filePart.getInputStream();
        PrintWriter out = response.getWriter();
        String asd = "";
        while(fileContent.available()>0)
        {
            out.print((char)fileContent.read());
        }
        
    }
}
