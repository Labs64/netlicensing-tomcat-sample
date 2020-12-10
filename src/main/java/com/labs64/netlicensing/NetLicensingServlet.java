package com.labs64.netlicensing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.labs64.netlicensing.domain.vo.Context;
import com.labs64.netlicensing.domain.vo.SecurityMode;
import com.labs64.netlicensing.domain.vo.ValidationParameters;
import com.labs64.netlicensing.domain.vo.ValidationResult;
import com.labs64.netlicensing.exception.NetLicensingException;
import com.labs64.netlicensing.service.LicenseeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public class NetLicensingServlet extends HttpServlet {

    private static final long serialVersionUID = -9139202060864623537L;

    private static Logger log = LoggerFactory.getLogger(NetLicensingServlet.class);

    public NetLicensingServlet() {
    }

    @Override
    public void init() {
        log.debug("NetLicensing test servlet init...");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // test URL: http://localhost:8080/netlicensing-tomcat-sample?licenseeNumber=CUST-SB-01

        PrintWriter out = response.getWriter();

        final String licenseeNumber = request.getParameter("licenseeNumber");
        out.println("Hello from NetLicensing!");
        out.println("- validate customer: " + licenseeNumber);
        log.debug("validate customer: " + licenseeNumber);

        try {
            final String productNumber = "P1S85TDRU-DEMO";
            final String licenseeName = "Customer Name";

            final ValidationParameters validationParameters = new ValidationParameters();
            validationParameters.setLicenseeName(licenseeName);
            validationParameters.setProductNumber(productNumber);

            final Context context = new Context();
            context.setBaseUrl("https://go.netlicensing.io/core/v2/rest");
            context.setSecurityMode(SecurityMode.BASIC_AUTHENTICATION);
            context.setUsername("demo");
            context.setPassword("demo");

            ValidationResult validationResult = LicenseeService.validate(context, licenseeNumber, validationParameters);

            out.println("- validationResult: " + validationResult);
        } catch (final NetLicensingException e) {
            out.println("Got NetLicensing exception: " + e);
        }
    }

}
