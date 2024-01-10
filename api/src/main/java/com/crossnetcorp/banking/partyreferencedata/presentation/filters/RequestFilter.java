package com.crossnetcorp.banking.partyreferencedata.presentation.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestBody = request.getInputStream().toString();

        //Document requestDocument = Document.parse(requestBody);
        //requestRepository.save(requestDocument, "requests");
        chain.doFilter(request, response);
    }
    
}
