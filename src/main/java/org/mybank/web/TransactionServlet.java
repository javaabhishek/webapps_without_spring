package org.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mybank.context.ApplicationContext;
import org.mybank.model.Transaction;
import org.mybank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;

public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService= ApplicationContext.transactionService;
    private ObjectMapper objectMapper = ApplicationContext.objectMapper;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         if(req.getRequestURI().equalsIgnoreCase("/transactions")){
            List<Transaction> transactionList=transactionService.findAllTransactions();
            resp.setContentType("application/json;charset=UTF-8");
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json=objectMapper.writeValueAsString(transactionList);
            resp.getWriter().println(json);
         }else{
             resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
         }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equalsIgnoreCase("/transactions")) {

            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
            String reference = req.getParameter("reference");

            Transaction transaction = transactionService.create(amount, reference);

            resp.setContentType("application/json; charset=UTF-8");
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            resp.getWriter().print(objectMapper.writeValueAsString(transaction));

        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
