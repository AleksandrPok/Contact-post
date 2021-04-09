package com.test.contactpost.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class LoggingDispatcherServlet extends DispatcherServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoggingDispatcherServlet.class);

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(responseToCache.getStatus())
                .append(requestToCache.getMethod())
                .append(requestToCache.getRequestURI())
                .append(requestToCache.getRemoteAddr())
                .append(getResponsePayload(responseToCache))
                .append(IOUtils.toString(requestToCache.getReader()));
        LOGGER.info(sb.toString());
    }

    private String getResponsePayload(HttpServletResponse response)
            throws UnsupportedEncodingException {
        ContentCachingResponseWrapper wrapper = WebUtils
                .getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                return new String(buf, wrapper.getCharacterEncoding());
            }
        }
        return "[null]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

}
